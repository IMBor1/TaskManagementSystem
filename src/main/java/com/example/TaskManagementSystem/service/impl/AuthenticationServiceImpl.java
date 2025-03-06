package com.example.TaskManagementSystem.service.impl;

import com.example.TaskManagementSystem.config.JwtTokenProvider;
import com.example.TaskManagementSystem.model.dto.AuthRequestDto;
import com.example.TaskManagementSystem.model.dto.AuthResponseDto;
import com.example.TaskManagementSystem.model.entity.User;
import com.example.TaskManagementSystem.repository.UserRepository;
import com.example.TaskManagementSystem.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public AuthResponseDto login(AuthRequestDto authRequest) {
        User user = userRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            throw new UsernameNotFoundException("Invalid password");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user, null, List.of(new SimpleGrantedAuthority(user.getRole().name()))
        );
        String token = jwtTokenProvider.generateToken(authentication);

        return new AuthResponseDto(token);

    }
}