package com.sp2.apirestmongo.service;

import com.sp2.apirestmongo.dto.UserDto;
import com.sp2.apirestmongo.model.User;
import com.sp2.apirestmongo.repository.UserRepository;
import com.sp2.apirestmongo.repository.UserRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepositoryImpl userRepository;

    @Test
    public void find_user_by_id(){
        User userMock = new User("Rolando", "rol@ndo.com", "r123", LocalDate.now());
        Mockito.when(userRepository.findById("1")).thenReturn(userMock);
        User userResult = userService.findById("1");

        User userExpected = new User("Rolando", "rol@ndo.com", "r123", LocalDate.now());

        Assertions.assertEquals(userExpected, userResult);
        Assertions.assertEquals(userExpected.getFullName(), userResult.getFullName());


    }

    @Test
    public void update_user(){
        UserDto userUpdated = new UserDto("Rolando", "rol@ndo.com", "r123", LocalDate.now());
        User userMock = new User("Rolando", "rol@ndo.com", "r123", LocalDate.now());
        Mockito.when(userRepository.updateUser("1",userUpdated)).thenReturn(userMock);
        User userResult = userService.updateUser("1", userUpdated);

        User userExpected = new User("Rolando", "rol@ndo.com", "r123", LocalDate.now());

        Assertions.assertEquals(userExpected, userResult);
        Assertions.assertEquals(userExpected.getFullName(), userResult.getFullName());


    }

    @Test
    public void delete_user(){
        Boolean deleteUpdated = true;
        Mockito.when(userRepository.deleteUser("1")).thenReturn(deleteUpdated);
        Boolean userResult = userService.deleteUser("1");

        Boolean userExpected = true;

        Assertions.assertEquals(userExpected, userResult);
        Assertions.assertEquals(userExpected.booleanValue(), userResult.booleanValue());


    }

}