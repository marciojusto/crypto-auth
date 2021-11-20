package com.crypto.service;

import com.crypto.domain.CryptoUser;
import com.crypto.domain.Role;

import java.util.List;

public interface CryptoUserService {

    public CryptoUser saveUser(CryptoUser user);

    public Role saveRole(Role role);

    public void addRoleToUser(String username, String roleName);

    public CryptoUser getUser(String username);

    public List<CryptoUser> getUsers();
}
