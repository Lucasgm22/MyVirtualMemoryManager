package br.edu.ufabc.model.pagetable

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.lang.IllegalStateException

class PageTableTest {

    lateinit var pageTable: PageTable

    @BeforeEach
    fun init() {
        pageTable = PageTable(pageCount = 5)
    }

    @Test
    fun createPageTable() {
        assertEquals(5, pageTable.entries.size)
        pageTable.entries.forEach {
            assertFalse(it.validInvalidBit)
        }
    }
    @Test
    fun removePageEntry() {
        pageTable.addFrameOnPageEntry(1, 2)
        pageTable.removePageEntry(1)
        assertFalse(pageTable.getValidBitByPage(1))
    }

    @Test
    fun addFrameOnPageEntry() {
        pageTable.addFrameOnPageEntry(1, 2)
        assertTrue(pageTable.getValidBitByPage(1))
        assertEquals(2, pageTable.getFrameByPage(1))
    }


    @Test
    fun getFrameByPage() {
        pageTable.addFrameOnPageEntry(1, 2)

        assertEquals(2, pageTable.getFrameByPage(1))
    }

    @Test
    fun getFrameByPage_Exception() {
        assertThrows (IllegalStateException::class.java) {
            assertFalse(pageTable.getValidBitByPage(1))
            pageTable.getFrameByPage(1)
        }
    }

    @Test
    fun getValidInvalidBit() {
        assertFalse(pageTable.getValidBitByPage(1))
        pageTable.addFrameOnPageEntry(1, 2)
        assertTrue(pageTable.getValidBitByPage(1))
    }
}