package com.example.backend.controller.images

import com.example.backend.jpa.common.Image
import com.example.backend.service.image.ImageService
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@CrossOrigin("*")
@Tag(name = "Images", description = "Images")
@RequestMapping("/images/")
class ImageController {

    @Autowired
    lateinit var imageService: ImageService

    @GetMapping("{id}")
    fun getFile(@PathVariable id: String, response: HttpServletResponse) {
        val fileEntityOptional: Optional<Image> = imageService.getImage(id)
        val file = fileEntityOptional.get()

        response.contentType = MediaType.valueOf(MediaType.IMAGE_PNG_VALUE).toString()
        response.outputStream.write(file.image!!)
        response.outputStream.close()
    }
}