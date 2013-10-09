/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wad.auth;

import java.util.ArrayList;
import java.util.List;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

/**
 *
 * @author timosand
 */
@Service
public class WadAuthenticationProvider implements AuthenticationProvider {
    
    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String username = auth.getName();
        String password = auth.getCredentials().toString();
        
        if("nsa".equals(username) && "nsa".equals(password)) {
            List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
            grantedAuths.add(new SimpleGrantedAuthority("superuser"));
            
            return new UsernamePasswordAuthenticationToken(username, password, grantedAuths);
        }
        
        if("jack".equals(username) && "random".equals(password)) {
            List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
            grantedAuths.add(new SimpleGrantedAuthority("user"));
            
            return new UsernamePasswordAuthenticationToken(username, password, grantedAuths);
        }

        throw new AuthenticationException("Incorrect information entered.") {};
    }

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(UsernamePasswordAuthenticationToken.class);
    }
    
}
