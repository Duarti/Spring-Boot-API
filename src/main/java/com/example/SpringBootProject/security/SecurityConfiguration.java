package com.example.SpringBootProject.security;

import com.example.SpringBootProject.security.filters.CustomAuthenticationFilter;
import com.example.SpringBootProject.security.filters.CustomAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfiguration extends GlobalMethodSecurityConfiguration {

    private UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfiguration(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());
        customAuthenticationFilter.setFilterProcessesUrl("/api/login");

        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/api/login/**", "/api/token/refresh/**", "/api/register/**").permitAll();
        http.authorizeRequests().antMatchers(GET, "/api/users").hasAnyAuthority("ROLE_USER");
        http.authorizeRequests().antMatchers(GET, "/api/posts", "/api/user/*/posts/**", "/api/users/**", "/api/user/**", "/api/post/*/user/**").hasAnyAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(GET, "/api/post/**", "/api/myposts/**", "/api/me/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER");
        http.authorizeRequests().antMatchers(POST, "/api/posts/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER");
        http.authorizeRequests().antMatchers(DELETE, "/api/post/*/**", "api/me/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER");
        http.authorizeRequests().antMatchers(DELETE, "/api/user/*/**").hasAuthority("ROLE_ADMIN");
        http.authorizeRequests().antMatchers(PUT, "/api/post/*/**", "/api/me/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER");
        http.authorizeRequests().antMatchers(PUT, "/api/user/*/**", "/api/user/*/makeadmin/**").hasAuthority("ROLE_ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.userDetailsService(userDetailsService)
                .headers(headers -> headers.frameOptions().sameOrigin())
                .httpBasic(Customizer.withDefaults())
                .addFilter(customAuthenticationFilter)
                .addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
    }
}