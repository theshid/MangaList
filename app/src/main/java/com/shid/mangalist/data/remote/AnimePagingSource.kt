package com.shid.mangalist.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import com.shid.mangalist.data.remote.network.ApiServices
import com.shid.mangalist.data.remote.response.main_response.AnimeListResponse
import com.shid.mangalist.ui.home.HomeFragment
import javax.inject.Inject

@ExperimentalPagingApi
class AnimePagingSource @Inject constructor(val backend: ApiServices
) : PagingSource<Int, AnimeListResponse>() {

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, AnimeListResponse> {
        try {
            // Start refresh at page 1 if undefined.

            val nextPageNumber = params.key ?: 1
            val response = backend.getTopAnimePaging(HomeFragment.typeAnime,nextPageNumber)
            return LoadResult.Page(
                data = response.top,
                prevKey = if (nextPageNumber == 1) null else nextPageNumber - 1, // Only paging forward.
                nextKey = if (nextPageNumber > 7) null else nextPageNumber + 1
            )
        } catch (e: Exception) {
            // Handle errors in this block and return LoadResult.Error if it is an
            // expected error (such as a network failure).
            return LoadResult.Error(e)
        }
    }


}