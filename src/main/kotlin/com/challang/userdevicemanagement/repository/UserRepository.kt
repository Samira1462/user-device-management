package com.challang.userdevicemanagement.repository

import com.challang.userdevicemanagement.entity.User
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long> {
    @Query("SELECT u FROM User u JOIN u.devices d")
    fun findAllUsersDevices(): List<User>

    @Query("SELECT u FROM User u JOIN u.devices d")
    fun findAllUsersDevices(pageable: Pageable): List<User>
}