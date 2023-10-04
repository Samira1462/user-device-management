package com.challang.userdevicemanagement

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
	classes = arrayOf(UserDeviceManagementApplication::class),
	webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserDeviceManagementApplicationTests {

	@Test
	fun contextLoads() {
	}

}
