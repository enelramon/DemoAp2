package edu.ucne.composedemo.domain.tareas.usecase

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class TaskValidationsTest {

    @Test
    fun `validateDescripcion fails on blank`() {
        val res = validateDescripcion("")
        assertThat(res.isValid).isFalse()
        assertThat(res.error).isNotEmpty()
    }

    @Test
    fun `validateDescripcion fails on short`() {
        val res = validateDescripcion("ab")
        assertThat(res.isValid).isFalse()
    }

    @Test
    fun `validateDescripcion passes on ok`() {
        val res = validateDescripcion("abc")
        assertThat(res.isValid).isTrue()
        assertThat(res.error).isNull()
    }

    @Test
    fun `validateTiempo fails on blank`() {
        val res = validateTiempo("")
        assertThat(res.isValid).isFalse()
    }

    @Test
    fun `validateTiempo fails on non numeric`() {
        val res = validateTiempo("abc")
        assertThat(res.isValid).isFalse()
    }

    @Test
    fun `validateTiempo fails on zero or negative`() {
        assertThat(validateTiempo("0").isValid).isFalse()
        assertThat(validateTiempo("-1").isValid).isFalse()
    }

    @Test
    fun `validateTiempo passes on positive integer`() {
        val res = validateTiempo("5")
        assertThat(res.isValid).isTrue()
    }
}
