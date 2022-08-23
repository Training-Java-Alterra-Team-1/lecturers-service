package com.sisakad.lecturers.security;

import com.sisakad.lecturers.filters.CustomAuthorization;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/swagger-ui/*", "/swagger-ui.html", "/webjars/**", "/v2/**", "/swagger-resources/**").permitAll();
        http.authorizeRequests().antMatchers("/api/v1/mapping/**", "/api/v1/dosen/**").hasAnyAuthority("ROLE_LECTURER", "ROLE_ADMIN");
        http.authorizeRequests().anyRequest().fullyAuthenticated();
        http.addFilterBefore(new CustomAuthorization(), UsernamePasswordAuthenticationFilter.class);
    }
}
