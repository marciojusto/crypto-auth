package com.crypto.service;

import com.crypto.domain.CryptoUser;
import com.crypto.domain.Role;
import com.crypto.repository.CryptoUserRepository;
import com.crypto.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CryptoUserServiceImpl implements CryptoUserService, UserDetailsService {

    private final CryptoUserRepository cryptoUserRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CryptoUser cryptoUser = Optional.ofNullable(cryptoUserRepository.findByUsername(username)).orElseThrow(() -> {
            log.error("User {} not found!", username);
            return new UsernameNotFoundException("User not found");
        });
        log.info("User {} found!",  username);
        return new User(cryptoUser.getUsername(), cryptoUser.getPassword(), cryptoUser.getRoles()
                                                                                      .stream()
                                                                                      .map(role -> new SimpleGrantedAuthority(role.getName())).toList());
    }

    @Override
    public CryptoUser saveUser(CryptoUser user) {
        log.info("Saving new user {}", user.getName());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return cryptoUserRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving new role {}", role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        CryptoUser user = cryptoUserRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public CryptoUser getUser(String username) {
        log.info("Fetching user {}", username);
        return cryptoUserRepository.findByUsername(username);
    }

    @Override
    public List<CryptoUser> getUsers() {
        log.info("Fetching all users");
        return cryptoUserRepository.findAll();
    }
}
