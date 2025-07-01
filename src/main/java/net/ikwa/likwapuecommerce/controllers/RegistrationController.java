package net.ikwa.likwapuecommerce.controllers;

import net.ikwa.likwapuecommerce.DTO.UserRegistrationDto;
import net.ikwa.likwapuecommerce.model.UserModel;
import net.ikwa.likwapuecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "https://laceand-legacy1.vercel.app/")
@RestController
@RequestMapping("/api/registration")
public class RegistrationController {

    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDto userDto) {
        try {
            userService.save(userDto);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e)  {
            e.printStackTrace();
          return ResponseEntity.status(HttpStatus.NOT_FOUND).
                  body("User registration failed");
        }

   }
}
