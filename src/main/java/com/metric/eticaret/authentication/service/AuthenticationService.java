package com.metric.eticaret.authentication.service;


import com.metric.eticaret.authentication.config.JwtTokenUtil;
import com.metric.eticaret.authentication.model.LoginRequest;
import com.metric.eticaret.exception.domain.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;
    private final CustemUserDetailsService custemUserDetailsService;

    public String authenticate(LoginRequest loginRequest) throws NotFoundException {
        try {
            authenticationManager.
                    authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new NotFoundException("Username or password incorrect. Please try again");
        }

        UserDetails userDetails = custemUserDetailsService.loadUserByUsername(loginRequest.getUsername());

        return jwtTokenUtil.generateToken(userDetails);

    }
}
