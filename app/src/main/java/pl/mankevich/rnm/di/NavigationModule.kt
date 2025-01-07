package pl.mankevich.rnm.di

import dagger.Module
import pl.mankevich.characterdetail.di.CharacterDetailEntryModule
import pl.mankevich.characterslist.di.CharactersListEntryModule
import pl.mankevich.episodedetail.di.EpisodeDetailEntryModule
import pl.mankevich.episodeslist.di.EpisodesListEntryModule
import pl.mankevich.locationdetail.di.LocationDetailEntryModule
import pl.mankevich.locationslist.di.LocationsListEntryModule

@Module(
    includes = [
        CharactersListEntryModule::class,
        LocationsListEntryModule::class,
        EpisodesListEntryModule::class,
        CharacterDetailEntryModule::class,
        LocationDetailEntryModule::class,
        EpisodeDetailEntryModule::class,
    ]
)
interface NavigationModule