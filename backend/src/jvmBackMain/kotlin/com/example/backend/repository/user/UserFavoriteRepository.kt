package com.example.backend.repository.user

import com.example.backend.jpa.user.UserFavorite
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserFavoriteRepository: JpaRepository<UserFavorite, String>