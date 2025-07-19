package com.example.demo

data class BranchDto(
    val name: String,
    val lastCommitSha: String
)

data class RepoDto(
    val repositoryName: String,
    val ownerLogin: String,
    val branches: List<BranchDto>
)
