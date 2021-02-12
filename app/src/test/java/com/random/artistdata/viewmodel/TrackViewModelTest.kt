package com.random.artistdata.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.random.artistdata.api.TrackManager
import com.random.artistdata.models.APIResponse
import com.random.artistdata.models.TrackDTO
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(JUnit4::class)
class TrackViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Mock
    lateinit var trackManager: TrackManager

    private lateinit var viewModel: TrackViewModel

    @Mock
    lateinit var observer: Observer<Boolean>

    @Mock
    lateinit var lifecycleOwner: LifecycleOwner
    private var lifecycle: Lifecycle? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = TrackViewModel(trackManager)
        lifecycle = LifecycleRegistry(lifecycleOwner)
        viewModel.progressVisibility.observeForever(observer)
    }

    @Test
    fun testSuccessResponse() = coroutinesTestRule.dispatcher.runBlockingTest {
        val apiResponse =
            APIResponse("200", 0, 1, listOf(TrackDTO(1, "Test", "Test Track", "", "", "")))
        Mockito.`when`(trackManager.getTracks(any())).thenReturn(Response.success(apiResponse))
        viewModel.liveDataArtistName.value = "Test"
        viewModel.searchByArtist()
        Mockito.verify(observer)!!.onChanged(true)
        Mockito.verify(observer)!!.onChanged(false)
        Assert.assertFalse(viewModel.data.isEmpty())
        Assert.assertNull(viewModel.liveDataArtistNameError.value)
    }

    @Test
    fun testErrorResponse() = coroutinesTestRule.dispatcher.runBlockingTest {
        val errorResponse =
            "{\n" +
                    "  \"type\": \"error\",\n" +
                    "  \"message\": \"Error Response.\"\n}"
        val errorResponseBody = errorResponse.toResponseBody("application/json".toMediaTypeOrNull())
        Mockito.`when`(trackManager.getTracks(any()))
            .thenReturn(Response.error(401, errorResponseBody))
        viewModel.liveDataArtistName.value = "Test"
        viewModel.searchByArtist()
        Mockito.verify(observer)!!.onChanged(true)
        Mockito.verify(observer)!!.onChanged(false)
        Assert.assertNotNull(viewModel.liveDataArtistNameError.value)
        Assert.assertFalse(viewModel.liveDataArtistNameError.value.isNullOrEmpty())
    }
}