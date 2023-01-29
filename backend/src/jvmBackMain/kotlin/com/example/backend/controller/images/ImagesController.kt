package com.example.backend.controller.images

import com.example.backend.jpa.common.Image
import com.example.backend.service.image.ImageService
import com.example.common.models.response.ServiceResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.ServletContext
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
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