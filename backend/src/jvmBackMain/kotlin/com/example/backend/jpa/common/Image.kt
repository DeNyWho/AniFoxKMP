package com.example.backend.jpa.common

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "image")
data class Image(
    @Id
    val id: String? = UUID.randomUUID().toString(),

    @Lob
    @Column(name = "image")
    var image: ByteArray? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Image

        if (id != other.id) return false
        if (image != null) {
            if (other.image == null) return false
            if (!image.contentEquals(other.image)) return false
        } else if (other.image != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (image?.contentHashCode() ?: 0)
        return result
    }
}