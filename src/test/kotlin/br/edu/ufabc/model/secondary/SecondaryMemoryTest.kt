package br.edu.ufabc.model.secondary

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class SecondaryMemoryTest {

    private val secondaryMemory = SecondaryMemory()

    @Test
    fun testSearchPage_Success() {
        assertDoesNotThrow { secondaryMemory.searchPage(0) }
        assertDoesNotThrow { secondaryMemory.searchPage(127) }
        assertDoesNotThrow { secondaryMemory.searchPage(255) }
    }
    @Test
    fun testSearchPage_Fails() {
        assertThrows(IndexOutOfBoundsException::class.java) { secondaryMemory.searchPage(-1) }
        assertThrows(IndexOutOfBoundsException::class.java) { secondaryMemory.searchPage(256) }
    }
}