package edu.ucne.composedemo.presentation.components

import androidx.compose.runtime.Composable

@Composable
fun Showcomponent(
    value: Boolean,
    whenContentIsTrue: @Composable () -> Unit = {},
    whenContentIsFalse: @Composable () -> Unit = {}
) {
    if (value) {
        whenContentIsTrue()
    } else {
        whenContentIsFalse()
    }
}