package br.edu.ufabc.model.nonvolatilememory

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class NonVolatileMemoryTest {

    private val nonVolatileMemory = NonVolatileMemory()

    @Test
    fun testSearchPageOnSecondaryMemory_Success() {
        assertDoesNotThrow { nonVolatileMemory.searchPageOnSecondaryMemory(0) }
        assertDoesNotThrow { nonVolatileMemory.searchPageOnSecondaryMemory(127) }
        assertDoesNotThrow { nonVolatileMemory.searchPageOnSecondaryMemory(255) }
    }
    @Test
    fun testSearchPageOnSecondaryMemory_Fails() {
        assertThrows(IndexOutOfBoundsException::class.java) { nonVolatileMemory.searchPageOnSecondaryMemory(-1) }
        assertThrows(IndexOutOfBoundsException::class.java) { nonVolatileMemory.searchPageOnSecondaryMemory(256) }
    }
}