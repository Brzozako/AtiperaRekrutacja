package com.example.demo

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class IntegrationTest {

	@LocalServerPort
	private var port: Int = 0

	@Autowired
	private lateinit var restTemplate: TestRestTemplate

	@Test
	fun `should return non-fork repos for existing user and correct branch data`() {
		// Popularny publiczny user, np. "octocat"
		val url = "http://localhost:$port/github/octocat/repos"
		val response = restTemplate.getForEntity(url, Array<RepoDto>::class.java)
		assertThat(response.statusCode.value()).isEqualTo(200)
		assertThat(response.body).isNotNull
		assertThat(response.body!!.all { it.ownerLogin == "octocat" }).isTrue() // prosty przypadek biznesowy
	}
}
