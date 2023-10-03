/*
package com.challang.userdevicemanagement.service

import com.challang.userdevicemanagement.dto.DeviceDto
import com.challang.userdevicemanagement.dto.UserDto
import com.challang.userdevicemanagement.exception.UserNotFoundException
import com.challang.userdevicemanagement.repository.DeviceRepository
import com.challang.userdevicemanagement.repository.UserRepository
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import java.time.LocalDate
import java.util.Optional


class UserServiceTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var deviceRepository: DeviceRepository

    private lateinit var userService: UserService

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        userService = UserService(userRepository, deviceRepository)
    }

    @Test
    fun testGetAllUsers() {
        val users = listOf(UserDto(1L, "John", "Doe", "123 Main St", LocalDate.of(1990, 1, 1)),
            UserDto(2L, "John", "Doe", "123 Main St", LocalDate.of(1990, 1, 1)))
        `when`(userRepository.findAll()).thenReturn(users)

        val result = userService.getAllUsers()

        assertEquals(users.map { UserDto.convertToUserDto(it) }, result)
    }

    @Test
    fun testGetUserById() {
        val userId = 1L
        val user = UserDto(*/
/* Initialize with user data *//*
)
        `when`(userRepository.findById(userId)).thenReturn(Optional.of(UserDto.convertToUser(user)))

        val result = userService.getUserById(userId)

        assertEquals(user, result)
    }

    @Test
    fun testGetUserByIdNotFound() {
        val userId = 1L
        `when`(userRepository.findById(userId)).thenReturn(Optional.empty())

        assertFailsWith<UserNotFoundException> {
            userService.getUserById(userId)
        }
    }

    @Test
    fun testAddUser() {
        val userDto = UserDto(*/
/* Initialize with user data *//*
)
        val user = UserDto.convertToUser(userDto)
        `when`(userRepository.saveAndFlush(user)).thenReturn(user)

        val result = userService.add(userDto)

        assertEquals(userDto, result)
    }

    @Test
    fun testDeleteUser() {
        val userId = 1L
        `when`(userRepository.existsById(userId)).thenReturn(true)

        userService.deleteUser(userId)

        verify(userRepository).deleteById(userId)
    }

    @Test
    fun testDeleteUserNotFound() {
        val userId = 1L
        `when`(userRepository.existsById(userId)).thenReturn(false)

        assertFailsWith<UserNotFoundException> {
            userService.deleteUser(userId)
        }
    }

    @Test
    fun testAddDevice() {
        val userId = 1L
        val deviceDto = DeviceDto(*/
/* Initialize with device data *//*
)
        val user = UserDto(*/
/* Initialize with user data *//*
)
        `when`(userRepository.findById(userId)).thenReturn(Optional.of(UserDto.convertToUser(user)))
        `when`(deviceRepository.save(deviceDto)).thenReturn(DeviceDto.convertToDevice(deviceDto))

        userService.addDevice(userId, deviceDto)

        verify(deviceRepository).save(deviceDto)
        assertEquals(user, deviceDto.user)
    }


}
*/
