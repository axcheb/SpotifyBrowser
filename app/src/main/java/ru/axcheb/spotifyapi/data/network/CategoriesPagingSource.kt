package ru.axcheb.spotifyapi.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import ru.axcheb.spotifyapi.data.model.Category
import ru.axcheb.spotifyapi.data.network.service.SpotifyService
import javax.inject.Inject

class CategoriesPagingSource @Inject constructor(
    private val spotifyService: SpotifyService
) : PagingSource<Int, Category>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Category> {
        val pageNumber = params.key ?: INITIAL_PAGE_NUMBER
        val pageSize = params.loadSize.coerceAtMost(SpotifyService.DEFAULT_PAGE_SIZE)
        val offset = pageNumber * pageSize
        val response = spotifyService.categories(limit = pageSize, offset = offset)

        return if (response.isSuccessful) {
            val categories = response.body()!!.categories.items.map { it.toCategory() }
            val nextPageNumber = if (categories.isEmpty()) null else pageNumber + 1
            val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null
            LoadResult.Page(categories, prevPageNumber, nextPageNumber)
        } else {
            LoadResult.Error(HttpException(response))
        }

    }

    override fun getRefreshKey(state: PagingState<Int, Category>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    companion object {
        const val INITIAL_PAGE_NUMBER = 0
    }

}