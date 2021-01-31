package com.janfranco.cleanarchitectureexample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.janfranco.cleanarchitectureexample.R
import com.janfranco.cleanarchitectureexample.framework.FakeLocationSource
import com.janfranco.cleanarchitectureexample.framework.InMemoryLocationPersistenceSource
import com.janfranco.data.LocationsRepository
import com.janfranco.usecases.GetLocations
import com.janfranco.usecases.RequestNewLocation

class MainActivity : AppCompatActivity(), MainPresenter.View {

    private val presenter: MainPresenter

    init {
        val persistence = InMemoryLocationPersistenceSource()
        val deviceLocation = FakeLocationSource()
        val locationsRepository = LocationsRepository(persistence, deviceLocation)
        presenter = MainPresenter(
            this,
            GetLocations(locationsRepository),
            RequestNewLocation(locationsRepository)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.onDestroy()
    }

    override fun renderLocations(locations: List<Location>) {
        
    }

}
