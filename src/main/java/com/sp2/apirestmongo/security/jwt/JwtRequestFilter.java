package com.sp2.apirestmongo.security.jwt;

import com.sp2.apirestmongo.model.User;
import com.sp2.apirestmongo.service.UserDetailServiceAuth;
import com.sp2.apirestmongo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private OperationJwt operationJwt;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDetailServiceAuth userDetailServiceAuth;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String autorizationHeader = request.getHeader("Authorization");
        if (autorizationHeader != null && autorizationHeader.startsWith("Bearer")){
            String jwt = autorizationHeader.substring(7);
            String iduser = operationJwt.extractSubject(jwt);

            if (iduser != null && SecurityContextHolder.getContext().getAuthentication() == null){
                User userFound = userService.findById(iduser);

                if (operationJwt.validateJwt(jwt, userFound)){
                    UserDetails userDetails = userDetailServiceAuth.loadUserByUsername(userFound.getEmail());
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails,
                                    null,
                                    userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource()
                            .buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);


                }


            }


        }
        filterChain.doFilter(request,response);
    }
}
