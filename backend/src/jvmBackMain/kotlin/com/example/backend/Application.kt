package com.example.backend

import com.example.backend.service.anime.AnimeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

@Component
class ScheduleTasks {

	@Autowired
	private lateinit var animeService: AnimeService

	@Scheduled(fixedRate = 12, timeUnit = TimeUnit.HOURS)
	fun refreshData(){
		thread {
			animeService.addDataToDB("610, 609, 735, 643, 559, 739, 767, 825, 933, 557, 794, 1002")
			refreshDataAniDub()
			refreshDataTwoXTwo()
			refreshDataStudioBand()
			refreshDataStudioBandFla()
			refreshDataAnimedia()
			refreshDataKansai()
			refreshDataSHIZA()
			refreshDataAniMaunt()
			refreshDataAmber()
			refreshDataJam()
			refreshDataHDREZ()
		}
	}

	fun refreshDataAniDub() {
		animeService.addDataToDB("609")
	}

	fun refreshDataTwoXTwo(){
		animeService.addDataToDB("735")
	}

	fun refreshDataStudioBand(){
		animeService.addDataToDB("643")
	}

	fun refreshDataStudioBandFla(){
		animeService.addDataToDB("1002")
	}

	fun refreshDataAnimedia(){
		animeService.addDataToDB("739")
	}

	fun refreshDataKansai(){
		animeService.addDataToDB("559")
	}

	fun refreshDataSHIZA(){
		animeService.addDataToDB("767")
	}

	fun refreshDataAniMaunt(){
		animeService.addDataToDB("825")
	}

	fun refreshDataAmber(){
		animeService.addDataToDB("933")
	}

	fun refreshDataJam(){
		animeService.addDataToDB("557")
	}

	fun refreshDataHDREZ(){
		animeService.addDataToDB("794")
	}
}

@SpringBootApplication
@EnableScheduling
@EnableCaching
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
