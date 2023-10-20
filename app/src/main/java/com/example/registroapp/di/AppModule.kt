package com.example.registroapp.di
import com.example.registroapp.data.remote.dto.ClientesApi
import com.example.registroapp.data.repository.ClienteRepository
import com.example.registroapp.data.repository.ClienteRepositoryImp
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindOcupacionRepository(impl: ClienteRepositoryImp): ClienteRepository
}

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Singleton
    @Provides
    fun providesMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Singleton
    @Provides
    fun providesMiClientesApi(moshi: Moshi): ClientesApi {
        return Retrofit.Builder().baseUrl("https://miclientesapi.azurewebsites.net")
            .addConverterFactory(MoshiConverterFactory.create(moshi)).build()
            .create(ClientesApi::class.java)
    }
}