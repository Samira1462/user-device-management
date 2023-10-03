package com.challang.userdevicemanagement.exception

import org.springframework.dao.DataIntegrityViolationException

class UserNotFoundException(userId: Long) : RuntimeException("User with ID $userId not found")

class UserException(message: String) : RuntimeException(message)
class SerialNumberNotUniqException(serialNumber: String) : DataIntegrityViolationException("Device with $serialNumber is Not unique ")
