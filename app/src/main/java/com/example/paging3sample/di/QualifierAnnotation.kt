package com.example.paging3sample.di

import javax.inject.Qualifier

object QualifierAnnotation {

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class DefaultDispatcher

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class IoDispatcher

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class MainDispatcher

    @Retention(AnnotationRetention.BINARY)
    @Qualifier
    annotation class MainImmediateDispatcher

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class ApplicationScope
}