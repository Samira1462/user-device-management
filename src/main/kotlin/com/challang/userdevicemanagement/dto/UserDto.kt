package com.challang.userdevicemanagement.dto

import com.challang.userdevicemanagement.entity.Device
import com.challang.userdevicemanagement.entity.User
import java.time.LocalDate

data class UserDto(
    val id: Long?,
    var firstName: String,
    var lastName: String,
    var address: String,
    var birthday: LocalDate,
    val devices: MutableSet<Device> = mutableSetOf()
) {
    companion object {
        fun convertToUserDto(user: User): UserDto {
            return UserDto(
                user.id,
                user.firstName,
                user.lastName,
                user.address,
                user.birthday,
                user.devices
            )
        }

        fun convertToUser(userDto: UserDto): User {
            return User(
                userDto.id?: 0,
                userDto.firstName,
                userDto.lastName,
                userDto.address,
                userDto.birthday,
                userDto.devices
            )
        }
    }
}