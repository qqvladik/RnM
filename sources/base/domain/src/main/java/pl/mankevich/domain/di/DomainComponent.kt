package pl.mankevich.domain.di

import dagger.Component
import pl.mankevich.core.di.FeatureScope
import pl.mankevich.dataapi.di.DataProvider
import pl.mankevich.domainapi.di.DomainProvider

@FeatureScope
@Component(
    dependencies = [
        DataProvider::class
    ],
    modules = [
        DomainModule::class
    ]
)
interface DomainComponent : DomainProvider {

    companion object {

        fun init(
            dataProvider: DataProvider
        ): DomainProvider {
            return DaggerDomainComponent.factory()
                .create(dataProvider)
        }
    }

    @Component.Factory
    interface Factory {

        fun create(
            dataProvider: DataProvider
        ): DomainProvider
    }
}
