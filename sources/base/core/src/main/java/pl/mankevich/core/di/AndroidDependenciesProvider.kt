package pl.mankevich.core.di

import android.content.Context

interface AndroidDependenciesProvider {

    fun provideContext(): Context
}