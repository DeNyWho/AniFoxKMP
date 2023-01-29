package com.example.backend.repository.image

import com.example.backend.jpa.common.Image
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ImageRepository: JpaRepository<Image, String>