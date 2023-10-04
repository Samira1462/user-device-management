package com.challang.userdevicemanagement.controller

import com.challang.userdevicemanagement.dto.DeviceDto
import com.challang.userdevicemanagement.dto.UserDto
import com.challang.userdevicemanagement.exception.ErrorResponse
import com.challang.userdevicemanagement.exception.UserNotFoundException
import com.challang.userdevicemanagement.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    @GetMapping
    fun getAll(): ResponseEntity<List<UserDto>> {
        return ResponseEntity(userService.getAllUsers(), HttpStatus.OK)
    }

    @GetMapping("/{id}", produces = ["application/json"])
    fun getById(@PathVariable id: Long): ResponseEntity<Any> {
        val userDto = userService.getUserById(id)
        return ResponseEntity(userDto, HttpStatus.OK)
    }

    @PostMapping
    fun create(@RequestBody userDto: UserDto): ResponseEntity<UserDto> {
        return ResponseEntity(userService.add(userDto), HttpStatus.CREATED)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): ResponseEntity<Any> {
        return try {
            ResponseEntity(userService.deleteUser(id), HttpStatus.NO_CONTENT)
        } catch (ex: UserNotFoundException) {
            val errorResponse = ErrorResponse("User not found", ex.message)
            ResponseEntity(errorResponse, HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/assign/device")
    fun assignDevice(
        @RequestParam userId: Long,
        @RequestBody deviceDto: DeviceDto
    ): ResponseEntity<String> {

        userService.assignDevice(userId, deviceDto)

        return ResponseEntity.ok("Device assigned to user successfully")
    }

    @GetMapping("/paged/devices")
    fun getUsersDevices(
        @RequestParam(defaultValue = "1") pageNumber: Int,
        @RequestParam(defaultValue = "10") pageSize: Int
    ): ResponseEntity<List<Map<String, Any>>> {
        return ResponseEntity(userService.findAllUsersDevices(pageNumber, pageSize), HttpStatus.OK)
    }
}