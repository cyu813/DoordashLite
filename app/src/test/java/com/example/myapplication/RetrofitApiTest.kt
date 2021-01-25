package com.example.myapplication

import com.example.myapplication.api.APIService
import com.example.myapplication.api.RetrofitClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import java.net.HttpURLConnection

class RetrofitApiTest {
    lateinit var mockServer: MockWebServer
    lateinit var apiService: APIService
    val lat = 37.422740 //default
    val long = -122.139956 //default
    val invalidLat = -100.00000 //valid range -90 to 90
    val invalidLong = 200.00000 //valid range -180 to 180

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        mockServer = MockWebServer()
        mockServer.start()
        apiService = RetrofitClient.apiService
    }

    @Test
    fun validate_location_search_successful_code() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_OK)
        mockServer.enqueue(response)

        val actualResponse = apiService.searchRestaurants(lat, long).execute()
        assertEquals(response.toString().contains("200"),actualResponse.code().toString().contains("200"))
    }

    @Test
    fun validate_location_search_successful_body() {
        val actualResponse = apiService.searchRestaurants(lat, long).execute()
        assertNotNull(actualResponse.body())
        assertEquals(actualResponse.body()?.get(0)?.name, "The Melt")
    }

    @Test
    fun validate_location_search_invalid_range() {
        val response = MockResponse()
            .setResponseCode(HttpURLConnection.HTTP_BAD_REQUEST)
            .setBody("")
        mockServer.enqueue(response)

        val actualResponse = apiService.searchRestaurants(invalidLat, invalidLong).execute()
        assertNull(actualResponse.body())
        assertEquals(response.toString().contains("400"),actualResponse.code().toString().contains("400"))
    }
}