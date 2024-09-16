package de.student.screen_sharer_application.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@Profile("!test")
@EnableWebSecurity

public class WebSecurityConfig{

    @Autowired
    UserDetailsService userDetailsService;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.authorizeRequests().antMatchers("/css/**","/js/**","/img/**","/register","/login")
                .permitAll()
                .antMatchers("/index/friend/add").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable().cors().and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/index/main")
                .permitAll()
                .and()
                .logout()
                .clearAuthentication(true)
                .permitAll()
                .and()
                .userDetailsService(userDetailsService)
                .build();
    }
}

