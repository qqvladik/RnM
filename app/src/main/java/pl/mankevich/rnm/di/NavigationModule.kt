package pl.mankevich.rnm.di

import dagger.Module
import pl.mankevich.characterslist.di.CharactersListEntryModule

@Module(
    includes = [
        CharactersListEntryModule::class
    ]
)
interface NavigationModule