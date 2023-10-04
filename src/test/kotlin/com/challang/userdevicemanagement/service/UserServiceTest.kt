package com.challang.userdevicemanagement.service

import com.challang.userdevicemanagement.dto.DeviceDto
import com.challang.userdevicemanagement.dto.UserDto
import com.challang.userdevicemanagement.entity.Device
import com.challang.userdevicemanagement.entity.User
import com.challang.userdevicemanagement.exception.SerialNumberNotUniqException
import com.challang.userdevicemanagement.exception.UserNotFoundException
import com.challang.userdevicemanagement.repository.DeviceRepository
import com.challang.userdevicemanagement.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import java.time.LocalDate
import java.util.*


@ExtendWith(MockitoExtension::class)
class UserServiceTest {

    @InjectMocks
    private lateinit var userServiceUnderTest: UserService

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var deviceRepository: DeviceRepository

    @Test
    @DisplayName("get All employee")
    fun whenGetAll_thenReturnAllUser() {
        val user1 = User(1L, "John", "Doe", "123 Main St", LocalDate.of(1990, 1, 1))
        val user2 = User(2L, "John", "Doe", "123 Main St", LocalDate.of(1990, 1, 1))
        val users = listOf(user1, user2)
        `when`(userRepository.findAll()).thenReturn(users)

        val expected = userServiceUnderTest.getAllUsers()
        assertEquals(2, expected.size)
        assertThat(expected).isNotEmpty()
    }

    @Test
    @DisplayName("get user")
    fun whenGetUserById_thenReturnUser() {
        val userId = 1L
        val user = User(1L, "John", "Doe", "123 Main St", LocalDate.of(1990, 1, 1))
        `when`(userRepository.findById(userId)).thenReturn(Optional.of(user))

        val userDto = userServiceUnderTest.getUserById(userId)

        assertThat(userDto).isNotNull()
    }

    @Test
    @DisplayName("get user by Id and then throw exception")
    fun whenGetUserById_thenReturnUserNotFound() {
        val userId = 1L
        val userOptional: Optional<User> = Optional.empty()

        `when`(userRepository.findById(userId)).thenReturn(userOptional)

        Assertions.assertThrows(UserNotFoundException::class.java) {
            userServiceUnderTest.getUserById(userId)
        }
    }

    @Test
    @DisplayName("add user successful")
    fun whenAddUser_thenReturnUserDto() {
        val userDto = UserDto(null, "John", "Doe", "123 Main St", LocalDate.of(1990, 1, 1))
        val user = User(1L, "John", "Doe", "123 Main St", LocalDate.of(1990, 1, 1))
        `when`(userRepository.saveAndFlush(any(User::class.java))).thenReturn(user)

        val addedUserDto = userServiceUnderTest.add(userDto)

        assertThat(addedUserDto).isNotNull()
    }

    @Test
    @DisplayName("delete user successful")
    fun whenDeleteUserById_thenUnit() {
        val userId = 1L
        `when`(userRepository.existsById(userId)).thenReturn(true)

        userServiceUnderTest.deleteUser(userId)

        verify(userRepository, times(1)).deleteById(userId)
    }

    @Test
    @DisplayName("delete user by Id and then throw exception")
    fun whenDeleteUserById_thenReturnUserNotFound() {
        val userId = 1L
        `when`(userRepository.existsById(userId)).thenReturn(false)

        assertThatThrownBy { userServiceUnderTest.deleteUser(userId) }
            .isInstanceOf(UserNotFoundException::class.java)
    }

    @Test
    @DisplayName("assign device to user successful")
    fun assignDevice () {
        val deviceDto = DeviceDto(
            null, "sdsd1322", "39822545-e35d-4445-80a5-64336b59f166",
            "004915784765517", "mobile", null
        )
        val userId = 1L
        val user = User(userId, "John", "Doe", "123 Main St", LocalDate.of(1990, 1, 1))
        val device = Device(
            1L, "sdsd1322", "39822545-e35d-4445-80a5-64336b59f166",
            "004915784765517", "mobile", user
        )

        `when`(userRepository.findById(userId)).thenReturn(Optional.of(user))
        `when`(deviceRepository.save(any(Device::class.java))).thenReturn(device)

        userServiceUnderTest.assignDevice(userId, deviceDto)

        verify(deviceRepository, times(1)).save(any(Device::class.java))
    }

    @Test
    @DisplayName("assign device to user successful")
    fun assignDeviceWhenSerialNumberNotUnique_ThenThrownByException() {
        val userId = 1L
        val deviceDto = DeviceDto(
            null, "sdsd1322", "39822545-e35d-4445-80a5-64336b59f166",
            "004915784765517", "mobile", null
        )
        val user = User(userId, "John", "Doe", "123 Main St", LocalDate.of(1990, 1, 1))
        `when`(userRepository.findById(userId)).thenReturn(Optional.of(user))
                `when` (deviceRepository.save(any(Device::class.java))).thenThrow(DataIntegrityViolationException("Duplicate entry"))

                assertThatThrownBy { userServiceUnderTest.assignDevice(userId, deviceDto) }
            .isInstanceOf(SerialNumberNotUniqException::class.java)
    }

       @Test
       @DisplayName("find all users devices successful")
       fun findAllUsersDevices() {
           val pageNumber = 1
           val pageSize = 10
           val pageable: Pageable = PageRequest.of(pageNumber - 1, pageSize)
           val user1 = User(1L, "John", "Doe", "123 Main St", LocalDate.of(1990, 1, 1))
           val user2 = User(2L, "John", "Doe", "123 Main St", LocalDate.of(1990, 1, 1))
           val users = listOf(user1, user2)
        `when`(userRepository.findAllUsersDevices(pageable)).thenReturn(users)

        val userDevices = userServiceUnderTest.findAllUsersDevices(pageNumber, pageSize)

        assertThat(userDevices).isNotEmpty()
    }
}
