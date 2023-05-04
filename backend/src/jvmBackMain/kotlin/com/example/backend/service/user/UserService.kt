package com.example.backend.service.user

import com.example.backend.jpa.user.StatusFavouriteAnime
import com.example.backend.jpa.user.UserFavorite
import com.example.backend.repository.anime.AnimeRepository
import com.example.backend.repository.user.UserFavoriteRepository
import com.example.backend.repository.user.UserRepository
import org.springframework.stereotype.Service
import javax.ws.rs.NotFoundException

@Service
class UserService(
    private val userRepository: UserRepository,
    private val animeRepository: AnimeRepository,
    private val userFavoriteRepository: UserFavoriteRepository,
) {
    fun addToFavorites(userId: String, animeId: String, status: StatusFavouriteAnime): UserFavorite {
        val user = userRepository.findById(userId).orElseThrow { throw NotFoundException("User not found") }
        val anime = animeRepository.findById(animeId).orElseThrow { throw NotFoundException("Anime not found") }
        val favorite = UserFavorite(user = user, anime = anime, status = status)
        return userFavoriteRepository.save(favorite)
    }

}