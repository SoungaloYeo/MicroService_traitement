/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.afriquecode.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 *
 * @author sglo
 */
public class JWTAuthorizationFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws ServletException, IOException {
        httpServletResponse.addHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers,authorization");
        httpServletResponse.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials, authorization");
        httpServletResponse.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,DELETE,PATCH");
        
        if (httpServletRequest.getMethod().equals("OPTIONS")) {
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        } else if (httpServletRequest.getRequestURI().equals("/login")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        } else {
            String jwtToken = httpServletRequest.getHeader(MyConfigParam.JWT_HEADER_NAME);
            System.out.println("************Token=" + jwtToken);
            if (jwtToken == null || !jwtToken.startsWith(MyConfigParam.HEADER_PREFIX)) {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(MyConfigParam.SECRET)).build();
            String jwt = jwtToken.substring(MyConfigParam.HEADER_PREFIX.length());
            DecodedJWT decodedJWT = verifier.verify(jwt);
            System.out.println("************JWT=" + jwt);
            String username = decodedJWT.getSubject();
            List<String> roles = decodedJWT.getClaims().get("roles").asList(String.class);
            System.out.println("*************username=" + username);
            System.out.println("*************roles=" + roles);
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            roles.forEach(rn -> {
                authorities.add(new SimpleGrantedAuthority(rn));
            });
            UsernamePasswordAuthenticationToken user
                    = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(user);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}
