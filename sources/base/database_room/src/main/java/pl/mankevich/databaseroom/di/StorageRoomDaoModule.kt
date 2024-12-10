package pl.mankevich.databaseroom.di

import dagger.Binds
import dagger.Module
import pl.mankevich.core.di.FeatureScope
import pl.mankevich.databaseroom.dao.CharacterDaoImpl
import pl.mankevich.databaseroom.dao.EpisodeDaoImpl
import pl.mankevich.databaseroom.dao.LocationDaoImpl
import pl.mankevich.databaseroom.dao.RelationsDaoImpl
import pl.mankevich.databaseapi.dao.CharacterDao
import pl.mankevich.databaseapi.dao.EpisodeDao
import pl.mankevich.databaseapi.dao.LocationDao
import pl.mankevich.databaseapi.dao.RelationsDao


@Module
interface StorageRoomDaoModule {

    @FeatureScope
    @Binds
    fun provideCharacterDao(impl: CharacterDaoImpl): CharacterDao

    @FeatureScope
    @Binds
    fun provideEpisodeDao(impl: EpisodeDaoImpl): EpisodeDao

    @FeatureScope
    @Binds
    fun provideLocationDao(impl: LocationDaoImpl): LocationDao

    @FeatureScope
    @Binds
    fun provideRelationsDao(impl: RelationsDaoImpl): RelationsDao
}