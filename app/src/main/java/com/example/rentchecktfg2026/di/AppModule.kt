package com.example.rentchecktfg2026.di

import com.example.rentchecktfg2026.data.repositories.ApplicationRepositoryImpl
import com.example.rentchecktfg2026.data.repositories.DocumentRepositoryImpl
import com.example.rentchecktfg2026.data.repositories.PropertyRepositoryImpl
import com.example.rentchecktfg2026.data.repositories.UserRepositoryImpl
import com.example.rentchecktfg2026.domain.repositories.ApplicationRepository
import com.example.rentchecktfg2026.domain.repositories.DocumentRepository
import com.example.rentchecktfg2026.domain.repositories.PropertyRepository
import com.example.rentchecktfg2026.domain.repositories.UserRepository
import com.example.rentchecktfg2026.domain.usecase.ApplyToPropertyUseCase
import com.example.rentchecktfg2026.domain.usecase.CreatePropertyUseCase
import com.example.rentchecktfg2026.domain.usecase.GetDocumentsUseCase
import com.example.rentchecktfg2026.domain.usecase.GetInquilinosUseCase
import com.example.rentchecktfg2026.domain.usecase.GetPropertiesByOwner
import com.example.rentchecktfg2026.domain.usecase.GetPropertiesUseCase
import com.example.rentchecktfg2026.domain.usecase.GetUserUseCase
import com.example.rentchecktfg2026.domain.usecase.LoginUseCase
import com.example.rentchecktfg2026.domain.usecase.LogoutUseCase
import com.example.rentchecktfg2026.domain.usecase.SyncUserUseCase
import com.example.rentchecktfg2026.domain.usecase.UpdateApplicationStatusUseCase
import com.example.rentchecktfg2026.domain.usecase.UploadDocumentUseCase
import com.example.rentchecktfg2026.network.RetrofitClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val appModule = module {

    single { RetrofitClient.instance }

    single<UserRepository>{ UserRepositoryImpl(get()) }
    single<PropertyRepository>{ PropertyRepositoryImpl(get()) }
    single<ApplicationRepository>{ ApplicationRepositoryImpl() }
    single<DocumentRepository>{ DocumentRepositoryImpl(androidContext()) }

    factory { ApplyToPropertyUseCase(get()) }
    factory { CreatePropertyUseCase(get()) }
    factory { GetDocumentsUseCase(get()) }
    factory { GetInquilinosUseCase(get()) }
    factory { GetPropertiesByOwner(get()) }
    factory { GetPropertiesUseCase(get()) }
    factory { GetUserUseCase(get()) }
    factory { LogoutUseCase(get()) }
    factory { SyncUserUseCase(get()) }
    factory { UpdateApplicationStatusUseCase(get()) }
    factory { UploadDocumentUseCase(get()) }
}