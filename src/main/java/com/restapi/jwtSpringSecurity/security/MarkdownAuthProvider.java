package com.restapi.jwtSpringSecurity.security;

import com.restapi.jwtSpringSecurity.enums.ERole;
import com.restapi.jwtSpringSecurity.model.Consumer;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class MarkdownAuthProvider extends AbstractUserDetailsAuthenticationProvider {
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {

    }

    @Override
    protected UserDetails retrieveUser(String userEmail, UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) throws AuthenticationException {
        Consumer principal = (Consumer) usernamePasswordAuthenticationToken.getPrincipal ();
        if (!principal.getConsumerID ().isEmpty ()) {
            return new User (principal.getConsumerID (), "", AuthorityUtils.createAuthorityList(principal.getERoles ().stream ().map (Enum::name).toArray (String[]::new)));
        } else {
            return new User ("", "", AuthorityUtils.createAuthorityList(ERole.ROLE_ANONYMOUS.name ()));
        }
    }
}
