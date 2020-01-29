package com.sep.nc.service.impl;


import io.jsonwebtoken.*;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@UtilityClass
public class UtilityService {

    public static final String SECRET = "pM0p1r2uGzRz3DCJBFvrQWm4";

    private static final String PREFIX = "Bearer ";


    public String getEmailFromToken(String wholeToken){
        if (wholeToken != null && wholeToken.startsWith(PREFIX)) {
            String token = wholeToken.substring(PREFIX.length(), wholeToken.length());

            try {
                Claims body = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(token)
                        .getBody();

                return body.getSubject();

            } catch (JwtException | ClassCastException e) {
                log.error("Error while parsing jwt");
                throw e;
            }
        }
        log.error("Error while parsing jwt");
        return null;
    }

}
