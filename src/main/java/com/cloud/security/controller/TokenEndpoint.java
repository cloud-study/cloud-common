package com.cloud.security.controller;

import com.cloud.config.JwtSettings;
import com.cloud.security.config.WebSecurityConfig;
import com.cloud.security.exception.InvalidJwtToken;
import com.cloud.security.extractor.TokenExtractor;
import com.cloud.security.jwt.verifier.TokenVerifier;
import com.cloud.security.model.UserContext;
import com.cloud.security.model.token.JwtToken;
import com.cloud.security.model.token.JwtTokenFactory;
import com.cloud.security.model.token.RawAccessJwtToken;
import com.cloud.security.model.token.RefreshToken;
import com.cloud.security.user.service.MyUserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * RefreshTokenEndpoint
 * 
 * @author vladimir.stankovic
 *
 * Aug 17, 2016
 */
@RestController
public class TokenEndpoint {
    @Autowired
    private JwtTokenFactory tokenFactory;
    @Autowired
    private JwtSettings jwtSettings;
    @Autowired
    private MyUserDetailServiceImpl userService;
    @Autowired
    private TokenVerifier tokenVerifier;
    @Autowired
    @Qualifier("jwtHeaderTokenExtractor")
    private TokenExtractor tokenExtractor;
    
    @RequestMapping(value="/api/auth/refreshToken", method= RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
    public JwtToken refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfig.AUTHENTICATION_HEADER_NAME));
        
        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        RefreshToken refreshToken = RefreshToken.create(rawToken, jwtSettings.getTokenSigningKey()).orElseThrow(() -> new InvalidJwtToken());

        String jti = refreshToken.getJti();
        if (!tokenVerifier.verify(jti)) {
            throw new InvalidJwtToken();
        }

        String subject = refreshToken.getSubject();
//        User user = userService.getByUsername(subject).orElseThrow(() -> new UsernameNotFoundException("User not found: " + subject));
//
//        if (user.getRoles() == null) throw new InsufficientAuthenticationException("User has no roles assigned");
//        List<GrantedAuthority> authorities = user.getRoles().stream()
//                .map(authority -> new SimpleGrantedAuthority(authority.getRole().authority()))
//                .collect(Collectors.toList());
//
//        UserContext userContext = UserContext.create(user.getUsername(), authorities);
//        return tokenFactory.createAccessJwtToken(userContext);

        UserDetails userDetails = userService.loadUserByUsername(subject);
        if(CollectionUtils.isEmpty(userDetails.getAuthorities())){
            throw new InsufficientAuthenticationException("User has no roles assigned");
        }
        UserContext userContext = UserContext.create(userDetails.getUsername(), new ArrayList<>(userDetails.getAuthorities()));
        return tokenFactory.createAccessJwtToken(userContext);

    }

//    @RequestMapping(value="/api/auth/accessToken", method= RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
//    public JwtToken accessToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfig.AUTHENTICATION_HEADER_NAME));
//
//        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
//        RefreshToken refreshToken = RefreshToken.create(rawToken, jwtSettings.getTokenSigningKey()).orElseThrow(() -> new InvalidJwtToken());
//
//        String jti = refreshToken.getJti();
//        if (!tokenVerifier.verify(jti)) {
//            throw new InvalidJwtToken();
//        }
//
//        String subject = refreshToken.getSubject();
////        User user = userService.getByUsername(subject).orElseThrow(() -> new UsernameNotFoundException("User not found: " + subject));
////
////        if (user.getRoles() == null) throw new InsufficientAuthenticationException("User has no roles assigned");
////        List<GrantedAuthority> authorities = user.getRoles().stream()
////                .map(authority -> new SimpleGrantedAuthority(authority.getRole().authority()))
////                .collect(Collectors.toList());
////
////        UserContext userContext = UserContext.create(user.getUsername(), authorities);
////        return tokenFactory.createAccessJwtToken(userContext);
//
//        UserDetails userDetails = userService.loadUserByUsername(subject);
//        if(CollectionUtils.isEmpty(userDetails.getAuthorities())){
//            throw new InsufficientAuthenticationException("User has no roles assigned");
//        }
//        UserContext userContext = UserContext.create(userDetails.getUsername(), new ArrayList<>(userDetails.getAuthorities()));
//        return tokenFactory.createAccessJwtToken(userContext);
//
//    }
}
