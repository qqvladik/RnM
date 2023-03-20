package pl.mankevich.core_impl.di

import android.content.Context
import dagger.Component
import pl.mankevich.core.di.providers.AndroidDependenciesProvider

@Component(
    modules = [
        AndroidDependenciesModule::class
    ]
)
interface AndroidDependenciesComponent : AndroidDependenciesProvider {

    companion object {

        //TODO попробовать здесь контекст сохранять с помощью @BindsInstance
        // - проверил, используется с абстрактными методами только. Также надобность в AndroidDependenciesModule
        // отпадает, ибо сейчас он возвращает только context, к которому и так будет доступ через @BindsInstance.
        // Однако этот модуль понадобится для других зависимостей в будущем.
        fun init(context: Context): AndroidDependenciesProvider {
            return DaggerAndroidDependenciesComponent.builder()
                .androidDependenciesModule(AndroidDependenciesModule(context))
                .build()
        }
    }
}