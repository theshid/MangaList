package com.shid.mangalist.data.di

import com.shid.mangalist.data.remote.RemoteDataSource
import com.shid.mangalist.data.remote.network.ApiServices
import com.shid.mangalist.data.remote.response.detail.DetailAnimeResponse
import com.shid.mangalist.data.repository.DetailAnimeRepository
import com.shid.mangalist.data.repository.SearchAnimeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ActivityComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDetailAnimeRepository(remoteDataSource: RemoteDataSource):DetailAnimeRepository =
        DetailAnimeRepository.getInstance(remoteDataSource)

    @Provides
    @Singleton
    fun provideSearchAnimeRepository(remoteDataSource: RemoteDataSource):SearchAnimeRepository =
        SearchAnimeRepository.getInstance(remoteDataSource)

    @Provides
    @Singleton
    fun provideRemoteDataSource(apiServices: ApiServices):RemoteDataSource =
        RemoteDataSource.getInstance(apiServices)
}