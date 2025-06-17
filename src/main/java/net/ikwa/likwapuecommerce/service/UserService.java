package net.ikwa.likwapuecommerce.service;

import net.ikwa.likwapuecommerce.DTO.UserRegistrationDto;
import net.ikwa.likwapuecommerce.model.UserModel;
import net.ikwa.likwapuecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public void save(UserRegistrationDto dto) {
        // Validate required fields
        if (dto.getFirstName() == null || dto.getFirstName().isEmpty()
                || dto.getLastName() == null || dto.getLastName().isEmpty()
                || dto.getEmail() == null || dto.getEmail().isEmpty()
                || dto.getPassword() == null || dto.getPassword().isEmpty()
                || dto.getRepeatedPassword() == null || dto.getRepeatedPassword().isEmpty()
                || dto.getCountry() == null || dto.getCountry().isEmpty()) {
            throw new IllegalArgumentException("All fields are required");
        }

        // Check if passwords match
        if (!dto.getPassword().equals(dto.getRepeatedPassword())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        // Check if email already exists
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        // Create UserModel and save
        UserModel user = new UserModel();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword())); // You should hash this!
        user.setCountry(dto.getCountry());

        userRepository.save(user);
    }
}

