package pl.mankevich.storageroom.di

import dagger.Binds
import dagger.Module
import pl.mankevich.core.di.FeatureScope
import pl.mankevich.storageroom.dao.CharacterDaoImpl
import pl.mankevich.storageroom.dao.EpisodeDaoImpl
import pl.mankevich.storageroom.dao.LocationDaoImpl
import pl.mankevich.storageroom.dao.RelationsDaoImpl
import pl.mankevich.storageapi.dao.CharacterDao
import pl.mankevich.storageapi.dao.EpisodeDao
import pl.mankevich.storageapi.dao.LocationDao
import pl.mankevich.storageapi.dao.RelationsDao


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