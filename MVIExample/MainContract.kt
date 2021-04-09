package com.janfranco.mviexample

import com.janfranco.mviexample.base.UIEffect
import com.janfranco.mviexample.base.UIEvent
import com.janfranco.mviexample.base.UIState

class MainContract {

    data class State(
        val randomNumberState: RandomNumberState
    ): UIState

    sealed class RandomNumberState {
        object Idle: RandomNumberState()
        object Loading: RandomNumberState()
        data class Success(val number: Int): RandomNumberState()
    }

    sealed class Event : UIEvent {
        object OnRandomNumberClicked: Event()
    }

    sealed class Effect: UIEffect {
        object ShowToast: Effect()
    }
}
