package ru.axcheb.spotifyapi.data.network.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import retrofit2.Response
import ru.axcheb.spotifyapi.data.network.service.SpotifyService

abstract class SpotifyPagingSource<Dto : Any, Value : Any>() : PagingSource<Int, Value>() {

    override fun getRefreshKey(state: PagingState<Int, Value>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Value> {
        val pageNumber = params.key ?: INITIAL_PAGE_NUMBER
        val pageSize = params.loadSize.coerceAtMost(SpotifyService.DEFAULT_PAGE_SIZE)
        val offset = pageNumber * pageSize

        return try {
            val response = load(limit = pageSize, offset = offset)
            if (response.isSuccessful) {
                val values = toModel(response.body()!!)
                val nextPageNumber = if (values.isEmpty()) null else pageNumber + 1
                val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null
                LoadResult.Page(values, prevPageNumber, nextPageNumber)
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    abstract suspend fun load(limit: Int, offset: Int): Response<Dto>

    abstract fun toModel(dto: Dto): List<Value>

    companion object {
        private const val INITIAL_PAGE_NUMBER = 0
    }

}