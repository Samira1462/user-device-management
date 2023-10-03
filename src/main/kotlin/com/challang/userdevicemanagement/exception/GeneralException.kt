package com.challang.userdevicemanagement.exception

class UserNotFoundException(userId: Long) : RuntimeException("User with ID $userId not found")

class UserException(message: String) : RuntimeException(message)
