package com.example.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.scheduling.annotation.EnableScheduling

//@Component
//class ScheduleTasks {
//
//	@Autowired
//	private lateinit var mangaService: MangaService
//
//	@Autowired
//	private lateinit var mangaRepository: MangaRepository
//
//	private val firstPart = mutableListOf<String>()
//	private val secondPart = mutableListOf<String>()
//	private val thirdPart = mutableListOf<String>()
//	private val fourthPart = mutableListOf<String>()
//
//	private var pageNum = 0
//	private val pageSize = 48
//
////	@Scheduled(fixedRate = 1, timeUnit = TimeUnit.DAYS)
////	fun addData() {
////		println("EXECUTE DATA")
////		mangaService.addDataToDB()
////	}
//
//
//	@Scheduled(fixedRate = 27, timeUnit = TimeUnit.MINUTES)
//	fun getAllUrls() {
//		println("URLS START")
//		val pageable = PageRequest.of(pageNum, pageSize)
//		val allManga = mangaRepository.findAll(pageable)
//		val urls = mutableListOf<String>()
//		println("URLS У")
//		allManga.forEach {
//			urls.add(it.id)
//		}
//
//		println(allManga.size)
//
//		val first = urls.subList(0,(urls.size + 1) / 2)
//		val second = urls.subList((urls.size + 1) / 2, urls.size)
//		firstPart.addAll(first.subList(0,(first.size + 1) / 2))
//		secondPart.addAll(first.subList((first.size + 1) / 2, first.size))
//		thirdPart.addAll(second.subList(0,(second.size + 1) / 2))
//		fourthPart.addAll(second.subList((second.size + 1) / 2, second.size))
//		pageNum++
//		if(allManga.size < 20) {
//			pageNum = 0
//		}
//	}
//
//	@Scheduled(fixedRate = 30, timeUnit = TimeUnit.MINUTES, initialDelay = 2)
//	fun addChaptersFirst(){
//		println("FIRST START")
//		var temping = false
//		while (!temping) {
//			temping = try {
//				firstPart.forEach {
//					mangaService.parsePages(it)
//				}
//				true
//			} catch (e: Exception) {
//				false
//			}
//		}
//	}
//
//	@Scheduled(fixedRate = 30, timeUnit = TimeUnit.MINUTES, initialDelay = 2)
//	fun addChaptersSecond() {
//		println("SECOND START")
//		var temping = false
//		while (!temping) {
//			temping = try {
//				secondPart.forEach {
//					mangaService.parsePages(it)
//				}
//				true
//			} catch (e: Exception) {
//				false
//			}
//		}
//	}
//
//	@Scheduled(fixedRate = 30, timeUnit = TimeUnit.MINUTES, initialDelay = 2)
//	fun addChaptersThird() {
//		println("THIRD START")
//		var temping = false
//		while (!temping) {
//			temping = try {
//				thirdPart.forEach {
//					mangaService.parsePages(it)
//				}
//				true
//			} catch (e: Exception) {
//				false
//			}
//		}
//	}
//
//	@Scheduled(fixedRate = 30, timeUnit = TimeUnit.MINUTES, initialDelay = 2)
//	fun addChaptersFourth() {
//		println("FOURTH START")
//		var temping = false
//		while (!temping) {
//			temping = try {
//				fourthPart.forEach {
//					mangaService.parsePages(it)
//				}
//				true
//			} catch (e: Exception) {
//				false
//			}
//		}
//	}
//}

@SpringBootApplication
@EnableScheduling
@EnableCaching
class Application

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
