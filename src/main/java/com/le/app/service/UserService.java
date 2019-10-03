package com.le.app.service;

import com.le.app.dto.UserDto;
import com.le.app.model.IrregularVerb;
import com.le.app.security.jwt.JwtUserDetails;
import lombok.extern.slf4j.Slf4j;
import com.le.app.model.Role;
import com.le.app.model.Status;
import com.le.app.model.User;
import com.le.app.repository.RoleRepository;
import com.le.app.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserService {
    @Autowired
    private IrregularVerbService irregularVerbService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(UserDto userDto) {
        User user = userDto.toUser();

        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);
//        user.setCreated(LocalDateTime.now());
//        user.setUpdated(LocalDateTime.now());


        User registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }

    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        log.info("IN getAll - {} users found", result.size());
        return result;
    }

    public User findByUsername(String username) {
        User result = userRepository.findByUsername(username);
        log.info("IN findByUsername - user: {} found by username: {}", result, username);
        return result;
    }

    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN findById - no user found by id: {}", id);
            return null;
        }

        log.info("IN findById - user: {} found by id: {}", result);
        return result;
    }


    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("IN delete - user with id: {} successfully deleted");
    }


    public boolean isRequestFromLoginedUser() {
        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        log.info("IN isRequestFromLoginedUser - principal: {}");
        if (principal instanceof JwtUserDetails) {
            log.info("IN isRequestFromLoginedUser - principal: {}");
            return true;
        } else {
            log.info("IN isRequestFromLoginedUser - user is not Logined");
            return false;
        }
    }


    public User getLoginedUser() {
        UserDetails principal = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        String username = principal.getUsername();
        User loginedUser = findByUsername(username);
        return loginedUser;
    }

     @Transactional
    public void addIrregularVerbToLearnt(Long id) {
        User loginedUser = getLoginedUser();
        IrregularVerb irregularVerb = irregularVerbService.findById(id).get();
        loginedUser.addIrregularVerbToLearnt(irregularVerb);
    }

     @Transactional
    public void removeIrregularVerbFromLearnt(Long id) {
        User loginedUser = getLoginedUser();
        IrregularVerb irregularVerb = irregularVerbService.findById(id).get();
        loginedUser.removeIrregularVerbFromLearnt(irregularVerb);
    }

}
