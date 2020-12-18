package com.nikolaev.testcaseapp.common

import com.nikolaev.testcaseapp.BuildConfig
import com.nikolaev.testcaseapp.database.DataStorage
import com.nikolaev.testcaseapp.database.EmployeeRepo
import com.nikolaev.testcaseapp.network.EmployeeRepository
import com.nikolaev.testcaseapp.network.RestClient
import com.nikolaev.testcaseapp.network.RestClientImpl
import com.nikolaev.testcaseapp.ui.specialty_employee.EmployeeListUseCase
import com.nikolaev.testcaseapp.ui.specialty_employee.EmployeeListViewModel
import com.nikolaev.testcaseapp.ui.specialty_list.SpecialtyListUseCase
import com.nikolaev.testcaseapp.ui.specialty_list.SpecialtyListViewModel
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.binds
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Developed by Magora Team (magora-systems.com)
 * 2020
 *
 * @author Andrey Nikolaev
 */

private const val TIMEOUT_CONNECTION_SECONDS = 40L
private const val TIMEOUT_READ_SECONDS = 40L
private const val TIMEOUT_WRITE_SECONDS = 40L
private const val REST_API_ENDPOINT = "https://gitlab.65apps.com/"

val useCasesModule = module {
    factory { SpecialtyListUseCase(netRepository = get(), dbRepository = get()) }
    factory { EmployeeListUseCase(dbRepository = get()) }
}

val viewModelsModel = module {
    viewModel { SpecialtyListViewModel(app = get(), specialtyListUseCase = get()) }
    viewModel { EmployeeListViewModel(app = get(), employeeListUseCase = get()) }
}

val datastoreModule = module {
    single(createdAtStart = true) {
        DataStorage()
    } binds arrayOf(EmployeeRepo::class)
}

val networkModule = module {
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .dispatcher(Dispatcher().apply { maxRequests = 1 })
            .retryOnConnectionFailure(true)
            .connectTimeout(TIMEOUT_CONNECTION_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_READ_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_WRITE_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(REST_API_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build().create(RestClient::class.java)
    }

    single {
        RestClientImpl(restClient = get())
    } binds arrayOf(EmployeeRepository::class)
}