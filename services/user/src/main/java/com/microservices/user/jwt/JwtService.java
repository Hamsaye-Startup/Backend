package com.microservices.user.jwt;

import com.microservices.user.users.models.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    private static final String SECRET_KEY = "150059a9896a3bf884277437c3b6b1d39b9283e8dc2f9ca668d61939ae019ae2";

    // extract phone number of user from token
    public String extractSubject(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // generate token -> access_token and refresh_token
    // TODO: encrypt the payload
    public String generateToken(
            Map<String, Object> claims,
            UserDetails user,
            int duration
    ) {
        return Jwts.builder()
                .claims(claims)
                .subject(user.getUsername())
                .issuer("hamsaye-user-service")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + duration))
                .signWith(getSignInKey())
                .compact();
    }

    // check token verification
    public boolean isTokenValid(String token, String username) {
        return ((username.equals(extractSubject(token)))
                && (!isTokenExpired(token))
                && (!isSessionExpired(token))
        );
    }

    // check token expiration
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date(System.currentTimeMillis()));
    }

    // check session expiration
    private boolean isSessionExpired(String token) {
        return false;
    }

    // extract specific claim based on the function
    public  <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // extract all claims of user from token
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // generate sign in secret key
    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
