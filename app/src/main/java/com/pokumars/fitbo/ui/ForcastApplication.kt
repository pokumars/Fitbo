package com.pokumars.fitbo.ui

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.pokumars.fitbo.data.*
import com.pokumars.fitbo.data.database.WeatherDatabase
import com.pokumars.fitbo.data.network.*
import com.pokumars.fitbo.ui.suggestion.SuggestionViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ForecastApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { WeatherDatabase(instance()) }
        bind() from singleton { instance<WeatherDatabase>().currentWeatherDao() }
        bind() from singleton { instance<WeatherDatabase>().currentLocationDao() }
        bind<ConnectivityInterceptor>()with singleton {
            ConnectivityInterceptorImpl(
                instance()
            )
        }
        bind() from singleton { WeatherApiService(instance()) }
        //bind() from singleton { instance<WeatherDatabase>().currentLocationDao() }
        bind<WeatherNetworkDataSource>()with singleton {
            WeatherNetworkDataSourceImpl(
                instance()
            )
        }
        bind()from provider { SuggestionViewModelFactory(instance())}
        bind<LocationProvider>() with singleton { LocationProviderImpl() }
        bind<ForecastRepository>() with singleton {
            ForecastRepositoryImpl(
                instance(),
                instance(),
                instance(),
                instance()
            )
        }
    }
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)

    }
}