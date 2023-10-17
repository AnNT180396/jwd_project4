package com.example.demo.controllers;

import com.example.demo.InjectMockUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {
    public static final String USER_NAME = "AnNT";
    public static final String PASSWORD = "Password";
    public static final String IS_HASHED = "isHashed";
    private UserController userController;

    private final UserRepository userRepository = mock(UserRepository.class);

    private final CartRepository cartRepository = mock(CartRepository.class);

    private final BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp() {
        userController = new UserController();
        InjectMockUtils.inject(userController, "userRepository", userRepository);
        InjectMockUtils.inject(userController, "cartRepository", cartRepository);
        InjectMockUtils.inject(userController, "bCryptPasswordEncoder", encoder);
    }

    @Test
    public void testCreateUser() {
        when(encoder.encode(PASSWORD)).thenReturn(IS_HASHED);
        CreateUserRequest requestCreate = new CreateUserRequest();
        requestCreate.setUsername(USER_NAME);
        requestCreate.setUsername(USER_NAME);
        requestCreate.setPassword("Password");
        requestCreate.setConfirmPassword("Password");
        final ResponseEntity<User> response = userController.createUser(requestCreate);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        User user = response.getBody();
        assertNotNull(user);
        assertEquals(USER_NAME, user.getUsername());
        assertEquals(IS_HASHED, user.getPassword());
    }

    @Test
    public void testFindById() {
        when(encoder.encode(PASSWORD)).thenReturn(IS_HASHED);
        CreateUserRequest requestCreate = new CreateUserRequest();
        requestCreate.setUsername(USER_NAME);
        requestCreate.setPassword(PASSWORD);
        requestCreate.setConfirmPassword(PASSWORD);
        final ResponseEntity<User> response = userController.createUser(requestCreate);
        User user = response.getBody();
        when(userRepository.findById(0L)).thenReturn(java.util.Optional.ofNullable(user));
        ResponseEntity<User> userResponse = userController.findById(0L);

        User userResponseData = userResponse.getBody();
        assertNotNull(userResponseData);
        assertEquals(USER_NAME, userResponseData.getUsername());
        assertEquals(IS_HASHED, userResponseData.getPassword());
    }

    @Test
    public void testFindByUserName() {
        when(encoder.encode(PASSWORD)).thenReturn(IS_HASHED);
        CreateUserRequest requestCreate = new CreateUserRequest();
        requestCreate.setUsername(USER_NAME);
        requestCreate.setPassword(PASSWORD);
        requestCreate.setConfirmPassword(PASSWORD);
        final ResponseEntity<User> response = userController.createUser(requestCreate);
        User user = response.getBody();
        when(userRepository.findByUsername(USER_NAME)).thenReturn(user);

        final ResponseEntity<User> userResponse = userController.findByUserName(USER_NAME);

        User userResponseData = userResponse.getBody();
        assertNotNull(userResponseData);
        assertEquals(USER_NAME, userResponseData.getUsername());
        assertEquals(IS_HASHED, userResponseData.getPassword());
    }
}