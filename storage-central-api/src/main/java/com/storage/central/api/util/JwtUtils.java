package com.storage.central.api.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.storage.central.common.exceptions.InvalidTokenException;
import com.storage.central.common.model.dto.JwtData;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtUtils {
    private final String key;

    /**
     * Method to create the JWT token for the User.
     *
     * @param email: the email id of the user.
     * @param guid:  the Guid of the user.
     * @return jwt as String, else null
     */
    public String createJwt(@NotNull final String email, @NotNull String guid) {
        Algorithm algorithm = Algorithm.HMAC256(key);
        return JWT.create().withClaim("email", email).withClaim("guid", guid).sign(algorithm);
    }

    /**
     * Method to verify the JWT token.
     *
     * @param token: the token to be verified.
     * @return the GUID if the token is valid, else throws {@link InvalidTokenException}.
     */
    public JwtData verifyJwt(@NotNull final String token) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            String guid = decodedJWT.getClaim("guid").asString();
            String email = decodedJWT.getClaim("email").asString();
            return JwtData.builder().emailId(email).guid(guid).build();
        } catch (JWTDecodeException e) {
            throw new InvalidTokenException("Invalid JWT token.");
        }
    }
}
