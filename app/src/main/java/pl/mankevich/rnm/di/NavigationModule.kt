package pl.mankevich.rnm.di

import dagger.Module
import pl.mankevich.characterdetail.di.CharacterDetailEntryModule
import pl.mankevich.characterslist.di.CharactersListEntryModule
import pl.mankevich.episodeslist.di.EpisodesListEntryModule
import pl.mankevich.locationslist.di.LocationsListEntryModule

@Module(
    includes = [
        CharactersListEntryModule::class,
        LocationsListEntryModule::class,
        EpisodesListEntryModule::class,
        CharacterDetailEntryModule::class
    ]
)
interface NavigationModule