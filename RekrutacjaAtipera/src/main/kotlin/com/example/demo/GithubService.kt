package com.example.demo
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate

@Service
class GithubService(private val restTemplate: RestTemplate) {

    fun getUserRepos(username: String): List<RepoDto> {
        val reposUrl = "https://api.github.com/users/$username/repos?type=owner"
        try {
            val entity = restTemplate.getForEntity(url, Array<Map<*, *>>::class.java)
            val repos = entity.body?: emptyArray()
            return repos
                .filter { it["fork"] == false }
                .map { repo ->
                    val repoName = repo["name"] as String
                    val ownerLogin = (repo["owner"] as Map<*, *>)["login"] as String
                    val branchesUrl = "https://api.github.com/repos/$ownerLogin/$repoName/branches"
                    val branchesEntity = restTemplate.getForEntity(url, Array<Map<*, *>>::class.java)
                    val branches = branchesEntity.body?.map { branch ->
                        BranchDto(
                            name = branch["name"] as String,
                            lastCommitSha = (branch["commit"] as Map<*, *>)["sha"] as String
                        )
                    } ?: listOf()
                    RepoDto(repositoryName = repoName, ownerLogin = ownerLogin, branches = branches)
                }
        } catch (e: HttpClientErrorException.NotFound) {
            throw GithubUserNotFoundException("Github user not found")
        }
    }
}
