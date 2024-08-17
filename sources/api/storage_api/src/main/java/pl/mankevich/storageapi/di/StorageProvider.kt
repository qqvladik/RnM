package pl.mankevich.storageapi.di

import pl.mankevich.storageapi.dao.CharacterDao
import pl.mankevich.storageapi.dao.EpisodeDao
import pl.mankevich.storageapi.dao.LocationDao
import pl.mankevich.storageapi.dao.RelationsDao
import pl.mankevich.storageapi.dao.Transaction

interface StorageProvider {

    fun provideCharacterDao(): CharacterDao

    fun provideEpisodeDao(): EpisodeDao

    fun provideLocationDao(): LocationDao

    fun provideRelationsDao(): RelationsDao

    fun provideTransaction(): Transaction
}
