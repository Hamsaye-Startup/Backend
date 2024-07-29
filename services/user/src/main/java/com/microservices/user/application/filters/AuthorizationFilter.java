package com.microservices.user.application.filters;

import com.microservices.user.roles.models.UserAuthorityEnum;
import com.microservices.user.users.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter {

    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        // get the X_USER_N and X_ROLE_A
        String username = request.getHeader("X_USER_N");
        String authority = request.getHeader("X_ROLE_A");

        if (!checkUsernameAuthorityExist(username, authority)) {
            filterChain.doFilter(request, response);
            return;
        }

        // get the list of authorities
        Set<GrantedAuthority> authorities = getAuthorities(authority);
        if (SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails user = userDetailsServiceImpl.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            authorities
                    );

            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }

    private boolean checkUsernameAuthorityExist(String username, String authority) {
        return username != null || authority != null;
    }

    private Set<GrantedAuthority> getAuthorities(String authority) {
        String cleanedString = authority.substring(1, authority.length() - 1);
        if (cleanedString.isEmpty()) {
            return null;
        }

        List<String> authorities = Arrays.asList(cleanedString.split(", "));
        return authorities.stream()
                .map(UserAuthorityEnum::valueOf)
                .collect(Collectors.toSet());
    }
}
