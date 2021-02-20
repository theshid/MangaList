package com.shid.mangalist.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shid.mangalist.data.remote.network.ApiServices
import com.shid.mangalist.data.remote.response.main_response.AnimeListResponse
import com.shid.mangalist.data.remote.response.top.TopAnimeResponse
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class AnimePagingSource @Inject constructor(val backend: ApiServices
) : PagingSource<Int, AnimeListResponse>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, AnimeListResponse> {
        try {
            // Start refresh at page 1 if undefined.

            val nextPageNumber = params.key ?: 1
            val response = backend.getTopAnime(type, nextPageNumber)
            return LoadResult.Page(
                data = response.top,
                prevKey = null, // Only paging forward.
                nextKey = if (response.top.isEmpty()) null else nextPageNumber + 1
            )
        } catch (e: Exception) {
            // Handle errors in this block and return LoadResult.Error if it is an
            // expected error (such as a network failure).
            return LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, AnimeListResponse>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
