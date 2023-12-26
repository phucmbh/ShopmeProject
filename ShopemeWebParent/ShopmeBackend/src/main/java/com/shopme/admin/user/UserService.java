package com.shopme.admin.user;


import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    // Reference to the UserRepository, @Autowired to let Spring inject an instance,
    // an implementation of UserRepository at runtime.
    @Autowired
    private UserRepository userRepo;


    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    public UserService(RoleRepository roleRepo, PasswordEncoder passwordEncoder) {
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }


    public List<User> listAll() {
        return (List<User>) userRepo.findAll();
    }

    public List<Role> listRoles() {
        return (List<Role>) roleRepo.findAll();
    }

    public void save(User user) {
        encodePassword(user);
        userRepo.save(user);
    }

    public void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }
}
