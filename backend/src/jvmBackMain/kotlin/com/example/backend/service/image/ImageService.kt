package com.example.backend.service.image

import com.example.backend.jpa.common.Image
import com.example.backend.repository.image.ImageRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class ImageService {

    @Autowired
    private lateinit var imageRepository: ImageRepository

    @Value("\${host_url}")
    lateinit var host: String

    fun saveFile(file: MultipartFile): String {
        val image = imageRepository.save(Image(
            image = file.bytes
        ))
        return "$host/images/${image.id}"
    }


    fun save(file: ByteArray): String {
        val image = imageRepository.save(
            Image(
                image = file
            )
        )
        return "$host/images/${image.id}"
    }

    fun getImage(id: String): Optional<Image> {
        return imageRepository.findById(UUID.fromString(id))
    }
}