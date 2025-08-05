package com.example.sacBack.controllers;

import com.example.sacBack.models.DTOs.SkillDTO;
import com.example.sacBack.models.DTOs.TeacherProfileDTO;
import com.example.sacBack.models.ntities.User;
import com.example.sacBack.repositories.UserRepository;
import com.example.sacBack.security.JWT.JwtUtils;
import com.example.sacBack.security.request.LoginRequest;
import com.example.sacBack.security.request.SignupRequest;
import com.example.sacBack.security.respose.MessageResponse;
import com.example.sacBack.services.UserDetailsImpl;
import com.example.sacBack.services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 600, allowCredentials = "true")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);


    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          PasswordEncoder encoder, JwtUtils jwtUtils, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            String jwt = jwtUtils.generateTokenFromUsername(userDetails.getUsername());

            logger.info("Generated JWT for user '{}'", userDetails.getUsername());

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .toList();

            // Возвращаем JWT в теле ответа
            return ResponseEntity.ok(Map.of(
                    "token", jwt,
                    "type", "Bearer",
                    "id", userDetails.getId(),
                    "username", userDetails.getUsername(),
                    "roles", roles
            ));

        } catch (BadCredentialsException ex) {
            logger.warn("Authentication failed for user '{}'", loginRequest.getUsername());
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse("Ошибка: неверное имя пользователя или пароль"));
        } catch (AuthenticationException ex) {
            logger.error("Authentication error: {}", ex.getMessage(), ex);
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse("Ошибка аутентификации: " + ex.getMessage()));
        } catch (Exception ex) {
            logger.error("Internal error during authentication", ex);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Внутренняя ошибка сервера"));
        }
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        // При использовании авторизации через заголовок JWT, logout — это просто удаление токена на клиенте
        logger.info("Logout requested - client should delete JWT");
        return ResponseEntity.ok(new MessageResponse("Вы вышли из системы"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userService.getUserByUserName(signUpRequest.getUsername()).isEmpty()) {
            logger.info("Username already exists");
            return ResponseEntity.badRequest().body(
                    Map.of(
                            "success", false,
                            "errorCode", "USERNAME_TAKEN",
                            "message", "Имя пользователя уже занято."
                    )
            );
        }
        User user = new User(
                signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword())
        );
        userService.save(user);
        logger.info("User created successfully: {}", user.getUsername());
        return ResponseEntity.ok(
                Map.of(
                        "success", true,
                        "message", "Пользователь зарегистрирован успешно.",
                        "username", user.getUsername()
                )
        );
    }



}
