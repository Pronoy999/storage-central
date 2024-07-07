package com.storage.central.utils;

import com.storage.central.exceptions.InvalidTokenException;
import com.storage.central.model.JwtData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class JwtHelper {

    @Value("${jwt.secret.key}")
    private static String key;

    /**
     * Method to create the JWT token for the User.
     *
     * @param email: the email id of the user.
     * @param guid:  the Guid of the user.
     * @return jwt as String, else null
     */
    public static String createJwt(@NotNull final String email, @NotNull String guid) {
        return Jwts.builder().claim("email", email).claim("guid", guid).signWith(Keys.hmacShaKeyFor(key.getBytes())).compact();
    }

    /**
     * Method to verify the JWT token.
     *
     * @param token: the token to be verified.
     * @return the GUID if the token is valid, else throws {@link InvalidTokenException}.
     */
    public static JwtData verifyJwt(@NotNull final String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().verifyWith(Keys.hmacShaKeyFor(key.getBytes())).build().parseSignedClaims(token);
            return JwtData.builder()
                    .email(claimsJws.getPayload().get("email").toString())
                    .guid(claimsJws.getPayload().get("guid").toString()).build();
        } catch (JwtException e) {
            log.error("Invalid JWT token.", e);
        }
        throw new InvalidTokenException("Invalid JWT token.");
    }
}
