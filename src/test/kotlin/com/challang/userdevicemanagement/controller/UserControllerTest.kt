package com.challang.userdevicemanagement.controller

import com.challang.userdevicemanagement.dto.DeviceDto
import com.challang.userdevicemanagement.dto.UserDto
import com.challang.userdevicemanagement.exception.SerialNumberNotUniqException
import com.challang.userdevicemanagement.exception.UserNotFoundException
import com.challang.userdevicemanagement.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDate


@WebMvcTest
class UserControllerTest(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    lateinit var userService: UserService
    var mapper = ObjectMapper().registerModule(JavaTimeModule())
    val userDto = UserDto(null, "John", "Doe", "123 Main St", LocalDate.of(1990, 1, 1))
    val user1Dto = UserDto(null, "John", "Doe", "123 Main St", LocalDate.of(1990, 1, 1))
    val user2Dto = UserDto(null, "Jon", "Doe", "123 Main St", LocalDate.of(1990, 1, 1))

    val deviceDto = DeviceDto(
        null, "sdsd1322", "39822545-e35d-4445-80a5-64336b59f166",
        "004915784765517", "mobile", null
    )
    @Test
    fun givenAllUser_whenGetAllRequest_thenReturnsUserJsonWithStatus200() {
        val users = listOf(user1Dto, user2Dto)
        every { userService.getAllUsers() } returns users

        mockMvc.perform(get("/users"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].firstName").value("John"))
            .andExpect(jsonPath("$[1].firstName").value("Jon"))
    }

    @Test
    fun givenExistingUserId_whenGetRequest_thenReturnsUserJsonWithStatus200() {
        every { userService.getUserById(1L) } returns userDto

        mockMvc.perform(get("/users/1"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.firstName").value("John"))
    }

    @Test
    fun givenExistingUserId_whenGetRequest_thenReturnsStatus404() {
        val userId = 2L
        every { userService.getUserById(userId) } throws UserNotFoundException(userId)

        mockMvc.perform(get("/users/2"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun whenPostRequestWithUser_whenPostRequest_thenReturnsStatus200() {
        every { userService.add(userDto) } returns userDto

        mockMvc.perform(
            post("/users")
                .content(mapper.writeValueAsString(userDto))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.firstName").value("John"))
    }

    @Test
    fun givenExistingUserId_whenDeleteRequest_thenReturnsUserJsonWithStatus200() {
        every { userService.deleteUser(1L) } returns Unit

        mockMvc.perform(delete("/users/1"))
            .andExpect(status().isNoContent)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    }

    /*    @Test
        fun givenExistingUserId_whenDeleteRequest_thenReturnsUserJsonWithStatus404() {
            val userId = 2L
            every { userService.deleteUser(userId) } throws UserNotFoundException(userId)

            mockMvc.perform(get("/users/2"))
                .andExpect(status().isNotFound)
        }*/

    @Test
    fun whenPutRequestWithDeviceDto_AssignDeviceUser_thenReturnsStatus200() {
        val userId = 1L
        every { userService.assignDevice(1L, deviceDto) } returns Unit

        mockMvc.perform(
            put("/users/assign/device")
                .param("userId", userId.toString())
                .content(mapper.writeValueAsString(deviceDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
    }

    @Test
    fun whenPutRequestWithExistingSerialnumber_AssignDeviceUser_thenReturnsStatus400() {
        val userId = 1L
        every { userService.assignDevice(1L, deviceDto) } throws SerialNumberNotUniqException(deviceDto.serialNumber)

        mockMvc.perform(
            put("/users/assign/device")
                .param("userId", userId.toString())
                .content(mapper.writeValueAsString(deviceDto))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
    }

    @Test
    fun whenGetRequestWith_getUsersDevices_thenReturnsStatus200() {
        val pageNumber = 1
        val pageSize = 10

        val result = listOf<Map<String, Any>>()
        every {userService.findAllUsersDevices(pageNumber, pageSize)} returns result

        mockMvc.perform(
            get("/users/paged/devices")
                .param("pageNumber", pageNumber.toString())
                .param("pageSize", pageSize.toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
    }
}