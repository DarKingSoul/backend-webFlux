package com.mitocode.Security;

import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthorizeByRol {

     public boolean hasAccess() {
         return Boolean.TRUE.equals(ReactiveSecurityContextHolder.getContext()
                 .map(securityContext -> securityContext.getAuthentication().getAuthorities().stream()
                         .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN")))
                 .block());
     }

}
