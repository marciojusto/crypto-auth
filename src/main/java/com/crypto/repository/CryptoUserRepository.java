package com.crypto.repository;

import com.crypto.domain.CryptoUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoUserRepository extends JpaRepository<CryptoUser, Long> {
    CryptoUser findByUsername(String username);
}
