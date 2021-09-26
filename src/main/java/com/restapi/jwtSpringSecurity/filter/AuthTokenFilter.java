package com.restapi.jwtSpringSecurity.filter;

import com.google.gson.Gson;
import com.restapi.jwtSpringSecurity.entity.Role;
import com.restapi.jwtSpringSecurity.enums.ERole;
import com.restapi.jwtSpringSecurity.model.Consumer;
import com.restapi.jwtSpringSecurity.service.JWTService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class AuthTokenFilter extends AbstractAuthenticationProcessingFilter {
    public AuthTokenFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super (requiresAuthenticationRequestMatcher);
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException {
        Authentication authentication;
        JWTService jwtService = new JWTService ();
        String token = parseJwt (httpServletRequest);
        if (token == null || token.isEmpty ()) {
            authentication = new UsernamePasswordAuthenticationToken (ERole.ROLE_ANONYMOUS.name (), "");
        } else if (jwtService.validateJwtToken (token)){
            Gson gson = new Gson();
            String subjectDataFromJwtToken = jwtService.getSubjectDataFromJwtToken (token);
            Consumer consumer = new Consumer ();
            consumer.setConsumerID ("4563534");
            Set<ERole> set = new HashSet<> ();
            set.add (ERole.ROLE_ADMIN);
            consumer.setERoles (set);
            authentication = new UsernamePasswordAuthenticationToken(gson.fromJson (subjectDataFromJwtToken, Consumer.class), "");
        } else {
            throw new Exception ("Invalid Payload");
        }
        return getAuthenticationManager().authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("error", failed.getCause());
        jsonObject.put("errorMessage", failed.getMessage());

        response.getWriter().print(jsonObject);
        response.getWriter().flush();
    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }
}
