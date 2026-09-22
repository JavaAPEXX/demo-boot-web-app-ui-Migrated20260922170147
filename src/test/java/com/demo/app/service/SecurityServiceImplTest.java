package com.demo.app.service;

import com.demo.app.service.SecurityService;
import com.demo.app.service.SecurityServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SecurityServiceImplTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private SecurityServiceImpl securityServiceImpl;

    @Test
    @DisplayName("givenValidInput_whenSecurityServiceImpl_thenReturnSuccess")
    void givenValidInput_whenSecurityServiceImpl_thenReturnSuccess() {
        // Arrange
        String username = "testUser";
        String password = "testPassword";

        when(userDetailsService.loadUserByUsername(username)).thenReturn(null);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(true);

        // Act
        securityServiceImpl.autologin(username, password);

        // Assert
        assertEquals(username, securityServiceImpl.findLoggedInUsername());
    }

    @Test
    @DisplayName("givenInvalidInput_whenSecurityServiceImpl_thenThrowUsernameNotFoundException")
    void givenInvalidInput_whenSecurityServiceImpl_thenThrowUsernameNotFoundException() {
        // Arrange
        String username = "nonExistingUser";
        String password = "testPassword";

        when(userDetailsService.loadUserByUsername(username)).thenThrow(UsernameNotFoundException.class);

        // Act and Assert
        assertThrows(UsernameNotFoundException.class, () -> securityServiceImpl.autologin(username, password));
    }

    @Test
    @DisplayName("givenNullInput_whenSecurityServiceImpl_thenThrowIllegalArgumentException")
    void givenNullInput_whenSecurityServiceImpl_thenThrowIllegalArgumentException() {
        // Arrange
        String username = null;
        String password = "testPassword";

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> securityServiceImpl.autologin(username, password));
    }

    @Test
    @DisplayName("givenEmptyDatabase_whenSecurityServiceImpl_thenReturnEmptyList")
    void givenEmptyDatabase_whenSecurityServiceImpl_thenReturnEmptyList() {
        // Arrange
        when(userDetailsService.loadUserByUsername("testUser")).thenReturn(null);

        // Act
        String loggedInUsername = securityServiceImpl.findLoggedInUsername();

        // Assert
        assertEquals("", loggedInUsername);
    }

    @Test
    @DisplayName("givenExistingUser_whenSecurityServiceImpl_thenReturnExistingUser")
    void givenExistingUser_whenSecurityServiceImpl_thenReturnExistingUser() {
        // Arrange
        String username = "testUser";
        when(userDetailsService.loadUserByUsername(username)).thenReturn(new org.springframework.security.core.userdetails.User("testUser", "testPassword", org.springframework.security.core.authority.SimpleGrantedAuthority("USER")));

        // Act
        String loggedInUsername = securityServiceImpl.findLoggedInUsername();

        // Assert
        assertEquals(username, loggedInUsername);
    }
}