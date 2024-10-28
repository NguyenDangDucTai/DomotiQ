package com.example.devicesservice.contexts;

import com.example.devicesservice.exceptions.UnauthorizedException;

public class SecurityContext {

    private static final ThreadLocal<AuthCertificate> certificateThreadLocal = new ThreadLocal<>();

    public static void setAuthenticationCertificate(AuthCertificate certificate) {
        certificateThreadLocal.set(certificate);
    }

    public static AuthCertificate getAuthenticationCertificate() {
        AuthCertificate certificate = certificateThreadLocal.get();
        if(certificate == null)
            throw new UnauthorizedException("Unauthorized");
        return certificate;
    }

    public static void clearAuthenticationCertificate() {
        certificateThreadLocal.remove();
    }

}
