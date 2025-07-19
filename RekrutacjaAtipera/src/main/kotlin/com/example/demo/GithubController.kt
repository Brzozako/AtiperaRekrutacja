package com.example.demo
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/github")
class GithubController(private val githubService: GithubService) {

    @GetMapping("/{username}/repos")
    fun getRepos(@PathVariable username: String): List<RepoDto> =
        githubService.getUserRepos(username)
}
