package pl.abovehead.common.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class OkHttpRepository

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RetrofitRepository
