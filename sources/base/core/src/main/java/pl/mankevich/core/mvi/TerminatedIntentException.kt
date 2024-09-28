package pl.mankevich.core.mvi

import kotlinx.coroutines.CancellationException

internal class TerminatedIntentException : CancellationException("Terminated intent")
