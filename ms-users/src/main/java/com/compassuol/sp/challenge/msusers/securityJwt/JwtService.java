package com.compassuol.sp.challenge.msusers.securityJwt;

import com.compassuol.sp.challenge.msusers.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtService {

    @Value("${security.jwt.expiration}")
    private String expiration;

    @Value("${security.jwt.secret-key}")
    private String passwordKey;

    public String tokenGenerate(User user) {
        Long expirationString = Long.valueOf(expiration);
        LocalDateTime dateTimeExpiration = LocalDateTime.now().plusMinutes(expirationString);
        Instant instant = dateTimeExpiration.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);

        return Jwts
                .builder()
                .setSubject(user.getEmail())
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, passwordKey)
                .compact();
    }

    private Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(passwordKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validToken(String token) {
        try {
            Claims claims = getClaims(token);
            Date expirationDate = claims.getExpiration();
            LocalDateTime date =
                    expirationDate.toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !LocalDateTime.now().isAfter(date);
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserEmail(String token) throws ExpiredJwtException {
        return (String) getClaims(token).getSubject();
    }
}
