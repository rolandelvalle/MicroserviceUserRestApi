package com.sp2.apirestmongo.security.jwt;

import com.sp2.apirestmongo.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;


public class OperationJwtImpl implements OperationJwt{

    private String keySecret = "Ada123";

    @Override
    public String generateJwt(User user, Calendar expirationData) {
        return Jwts.builder()
                .setSubject(user.getId())
                .claim("name", user.getFullName())
                .setIssuedAt(new Date())
                .setExpiration(expirationData.getTime())
                .signWith(SignatureAlgorithm.HS256, keySecret)
                .compact();
    }

    @Override
    public Boolean validateJwt(String jwt, User user) {
        Boolean isJwtExpired = returnClaim(jwt).getExpiration().before(new Date());
        Boolean isValidJwt = user.getId().equals(extractSubject(jwt)) && !isJwtExpired;
        return isValidJwt;
    }

    @Override
    public Claims returnClaim(String jwt) {
        return Jwts.parser().setSigningKey(keySecret).parseClaimsJws(jwt).getBody();
    }

    @Override
    public String extractSubject(String jwt) {
        return returnClaim(jwt).getSubject();
    }
}
