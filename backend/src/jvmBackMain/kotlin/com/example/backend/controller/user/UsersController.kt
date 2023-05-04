package com.example.backend.controller.user

import com.example.backend.jpa.user.StatusFavouriteAnime
import com.example.backend.jpa.user.UserFavorite
import com.example.backend.models.users.*
import com.example.backend.service.user.UserService
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin("*")
@Tag(name = "UsersApi", description = "All about users")
@RequestMapping("/api/users/")
class UsersController(
    private val userService: UserService
) {


    @PostMapping("/{animeId}")
    fun addToFavorites(
        @PathVariable userId: String,
        @PathVariable animeId: String,
        @RequestParam status: StatusFavouriteAnime
    ): ResponseEntity<UserFavorite> {
        val favorite = userService.addToFavorites(userId, animeId, status)
        return ResponseEntity.status(HttpStatus.CREATED).body(favorite)
    }

}