package com.challang.userdevicemanagement.repository

import com.challang.userdevicemanagement.entity.Device
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DeviceRepository: JpaRepository<Device, Long>
