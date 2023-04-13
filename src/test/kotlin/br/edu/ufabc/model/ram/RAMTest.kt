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
        assertEquals(3, ram.frames.size)
        ram.frames.forEach {
            assertNull(it)
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

}