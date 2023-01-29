package com.example.backend.service.manga

import com.example.backend.jpa.manga.MangaTable
import com.example.backend.models.MangaLightJson
import com.example.backend.repository.manga.MangaRepository
import com.example.backend.repository.manga.MangaRepositoryImpl
import com.google.gson.Gson
import it.skrape.core.document
import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.eachHref
import it.skrape.selects.eachText
import it.skrape.selects.html5.a
import it.skrape.selects.html5.div
import it.skrape.selects.html5.li
import it.skrape.selects.html5.script
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class MangaService: MangaRepositoryImpl {

    @Value("\${afa.app.manga_full}")
    lateinit var mangaFull: String

    @Value("\${afa.app.manga_small}")
    lateinit var mangaSmall: String

    @Autowired
    lateinit var mangaRepository: MangaRepository

    override fun addDataToDB(): MangaTable {
        var pageSize = 0

        skrape(HttpFetcher){
            request {
                url = mangaFull + 1
            }
            response {
                document.li {
                    withClass = "page-item"
                    val pages = findAll { return@findAll eachText }
                    pageSize = pages[pages.lastIndex-1].toInt()
                }
            }
        }

        Thread.sleep(1500)


        for (i in 1 until pageSize) {

            val mangaUrls = mutableListOf<String>()

            println(pageSize)

            skrape(HttpFetcher) {
                request {
                    url = mangaFull + pageSize
                    timeout = 400_000
                }
                response {
                    document.div {
                        withClass = "text-line-clamp.mt-2"
                        this.a {
                            mangaUrls.addAll(findAll { return@findAll eachHref })
                        }
                    }
                    println(mangaUrls.size)
                }
            }

            mangaUrls.forEach {
                val json = Json { ignoreUnknownKeys = true }
                val gson = Gson()
                val manga = MangaTable()

                skrape(HttpFetcher) {
                    request {
                        url = mangaSmall + it
                        timeout = 400_000
                    }
                    response {
                        htmlDocument {
                            val schemaScript = script {
                                withAttribute = "type" to "application/ld+json"
                                findFirst { this }
                            }

                            val mangaJsonElement = json.parseToJsonElement(schemaScript.html)
                            val tempStart = gson.fromJson(mangaJsonElement.toString(), MangaLightJson::class.java)
                            manga.url = tempStart.url
                            manga.title = tempStart.name
                            manga.description = tempStart.description
                            manga.image = tempStart.url

                        }
                    }
                }

            }
        }

        return MangaTable()
    }

}