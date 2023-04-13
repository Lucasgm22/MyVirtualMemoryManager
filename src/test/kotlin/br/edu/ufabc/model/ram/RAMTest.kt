package br.edu.ufabc.model.ram

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

class RAMTest {

    private lateinit var ram: RAM

    @BeforeEach
    fun init() {
        ram = RAM(frameCount = 3)
    }


    @Test
    fun createRAM() {
        assertEquals(3, ram.frameAllocation.size)
        ram.frameAllocation.forEach {
            assertNull(it)
        }
    }
    @Test
    fun addPage_NoFrameIndexParameter() {
        assertEquals(0, ram.addPage(ArrayList()))
        assertEquals(1, ram.addPage(ArrayList()))
        assertEquals(2, ram.addPage(ArrayList()))

        assertThrows(IllegalStateException::class.java) {
            ram.addPage(ArrayList())
        }
    }

    @Test
    fun addPage_FrameIndexParameter() {
        assertEquals(1, ram.addPage(ArrayList(), 1))
        assertThrows(IndexOutOfBoundsException::class.java) {
            ram.addPage(ArrayList(), 3)
        }

        assertThrows(IndexOutOfBoundsException::class.java) {
            ram.addPage(ArrayList(), -1)
        }
    }

    @Test
    fun isFull() {
        assertFalse(ram.isFull())
        ram.addPage(ArrayList())
        assertFalse(ram.isFull())
        ram.addPage(ArrayList())
        ram.addPage(ArrayList())

        assertTrue(ram.isFull())
    }
}