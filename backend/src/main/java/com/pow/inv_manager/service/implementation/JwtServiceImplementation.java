package com.pow.inv_manager.service.implementation;

import com.pow.inv_manager.model.Admin;
import com.pow.inv_manager.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static io.jsonwebtoken.Jwts.builder;

/**
 * Implementation of the JWT service for token generation and validation.
 */
@Service
public class JwtServiceImplementation implements JwtService {

    @Value("${jwtKey}")
    private String secretKey;

    private static final long TOKEN_VALIDITY = Duration.ofDays(1).toMillis();

    /**
     * Validates if the provided token is valid for the given user details.
     *
     * @param token       the JWT token to validate
     * @param userDetails the UserDetails object representing the user
     * @return true if the token is valid, false otherwise
     */
    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String email = getEmailFromToken(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private Claims getAllClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts the email from the JWT token.
     *
     * @param token the JWT token
     * @return the email extracted from the token
     */
    @Override
    public String getEmailFromToken(String token) {
        return getClaim(token, Claims::getSubject);
    }

    private Date getExpirationDate(String token) {
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token) {
        return getExpirationDate(token).before(new Date());
    }

    /**
     * Generates a JWT token for the given user entity.
     *
     * @param user the user entity for which to generate the token
     * @return the JWT token
     */
    @Override
    public String getToken(Admin user) {
        return getToken(new HashMap<>(), user);
    }

    /**
     * Generates a JWT token with additional claims for the given user entity.
     *
     * @param extraClaims additional claims to include in the token
     * @param admin        the admin entity for which to generate the token
     * @return the JWT token with the specified claims
     */
    private String getToken(Map<String, Object> extraClaims, Admin admin) {
        return builder()
                .claims(extraClaims)
                .claim("userId", admin.getId())
                .claim("ROLE", admin.getRole())
                .subject(admin.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
                .signWith(getKey())
                .compact();
    }

    /**
     * Retrieves the secret key used for signing JWT tokens.
     *
     * @return the secret key used for signing JWT tokens
     */
    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
