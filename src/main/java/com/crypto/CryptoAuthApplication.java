package com.crypto;

import com.crypto.domain.CryptoUser;
import com.crypto.domain.Role;
import com.crypto.service.CryptoUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
public class CryptoAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(CryptoAuthApplication.class, args);
    }

    @Bean
    CommandLineRunner run(CryptoUserService userService) {
        return args -> {
          userService.saveRole(new Role(null, "ROLE_USER"));
          userService.saveRole(new Role(null, "ROLE_MANAGER"));
          userService.saveRole(new Role(null, "ROLE_ADMIN"));
          userService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

          userService.saveUser(new CryptoUser(null, "John Travolta", "john", "1234", List.of()));
          userService.saveUser(new CryptoUser(null, "Will Smith", "will", "1234", List.of()));
          userService.saveUser(new CryptoUser(null, "Jim Carrey", "jim", "1234", List.of()));

          userService.addRoleToUser("jim", "ROLE_USER");
          userService.addRoleToUser("john", "ROLE_SUPER_ADMIN");
          userService.addRoleToUser("will", "ROLE_MANAGER");
          userService.addRoleToUser("will", "ROLE_ADMIN");
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
