package com.example.sacBack.controllers;


import com.example.sacBack.security.models.User;
import com.example.sacBack.repositories.RoleRepository;
import com.example.sacBack.repositories.UserRepository;
import com.example.sacBack.security.respose.MessageResponse;
import com.example.sacBack.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200/", maxAge = 600, allowCredentials = "true")
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

   private final UserService userService;
   private final RoleRepository roleRepository;
   private final PasswordEncoder encoder;

    public AdminController(UserService userService, RoleRepository roleRepository, PasswordEncoder encoder) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/deleteuser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id){
        logger.info("Deleting user with id: " + id);
        userService.deleteUser(id);
        return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));
    }
}
