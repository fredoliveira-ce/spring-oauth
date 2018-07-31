package com.fredoliveira.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class OAuth2SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.ignoring()
                .mvcMatchers("/favicon.ico", "/webjars/**", "/css/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers().antMatchers("/oauth/authorize")
                .and()
                    .authorizeRequests()
                    .antMatchers("/oauth/authorize").authenticated()
                .and()
                    .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password("password")
                .roles("USER")
                .and()
                .withUser("manager")
                .password("password")
                .credentialsExpired(true)
                .accountExpired(true)
                .accountLocked(true)
                .authorities("WRITE_PRIVILEGES", "READ_PRIVILEGES")
                .roles("MANAGER");
    }

    @SuppressWarnings("deprecation")
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}

