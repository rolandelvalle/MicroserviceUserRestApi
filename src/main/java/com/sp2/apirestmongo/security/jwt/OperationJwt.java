package com.sp2.apirestmongo.security.jwt;

import com.sp2.apirestmongo.model.User;
import io.jsonwebtoken.Claims;

import java.util.Calendar;

public interface OperationJwt {
    String generateJwt(User user, Calendar expirationData);
    Boolean validateJwt(String jwt, User user);
    Claims returnClaim(String jwt);
    String extractSubject(String jwt);
}
