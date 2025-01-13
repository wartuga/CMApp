package com.cmapp.network

import com.cmapp.model.domain.potterDB.PotterBook
import com.cmapp.model.domain.potterDB.PotterBookChapter
import com.cmapp.model.domain.potterDB.PotterChar
import com.cmapp.model.domain.potterDB.PotterData
import com.cmapp.model.domain.potterDB.PotterMovie
import com.cmapp.model.domain.potterDB.PotterPotion
import com.cmapp.model.domain.potterDB.PotterSpell
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://api.potterdb.com/"
private const val VERSION = "v1/"

private val json = Json {
    ignoreUnknownKeys = true
}

private val potterDBRetrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/vnd.api+json".toMediaType()))
    .baseUrl(BASE_URL+ VERSION)
    .build()

/**
 * Retrofit service object for creating api calls
 */
interface PotterDBApiService {
    // limited to 900 requests/h, then retrieves http status code 429

    /**
     * uri options:
     * page size - ?page[size]=x
     * page number - ?page[number]=x
     * filter - ?filter[name_cont]=x
     * sort - ?sort=attribute
     */

    // books
    @GET("books")
    suspend fun getBooks(): PotterData<List<PotterBook>>
    @GET("books/{id}")
    suspend fun getBook(@Path("id") id: String): PotterData<PotterBook>
    @GET("books/{bookId}/chapters")
    suspend fun getBookChapters(@Path("bookId") bookId: String): PotterData<List<PotterBookChapter>>
    @GET("books/{bookId}/chapters/{chapterId}")
    suspend fun getBookChapter(
        @Path("bookId") bookId: String,
        @Path("chapterId") chapterId: String
    ): PotterData<PotterBookChapter>

    // characters
    @GET("characters")
    suspend fun getCharacters(): PotterData<List<PotterChar>>
    @GET("characters/{id}")
    suspend fun getCharacter(@Path("id") id: String): PotterData<PotterChar>

    // movies
    @GET("movies")
    suspend fun getMovies(): PotterData<List<PotterMovie>>
    @GET("movies/{id}")
    suspend fun getMovie(@Path("id") id: String): PotterData<PotterMovie>

    // potions
    @GET("potions")
    suspend fun getPotions(): PotterData<List<PotterPotion>>
    @GET("potions/{id}")
    suspend fun getPotion(@Path("id") id: String): PotterData<PotterPotion>

    // spells
    @GET("spells")
    suspend fun getSpells(): PotterData<List<PotterSpell>>
    @GET("spells/{id}")
    suspend fun getSpell(@Path("id") id: String): PotterData<PotterSpell>
}

object PotterDBApi {
    val retrofitService: PotterDBApiService by lazy {
        potterDBRetrofit.create(PotterDBApiService::class.java)
    }
}
