package com.crypto.api;

import com.crypto.domain.CryptoUser;
import com.crypto.domain.Role;
import com.crypto.service.CryptoUserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/1")
@Slf4j
public class CryptoUserResource {

    private final CryptoUserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<CryptoUser>> getUsers() {
        log.info("Getting user list");
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/user/save")
    public ResponseEntity<CryptoUser> saveUser(@RequestBody CryptoUser user) {
        log.info("Saving user {}", user.getName());
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/1/user/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role) {
        log.info("Saving role {}", role.getName());
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/1/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("/role/add-to-user")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleForm form) {
        log.info("Adding role {} to user {}", form.getRolename(),form.getUsername());
        userService.addRoleToUser(form.getUsername(), form.getRolename());
        return ResponseEntity.ok().build();
    }

}

@Data
class RoleForm {
    private String username;
    private String rolename;
}
