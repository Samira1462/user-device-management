package com.challang.userdevicemanagement.entity

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class DeviceTest {

    @Mock
    private lateinit var user: User
    @Test
    @DisplayName("device object create successful")
    fun deviceCreation() {
        val device = Device(1L, "serial123", "uuid123", "1234567890", "Test Model", user)

        assertNotNull(device)
        assertEquals(1L, device.id)
        assertEquals("serial123", device.serialNumber)
        assertEquals("uuid123", device.uuid)
        assertEquals("1234567890", device.phoneNumber)
        assertEquals("Test Model", device.model)
        assertEquals(user, device.user)
    }

    @Test
    @DisplayName("Same object should be equal to itself and Devices with the same serialNumber should be equal")
    fun deviceEquality() {
        val device1 = Device(1L, "serial123", "uuid123", "1234567890", "Test Model", user)
        val device2 = Device(1L, "serial123", "uuid123", "1234567890", "Test Model", user)

        assertEquals(device1, device1)
        assertEquals(device1, device2)
    }

    @Test
    @DisplayName("Devices with different properties should not be equal")
    fun deviceInequality() {
        val device1 = Device(1L, "serial123", "uuid123", "1234567890", "Test Model", user)
        val device2 = Device(2L, "serial456", "uuid456", "9876543210", "Different Model", null)

        assertNotEquals(device1, device2)
    }

    @Test
    @DisplayName("Devices with the same serialNumber should have the same hashCode")
    fun hashCodeIsEqual() {
        val device1 = Device(1L, "serial123", "uuid123", "1234567890", "Test Model", user)
        val device2 = Device(1L, "serial123", "uuid123", "1234567890", "Test Model", user)

        assertEquals(device1.hashCode(), device2.hashCode())
    }

    @Test
    @DisplayName("Devices with different properties should have different hashCodes")
    fun hashCodeInequality() {
        val device1 = Device(1L, "serial123", "uuid123", "1234567890", "Test Model", user)
        val device2 = Device(2L, "serial456", "uuid456", "9876543210", "Different Model", null)

        assertNotEquals(device1.hashCode(), device2.hashCode())
    }
}
