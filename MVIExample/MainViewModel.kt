package com.janfranco.mviexample

import androidx.lifecycle.viewModelScope
import com.janfranco.mviexample.base.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : BaseViewModel<MainContract.State, MainContract.Event, MainContract.Effect>() {

    override fun createInitialState(): MainContract.State =
        MainContract.State(
            MainContract.RandomNumberState.Idle
        )

    override fun handleEvent(event: MainContract.Event) {
        when (event) {
            is MainContract.Event.OnRandomNumberClicked -> {
                generateRandomNumber()
            }
        }
    }

    private fun generateRandomNumber() {
        viewModelScope.launch {
            setState {
                copy(randomNumberState = MainContract.RandomNumberState.Loading)
            }
            try {
                delay(5000)
                val random = (0..10).random()
                if (random % 2 == 0) {
                    setState {
                        copy(randomNumberState = MainContract.RandomNumberState.Idle)
                    }
                    throw RuntimeException("Number is even")
                }
                setState { copy(randomNumberState = MainContract.RandomNumberState.Success(number = random)) }
            } catch (exception : Exception) {
                setEffect {
                    MainContract.Effect.ShowToast
                }
            }
        }
    }

}
