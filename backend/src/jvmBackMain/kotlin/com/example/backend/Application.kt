package com.example.backend

import com.example.backend.repository.manga.MangaGenreRepository
import com.example.backend.service.manga.MangaService
import com.example.backend.util.getAllCombinationsMangaGenre
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.servlet.view.InternalResourceViewResolver
import java.util.concurrent.TimeUnit

//@Component
//class ScheduleTasks {
//
//	@Autowired
//	private lateinit var mangaService: MangaService
//
//	@Autowired
//	private lateinit var genreRepository: MangaGenreRepository
//
//	val whiteList = listOf("Сёнэн", "Психология", "Сверхъестественное", "Боевик", "Драма", "Комедия",
//		"Детектив", "Приключения", "Романтика", "Ужасы", "Повседневность", "Школа",
//		"Боевые искусства", "Демоны", "Триллер", "Фантастика", "Спорт", "Гарем", "Этти", "Научная фантастика")
//
//	val statuses = listOf("онгоинг", "завершён")
//
//	private val genres = mutableListOf<String>()
//	private val firstPart = mutableListOf(listOf<String>())
//	private val secondPart = mutableListOf(listOf<String>())
//	private val thirdPart = mutableListOf(listOf<String>())
//	private val fourthPart = mutableListOf(listOf<String>())
//
//	@Scheduled(fixedRate = 1, timeUnit = TimeUnit.DAYS)
//	fun cacheGenres(){
//		val tempGenres = genreRepository.findAllGenreIDS().filter { it.title in whiteList }.forEach {
//			if(!it.title.contains("#")) {
//				genres.add(it.id)
//			}
//		}
//		val array = genres.toTypedArray()
//		val list = getAllCombinationsMangaGenre(listOf(*array))
//
//		val first = list.subList(0,(list.size + 1) / 2)
//		val second = list.subList((list.size + 1) / 2, list.size)
//		firstPart.addAll(first.subList(0,(first.size + 1) / 2))
//		secondPart.addAll(first.subList((first.size + 1) / 2, first.size))
//		thirdPart.addAll(second.subList(0,(second.size + 1) / 2))
//		fourthPart.addAll(second.subList((second.size + 1) / 2, second.size))
//	}
//
//
//	@Scheduled(fixedRate = 3600, timeUnit = TimeUnit.SECONDS, initialDelay = 5)
//	fun cacheMangaTwelve() {
//		val size = 12
//		val page = 100
//
//		for (i in 0 until page){
//			mangaService.getAllManga(pageNum = i, pageSize = size, null, null, null)
//			statuses.forEach { status ->
//				mangaService.getAllManga(pageNum = i, pageSize = size, null, null, status)
//			}
//		}
//	}
//
//	@Scheduled(fixedRate = 3600, timeUnit = TimeUnit.SECONDS, initialDelay = 5)
//	fun cacheMangaFourty() {
//		val size = 48
//		val maxPage = 40
//
//		for (i in 0 until maxPage){
//			mangaService.getAllManga(pageNum = i, pageSize = size, null, null, null)
//			statuses.forEach { status ->
//				mangaService.getAllManga(pageNum = i, pageSize = size, null, null, status)
//			}
//		}
//	}
//
//	@Scheduled(fixedRate = 3600, timeUnit = TimeUnit.SECONDS, initialDelay = 10)
//	fun cacheMangaTwelveGenreFirst() {
//		val size = 12
//		val maxPage = 30
//		for(arr in firstPart) {
//			for (i in 0 until maxPage) {
//				mangaService.getAllManga(pageNum = i, pageSize = size, null, arr, null)
//				statuses.forEach { status ->
//					mangaService.getAllManga(pageNum = i, pageSize = size, null, arr, status)
//				}
//
//			}
//		}
//	}
//
//	@Scheduled(fixedRate = 3600, timeUnit = TimeUnit.SECONDS, initialDelay = 10)
//	fun cacheMangaTwelveGenreSecond() {
//		val size = 12
//		val maxPage = 30
//		for(arr in secondPart) {
//			for (i in 0 until maxPage) {
//				mangaService.getAllManga(pageNum = i, pageSize = size, null, arr, null)
//				statuses.forEach { status ->
//					mangaService.getAllManga(pageNum = i, pageSize = size, null, arr, status)
//				}
//			}
//		}
//	}
//
//	@Scheduled(fixedRate = 3600, timeUnit = TimeUnit.SECONDS, initialDelay = 10)
//	fun cacheMangaTwelveGenreThird() {
//		val size = 12
//		val maxPage = 30
//
//		for (arr in thirdPart) {
//			for (i in 0 until maxPage) {
//				mangaService.getAllManga(pageNum = i, pageSize = size, null, arr, null)
//				statuses.forEach { status ->
//					mangaService.getAllManga(pageNum = i, pageSize = size, null, arr, status)
//				}
//			}
//		}
//	}
//
//	@Scheduled(fixedRate = 3600, timeUnit = TimeUnit.SECONDS, initialDelay = 10)
//	fun cacheMangaTwelveGenreFourth() {
//		val size = 12
//		val maxPage = 30
//		for(arr in fourthPart) {
//			for (i in 0 until maxPage) {
//				mangaService.getAllManga(pageNum = i, pageSize = size, null, arr, null)
//				statuses.forEach { status ->
//					mangaService.getAllManga(pageNum = i, pageSize = size, null, arr, status)
//				}
//			}
//		}
//	}
//
//	@Scheduled(fixedRate = 3600, timeUnit = TimeUnit.SECONDS, initialDelay = 10)
//	fun cacheMangaFourtyGenreFirst() {
//		val size = 48
//		val maxPage = 30
//		for(arr in firstPart) {
//			for (i in 0 until maxPage) {
//				mangaService.getAllManga(pageNum = i, pageSize = size, null, arr, null)
//				statuses.forEach { status ->
//					mangaService.getAllManga(pageNum = i, pageSize = size, null, arr, status)
//				}
//			}
//		}
//	}
//
//	@Scheduled(fixedRate = 3600, timeUnit = TimeUnit.SECONDS, initialDelay = 10)
//	fun cacheMangaFourtyGenreSecond() {
//		val size = 48
//		val maxPage = 30
//		for(arr in secondPart) {
//			for (i in 0 until maxPage) {
//
//				mangaService.getAllManga(pageNum = i, pageSize = size, null, arr, null)
//				statuses.forEach { status ->
//					mangaService.getAllManga(pageNum = i, pageSize = size, null, arr, status)
//				}
//			}
//		}
//	}
//
//	@Scheduled(fixedRate = 3600, timeUnit = TimeUnit.SECONDS, initialDelay = 10)
//	fun cacheMangaFourtyGenreThird() {
//		val size = 48
//		val maxPage = 30
//		for (arr in thirdPart) {
//			for (i in 0 until maxPage) {
//				mangaService.getAllManga(pageNum = i, pageSize = size, null, arr, null)
//				statuses.forEach { status ->
//					mangaService.getAllManga(pageNum = i, pageSize = size, null, arr, status)
//				}
//			}
//		}
//	}
//
//	@Scheduled(fixedRate = 3600, timeUnit = TimeUnit.SECONDS, initialDelay = 10)
//	fun cacheMangaFourtyGenreFourth() {
//		val size = 48
//		val maxPage = 30
//		for(arr in fourthPart) {
//			for (i in 0 until maxPage) {
//				mangaService.getAllManga(pageNum = i, pageSize = size, null, arr, null)
//				statuses.forEach { status ->
//					mangaService.getAllManga(pageNum = i, pageSize = size, null, arr, status)
//				}
//			}
//		}
//	}
//}

@SpringBootApplication
@EnableScheduling
@EnableCaching
class Application {
	@Bean
	fun viewResolver(): InternalResourceViewResolver {
		val resolver = InternalResourceViewResolver()
		resolver.setPrefix("/templates/")
		resolver.setSuffix(".html")
		return resolver
	}
}

fun main(args: Array<String>) {
	runApplication<Application>(*args)
}
