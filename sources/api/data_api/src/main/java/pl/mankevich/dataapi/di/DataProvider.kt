package pl.mankevich.dataapi.di

import pl.mankevich.dataapi.repository.CharacterRepository

interface DataProvider {

    fun provideCharacterRepository(): CharacterRepository
}
