package com.example.sacBack.controllers;


import com.example.sacBack.models.DTOs.UserDTO;
import com.example.sacBack.security.respose.MessageResponse;
import com.example.sacBack.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 600, allowCredentials = "true")
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

   private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/deleteuser/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable long id){
        logger.info("Deleting user with id: {}", id);
        userService.deleteUser(id);
        return ResponseEntity.ok(new MessageResponse("User deleted successfully!"));
    }

    @PutMapping("/user")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO user){
        logger.info("Updating user with id: {}", user.getId());
        userService.updateUser(user);
        return ResponseEntity.ok(new MessageResponse("User updated successfully!"));
    }
}
