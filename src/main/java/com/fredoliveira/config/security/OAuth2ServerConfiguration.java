package com.fredoliveira.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
public class OAuth2ServerConfiguration {

    private static final String RESOURCE_ID = "com.fredoliveira.springoauth";

    private static final String CLIENT_ID = "com.fredoliveira.springoauth";

    private static final String CLIENT_SECRET = "FO#xCg8Kh^UAGa3vC";

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        @Autowired
        private OAuthLogoutSuccessHandler ajaxLogoutSuccessHandler;

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) {
            resources.resourceId(RESOURCE_ID);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.logout()
                        .logoutUrl("/api/logout")
                        .logoutSuccessHandler(ajaxLogoutSuccessHandler)
                    .and()
                        .sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                        .authorizeRequests()
                        .antMatchers("/api/**").authenticated()
                        .antMatchers("/resources/**").permitAll()
                        .antMatchers("/oauth/authorize").authenticated()
                    .and();
        }

    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Bean
        public TokenStore tokenStore() {
            return new InMemoryTokenStore();
        }

        @Autowired
        private AuthenticationManager authenticationManager;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints
                    .tokenStore(tokenStore())
                    .authenticationManager(authenticationManager);
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients
                    .inMemory()
                    .withClient(CLIENT_ID)
                    .authorizedGrantTypes("password", "refresh_token")
                    .scopes("read", "write")
                    .resourceIds(RESOURCE_ID)
                    .secret(CLIENT_SECRET);
        }

        @Bean
        @Primary
        public DefaultTokenServices tokenServices() {
            DefaultTokenServices tokenServices = new DefaultTokenServices();
            tokenServices.setSupportRefreshToken(true);
            tokenServices.setTokenStore(tokenStore());
            return tokenServices;
        }
    }
}
