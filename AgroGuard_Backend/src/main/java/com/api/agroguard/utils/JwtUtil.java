package com.api.agroguard.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Date;

public class JwtUtil {

    private static final String SECRET = "your_secret";
    private static final String ISSUER = "your_issuer";

    public static String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuer(ISSUER)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 900000)) // 15 minutes
                .sign(Algorithm.HMAC512(SECRET));
    }

    public static String validateTokenAndRetrieveSubject(String token) {
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC512(SECRET))
                    .withIssuer(ISSUER)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getSubject();
        } catch (JWTVerificationException exception) {
            //Invalid token
        }
        return null;
    }
}
