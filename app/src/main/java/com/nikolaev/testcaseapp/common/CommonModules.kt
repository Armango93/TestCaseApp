package com.nikolaev.testcaseapp.common

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.binds
import org.koin.dsl.module

/**
 * Developed by Magora Team (magora-systems.com)
 * 2020
 *
 * @author Andrey Nikolaev
 */

val useCasesModule = module {
//    factory { TrainingListUseCase(trainingRepository = get()) }
}

val datastoreModule = module {
//    single(createdAtStart = true) {
//        PrefsSessionRepository(get())
//    } binds arrayOf(SessionRepository::class)
}