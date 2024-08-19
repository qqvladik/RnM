package pl.mankevich.rnm.di

import dagger.Module
import pl.mankevich.characterdetail.di.CharacterDetailEntryModule
import pl.mankevich.characterslist.di.CharactersListEntryModule

@Module(
    includes = [
        CharactersListEntryModule::class,
        CharacterDetailEntryModule::class
    ]
)
interface NavigationModule