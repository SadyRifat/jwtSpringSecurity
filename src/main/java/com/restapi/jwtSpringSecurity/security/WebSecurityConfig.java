package com.restapi.jwtSpringSecurity.security;

import com.restapi.jwtSpringSecurity.filter.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement ()
                .sessionCreationPolicy (SessionCreationPolicy.STATELESS)
                .and ()
                .authenticationProvider (authenticationProvider)
                .addFilterBefore(authFIlter(), AnonymousAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
                .httpBasic().disable()
                .logout().disable()
                .cors();
    }

    public AuthFilter authFIlter() throws Exception {
        OrRequestMatcher orRequestMatcher = new OrRequestMatcher(
                new AntPathRequestMatcher ("/user/**"),
                new AntPathRequestMatcher("/token/**"),
                new AntPathRequestMatcher("/role/**")
        );
        AuthFilter authFilter = new AuthFilter(orRequestMatcher);
        authFilter.setAuthenticationManager(authenticationManager());
        return authFilter;
    }
}
