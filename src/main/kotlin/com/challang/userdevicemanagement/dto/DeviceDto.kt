package com.challang.userdevicemanagement.dto

import com.challang.userdevicemanagement.entity.Device
import com.challang.userdevicemanagement.entity.User

data class DeviceDto(
    val id: Long?,
    var serialNumber: String,
    var uuid: String,
    var phoneNumber: String,
    var model: String,
    var user: User?
) {
    companion object {
        fun convertToDeviceDto(device: Device): DeviceDto {
            return DeviceDto(
                id = device.id,
                serialNumber = device.serialNumber ?: "",
                uuid = device.uuid ?: "",
                phoneNumber = device.phoneNumber ?: "",
                model = device.model ?: "",
                user = device.user
            )
        }

        fun convertToDevice(deviceDto: DeviceDto): Device {
            return Device(
                id = deviceDto.id?:0,
                serialNumber = deviceDto.serialNumber,
                uuid = deviceDto.uuid,
                phoneNumber = deviceDto.phoneNumber,
                model = deviceDto.model,
                user = deviceDto.user
            )
        }
    }
}

