package com.example.devicesservice.services.impls;

import com.example.devicesservice.contexts.AuthCertificate;
import com.example.devicesservice.dtos.auth.AuthResponse;
import com.example.devicesservice.dtos.auth.LoginRequest;
import com.example.devicesservice.dtos.auth.SignupRequest;
import com.example.devicesservice.dtos.user.UserResponse;
import com.example.devicesservice.exceptions.UnauthorizedException;
import com.example.devicesservice.exceptions.ValidationException;
import com.example.devicesservice.mappers.UserMapper;
import com.example.devicesservice.models.User;
import com.example.devicesservice.repositories.UserRepository;
import com.example.devicesservice.services.AuthService;
import com.example.devicesservice.utils.JwtTokenUtil;
import com.example.devicesservice.utils.PasswordEncoder;
import com.example.devicesservice.utils.TokenType;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Value("${auth.token.access-token.expiration-time}")
    private long accessTokenExpirationTime;

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    @Override
    public UserResponse signup(SignupRequest request) {
        if(userRepository.existsByUsername(request.getUsername()))
            throw new ValidationException(Map.of("username", "Username is already taken"));

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UnauthorizedException("Username or password is incorrect"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Username or password is incorrect");
        }

        String accessToken = jwtTokenUtil.generateToken(user,  TokenType.ACCESS_TOKEN, accessTokenExpirationTime);

        return AuthResponse.builder()
                .accessToken(accessToken)
                .user(userMapper.toUserResponse(user))
                .build();
    }

    @Override
    public AuthCertificate getAuthCertificateByUserId(String userId) {
        User user = userRepository.findById(new ObjectId(userId))
                .orElseThrow(() -> new UnauthorizedException("User not found or has been deleted"));

        return AuthCertificate.builder()
                .user(user)
                .build();
    }

}
