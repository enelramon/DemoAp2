package edu.ucne.composedemo.presentation.cxc

sealed interface CxcEvent {
    data object GetCxc : CxcEvent
}