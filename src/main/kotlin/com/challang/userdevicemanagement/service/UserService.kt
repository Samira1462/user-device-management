package com.challang.userdevicemanagement.service

import com.challang.userdevicemanagement.dto.DeviceDto
import com.challang.userdevicemanagement.dto.UserDto
import com.challang.userdevicemanagement.entity.User
import com.challang.userdevicemanagement.exception.UserNotFoundException
import com.challang.userdevicemanagement.repository.DeviceRepository
import com.challang.userdevicemanagement.repository.UserRepository
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Slf4j
@Service
class UserService(private val userRepository: UserRepository,
                  private val deviceRepository: DeviceRepository
) {
    private val log: Logger = LoggerFactory.getLogger(UserService::class.java)

    fun getAllUsers(): List<UserDto> {
        log.info("find all system Users")
        return userRepository.findAll().map { UserDto.convertToUserDto(it) }
    }

    fun getUserById(id: Long): UserDto {
        log.info("find User by id {}", id)
        return userRepository.findById(id)
            .stream()
            .map { UserDto.convertToUserDto(it) }
            .findFirst()
            .orElseThrow { UserNotFoundException(id) }
    }

    @Transactional
    fun add(userDto: UserDto): UserDto {
        val user = UserDto.convertToUser(userDto)
        log.debug("created a new user ")
        return UserDto.convertToUserDto(userRepository.saveAndFlush(user))
    }

    fun deleteUser(id: Long) {
        if (!userRepository.existsById(id)) {
            throw UserNotFoundException(id)
        }
        userRepository.deleteById(id)
        log.info("delete User by id {}", id)
    }

    @Transactional
    fun addDevice(userId: Long, deviceDto: DeviceDto) {
        val user = userRepository.findById(userId).orElse(null)
        log.info("assign device to User")
        if (user != null) {
            val device = DeviceDto.convertToDevice(deviceDto)
            device.user = user
            user.devices.add(device)
            val savedDevice = deviceRepository.save(device)
            DeviceDto.convertToDeviceDto(savedDevice)
        }
        log.info("assigned device to User")
    }

    fun findUsersDevices(): List<Map<String, Any>> {
        val users = userRepository.findAllUsersDevices()
        log.info("find all assigned device to User")
        return findAllUserDevices(users)
    }
    fun findAllUsersDevices(pageNumber: Int,
                            pageSize: Int): List<Map<String, Any>> {
        val pageable = PageRequest.of(pageNumber - 1, pageSize)
        val users = userRepository.findAllUsersDevices(pageable)
        log.info("find pageable assigned device to User")
        return findAllUserDevices(users)
    }

    private fun findAllUserDevices(users: List<User>): MutableList<Map<String, Any>> {
        fun maps(): MutableList<Map<String, Any>> {
            val result = mutableListOf<Map<String, Any>>()

            for (user in users) {
                val deviceList = user.devices.map { device ->
                    mapOf(
                        "id" to device.id,
                        "serialNumber" to device.serialNumber,
                        "uuid" to device.uuid,
                        "phoneNumber" to device.phoneNumber,
                        "model" to device.model
                    )
                }

                val userMap = mapOf(
                    "user" to mapOf(
                        "id" to user.id,
                        "firstName" to user.firstName,
                        "lastName" to user.lastName,
                        "address" to user.address,
                        "birthday" to user.birthday,
                        "devices" to deviceList
                    )
                )

                result.add(userMap)
            }

            return result
        }

        return maps()
    }

}