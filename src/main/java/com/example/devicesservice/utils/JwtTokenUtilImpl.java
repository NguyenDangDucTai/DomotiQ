package com.example.devicesservice.utils;

import com.example.devicesservice.exceptions.InvalidTokenException;
import com.example.devicesservice.exceptions.TokenExpiredException;
import com.example.devicesservice.models.User;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenUtilImpl implements JwtTokenUtil {

    @Value("${auth.token.secret}")
    private String SECRET_KEY;

    @Override
    public String generateToken(User user, TokenType type, long expirationTime) {
        ZonedDateTime issuedAt = ZonedDateTime.now();
        ZonedDateTime expiration = issuedAt.plusSeconds(expirationTime);

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getId().toHexString())
                .issuer("Pharmista")
                .issueTime(Date.from(issuedAt.toInstant()))
                .expirationTime(Date.from(expiration.toInstant()))
                .claim("type", type.name())
                .build();

        try {
            JWSSigner signer = new MACSigner(SECRET_KEY);
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            signedJWT.sign(signer);
            return signedJWT.serialize();
        } catch (Exception ex) {
            throw new RuntimeException("Failed to sign JWT");
        }
    }

    @Override
    public TokenPayload validateToken(String token, TokenType type) {
        if(!token.startsWith("Bearer ")) {
            throw new InvalidTokenException("Invalid token");
        }

        token = token.substring(7);

        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

            if(!claimsSet.getIssuer().equals("Pharmista")) {
                throw new InvalidTokenException("Invalid token");
            }

            String tokenType = (String) claimsSet.getClaim("type");
            if(tokenType == null) {
                throw new InvalidTokenException("Invalid token");
            } else if(!tokenType.equals(type.name())) {
                throw new InvalidTokenException("Invalid token");
            }

            Date expiredTime = claimsSet.getExpirationTime();
            Date now = new Date();

            if(expiredTime != null && now.after(expiredTime)) {
                throw new TokenExpiredException("Token is expired");
            }

            JWSVerifier verifier = new MACVerifier(SECRET_KEY);

            if(!signedJWT.verify(verifier)) {
                throw new InvalidTokenException("Invalid token");
            }

            String userId = claimsSet.getSubject();
            String key = claimsSet.getJWTID();

            return TokenPayload.builder()
                    .tokenId(key)
                    .userId(userId)
                    .build();
        } catch (InvalidTokenException | TokenExpiredException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InvalidTokenException("Invalid token");
        }
    }

    @Override
    public TokenPayload getTokenPayload(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWTClaimsSet claimsSet = signedJWT.getJWTClaimsSet();

            return TokenPayload.builder()
                    .tokenId(claimsSet.getJWTID())
                    .userId(claimsSet.getSubject())
                    .build();
        } catch (Exception ex) {
            throw new InvalidTokenException("Khong the parse token");
        }
    }

}
