package com.example.backend.jpa.user

import com.example.backend.jpa.anime.AnimeTable
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "user_favorites", schema = "users")
data class UserFavorite(
    @Id
    val id: String = UUID.randomUUID().toString(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user: User = User(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "anime_id")
    val anime: AnimeTable = AnimeTable(),

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var status: StatusFavouriteAnime = StatusFavouriteAnime.Watching
)

enum class StatusFavouriteAnime {
    InPlan,
    Watching,
    Watched,
    Postponed
}
