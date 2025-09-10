package com.oyj.di.source

import com.oyj.data.source.local.BookLocalSource
import com.oyj.data.source.local.BookLocalSourceImpl
import com.oyj.data.source.remote.BookRemoteSource
import com.oyj.data.source.remote.BookRemoteSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindBookLocalSource(bookLocalSource: BookLocalSourceImpl): BookLocalSource

    @Binds
    abstract fun bindBookRemoteSource(bookRemoteSource: BookRemoteSourceImpl): BookRemoteSource
}