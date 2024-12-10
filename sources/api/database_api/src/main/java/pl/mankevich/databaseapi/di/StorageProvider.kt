package pl.mankevich.databaseapi.di

import pl.mankevich.databaseapi.dao.CharacterDao
import pl.mankevich.databaseapi.dao.EpisodeDao
import pl.mankevich.databaseapi.dao.LocationDao
import pl.mankevich.databaseapi.dao.RelationsDao
import pl.mankevich.databaseapi.dao.Transaction

interface StorageProvider {

    fun provideCharacterDao(): CharacterDao

    fun provideEpisodeDao(): EpisodeDao

    fun provideLocationDao(): LocationDao

    fun provideRelationsDao(): RelationsDao

    fun provideTransaction(): Transaction
}
