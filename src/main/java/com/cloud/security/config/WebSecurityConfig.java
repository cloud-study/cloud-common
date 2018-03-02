package com.cloud.security.config;

import com.cloud.security.auth.ajax.AjaxAuthenticationProvider;
import com.cloud.security.auth.ajax.AjaxLoginProcessingFilter;
import com.cloud.security.extractor.TokenExtractor;
import com.cloud.security.filter.JwtAuthenticationProvider;
import com.cloud.security.filter.JwtTokenAuthenticationProcessingFilter;
import com.cloud.security.filter.SkipPathRequestMatcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;
import java.util.List;

/**
 * WebSecurityConfig
 * 
 * @author vladimir.stankovic
 *
 * Aug 3, 2016
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  //开启Spring方法级安全
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    public static final String AUTHENTICATION_HEADER_NAME = "Authorization";
    public static final String AUTHENTICATION_URL = "/api/auth/login";
    public static final String REFRESH_TOKEN_URL = "/api/auth/token";
    public static final String API_ROOT_URL = "/api/**";
//
//    @Autowired
//    private RestAuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private AuthenticationSuccessHandler successHandler;
    @Autowired
    private AuthenticationFailureHandler failureHandler;
    @Autowired
    private AjaxAuthenticationProvider ajaxAuthenticationProvider;
    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    private TokenExtractor tokenExtractor;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ObjectMapper objectMapper;


    protected JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter() throws Exception {
//        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, pattern);
        AntPathRequestMatcher matcher = new AntPathRequestMatcher("/api/**/sec/**");
        JwtTokenAuthenticationProcessingFilter filter
            = new JwtTokenAuthenticationProcessingFilter(failureHandler, tokenExtractor, matcher);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    protected AjaxLoginProcessingFilter buildAjaxLoginProcessingFilter(String loginEntryPoint) throws Exception {
        AjaxLoginProcessingFilter filter = new AjaxLoginProcessingFilter(loginEntryPoint, successHandler, failureHandler, objectMapper);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(ajaxAuthenticationProvider);
        auth.authenticationProvider(jwtAuthenticationProvider);
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // We don't need CSRF for JWT based authentication
                .exceptionHandling()
//                .authenticationEntryPoint(this.authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/**/sec/**").authenticated()
                .anyRequest().permitAll()
                .and()
                .addFilterBefore(buildAjaxLoginProcessingFilter(AUTHENTICATION_URL), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(buildJwtTokenAuthenticationProcessingFilter(),UsernamePasswordAuthenticationFilter.class);
    }
}
