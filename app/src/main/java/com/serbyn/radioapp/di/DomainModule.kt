package com.serbyn.radioapp.di;

import com.serbyn.radioapp.data.CatalogRepositoryImpl
import com.serbyn.radioapp.domain.CatalogRepository
import com.serbyn.radioapp.domain.dispatchers.CoroutinesDispatchers
import com.serbyn.radioapp.domain.dispatchers.CoroutinesDispatchersImpl

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindCoroutineDispatchers(
            dispatcherImpl: CoroutinesDispatchersImpl
    ) : CoroutinesDispatchers

    @Binds
    abstract fun bindCatalogRepository(
        repositoryImpl: CatalogRepositoryImpl
    ) : CatalogRepository

}
