package com.le.app.service;

import com.le.app.model.dto.UserProfileDto;
import com.le.app.model.dto.UserRegisterDto;
import com.le.app.model.IrregularVerb;
import com.le.app.repository.UserWithSuchUsernameExistsException;
import com.le.app.security.jwt.JwtUserDetails;
import lombok.extern.slf4j.Slf4j;
import com.le.app.model.Role;
import com.le.app.model.Status;
import com.le.app.model.User;
import com.le.app.repository.RoleRepository;
import com.le.app.repository.UserRepository;

import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Transactional
@Service
// @Cacheable
public class UserService {
    @Autowired
    private IrregularVerbService irregularVerbService;
    //    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //@Transactional()
    public User register(UserRegisterDto userRegisterDto) {
        User user = userRegisterDto.toUser();

        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);
        user.setCreated(LocalDateTime.now());
        user.setUpdated(LocalDateTime.now());

        User registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser);

        return registeredUser;
    }

    @Transactional(readOnly = true)
    public List<User> findAll() {
        List<User> result = userRepository.findAll();
        log.info("IN findAll - {} users found", result.size());
        return result;
    }

    @Transactional(readOnly = true)
    public User findByUsernameWithRoles(String username) {
        User result = userRepository.findByUsernameWithRoles(username);
        System.out.println("-----------------IN findByUsernameWithRoles - user: " + result);
        log.info("IN findByUsernameWithRoles - user: {} found by username: {}", result, username);
        return result;
    }

    @Transactional(readOnly = true)
    public User findByUsernameWithoutRoles(String username) {
        User result = userRepository.findByUsername(username);
        System.out.println("-----------------IN findByUsername - user: " + result);
        log.info("IN findByUsernameWithoutRoles - user: {} found by username: {}", result, username);
        return result;
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);

        if (result == null) {
            log.warn("IN findById - no user found by id: {}", id);
            return null;
        }

        log.info("IN findById - user: {} found by id: {}", result);
        return result;
    }

    //@Transactional()
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


    public User findLoginedUser() {
        UserDetails principal = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        String username = principal.getUsername();
        User loginedUser = findByUsernameWithoutRoles(username);
        return loginedUser;
    }

    public UserProfileDto getUserProfile() {
        User loginedUser = findLoginedUser();
        UserProfileDto userProfileDto = UserProfileDto.fromUser(loginedUser);
        return userProfileDto;
    }

    //@Transactional
    public void updateUserProfile(UserProfileDto newUserProfile) { // Todo handle password change
        log.info("IN updateUserProfile before findLoginedUser");
        User loginedUser = findLoginedUser();
        log.info("IN updateUserProfile - loginedUser: {}");

        if (isUsernameWasChanged(loginedUser, newUserProfile)
                && isUpdatedUsernameNotUnique(newUserProfile.getUsername())) {
            log.info("IN updateUserProfile: " + "throw new UserWithSuchUsernameExistsException");
            throw new UserWithSuchUsernameExistsException("User with such Username exists: " + newUserProfile.getUsername());
        } else {
            loginedUser.setUsername(newUserProfile.getUsername());
            loginedUser.setEmail(newUserProfile.getEmail());
            loginedUser.setFirstName(newUserProfile.getFirstName());
            loginedUser.setLastName(newUserProfile.getLastName());
            loginedUser.setUpdated(LocalDateTime.now());
        }
    }


    private boolean isUsernameWasChanged(User loginedUser, UserProfileDto newUserProfile) {
        log.info("IN isUsernameWasChanged: " + !loginedUser.getUsername().equals(newUserProfile.getUsername()));

        return !loginedUser.getUsername().equals(newUserProfile.getUsername());

    }

    private boolean isUpdatedUsernameNotUnique(String username) {
        log.info("IN checkUniquenessOfUsername");
        if (userRepository.findByUsername(username) == null) {
            log.info("IN isUpdatedUsernameNotUnique: " + false);
            return false;
        } else {
            log.info("IN isUpdatedUsernameNotUnique: " + true);
            return true;}
        }




    // @Transactional
    public void addIrregularVerbToLearnt(Long id) {
        User loginedUser = findLoginedUser();
        IrregularVerb irregularVerb = irregularVerbService.findById(id).get();
        loginedUser.addIrregularVerbToLearnt(irregularVerb);
    }

    // @Transactional
    public void removeIrregularVerbFromLearnt(Long id) {
        User loginedUser = findLoginedUser();
        IrregularVerb irregularVerb = irregularVerbService.findById(id).get();
        loginedUser.removeIrregularVerbFromLearnt(irregularVerb);
    }


}
