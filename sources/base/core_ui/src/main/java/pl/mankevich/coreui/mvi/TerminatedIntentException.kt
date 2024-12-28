package pl.mankevich.coreui.mvi

import kotlinx.coroutines.CancellationException

internal class TerminatedIntentException : CancellationException("Terminated intent")
