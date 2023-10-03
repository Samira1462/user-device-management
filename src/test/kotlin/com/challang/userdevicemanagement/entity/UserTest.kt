package com.challang.userdevicemanagement.entity

import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
class UserTest {

    @Mock
    private lateinit var device: Device

    @Test
    @DisplayName("user object create successful")
    fun userCreation() {
        val user = User(1L, "John", "Doe", "123 Main St", LocalDate.of(1990, 1, 1))

        assertNotNull(user)
        assertEquals(1L, user.id)
        assertEquals("John", user.firstName)
        assertEquals("Doe", user.lastName)
        assertEquals("123 Main St", user.address)
        assertEquals(LocalDate.of(1990, 1, 1), user.birthday)
        assertNotNull(user.devices)
        assertEquals(0, user.devices.size)
    }

    @Test
    @DisplayName("Same object should be equal to itself AND Devices with the same serialNumber should be equal")
    fun userEquality() {
        val user1 = User(1L, "John", "Doe", "123 Main St", LocalDate.of(1990, 1, 1))
        val user2 = User(1L, "John", "Doe", "123 Main St", LocalDate.of(1990, 1, 1))

        assertEquals(user1, user1)
        assertEquals(user1, user2)
    }

    @Test
    @DisplayName("Users with different properties should not be equal")
    fun userInequality() {
        val user1 = User(1L, "John", "Doe", "123 Main St", LocalDate.of(1990, 1, 1))
        val user2 = User(2L, "Jane", "Smith", "456 Elm St", LocalDate.of(1985, 5, 5))

        assertNotEquals(user1, user2)
    }

    @Test
    @DisplayName("Users with the same serialNumber should have the same hashCode")
    fun hashCodeIsEquals() {
        val user1 = User(1L, "John", "Doe", "123 Main St", LocalDate.of(1990, 1, 1))
        val user2 = User(1L, "John", "Doe", "123 Main St", LocalDate.of(1990, 1, 1))

        assertEquals(user1.hashCode(), user2.hashCode())
    }

    @Test
    @DisplayName("Users with different properties should have different hashCodes")
    fun hashCodeInequality() {
        val user1 = User(1L, "John", "Doe", "123 Main St", LocalDate.of(1990, 1, 1))
        val user2 = User(2L, "Jane", "Smith", "456 Elm St", LocalDate.of(1985, 5, 5))

        assertNotEquals(user1.hashCode(), user2.hashCode())
    }

    @Test
    @DisplayName("add device to user")
    fun deviceAssociation() {
        val user = User(1L, "John", "Doe", "123 Main St", LocalDate.of(1990, 1, 1))
        user.devices.add(device)

        assertEquals(1, user.devices.size)
        assertTrue(user.devices.contains(device))
    }

    @Test
    @DisplayName("disassociation device to user")
    fun deviceDisassociation() {
        val user = User(1L, "John", "Doe", "123 Main St", LocalDate.of(1990, 1, 1))
        user.devices.add(device)
        user.devices.remove(device)

        assertEquals(0, user.devices.size)
        assertFalse(user.devices.contains(device))
    }
}
