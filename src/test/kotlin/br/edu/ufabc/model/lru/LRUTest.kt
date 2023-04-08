package br.edu.ufabc.model.lru

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class LRUTest {

    private val lru = LRU(frameCount = 3)
    @Test
    fun testGetFrameCount() {
        assertEquals(3, lru.frameCount)
    }

    @Test
    fun testLRU() {
        lru.addOnLRU(1)
        assertEquals(1, lru.entries.last())
        assertEquals(1, lru.entries.size)
        assertEquals(1, lru.getLastRecentUsed())

        lru.addOnLRU(2)
        assertEquals(2, lru.entries.last())
        assertEquals(2, lru.entries.size)
        assertEquals(1, lru.getLastRecentUsed())

        lru.addOnLRU(3)
        assertEquals(3, lru.entries.last())
        assertEquals(3, lru.entries.size)
        assertEquals(1, lru.getLastRecentUsed())

        lru.addOnLRU(4)
        assertEquals(4, lru.entries.last())
        assertFalse(lru.entries.contains(1))
        assertEquals(3, lru.entries.size)
        assertEquals(2, lru.getLastRecentUsed())

        lru.addOnLRU(1)
        assertEquals(1, lru.entries.last())
        assertFalse(lru.entries.contains(2))
        assertEquals(3, lru.entries.size)
        assertEquals(3, lru.getLastRecentUsed())

        lru.addOnLRU(2)
        assertEquals(2, lru.entries.last())
        assertFalse(lru.entries.contains(3))
        assertEquals(3, lru.entries.size)
        assertEquals(4, lru.getLastRecentUsed())

        lru.addOnLRU(5)
        assertEquals(5, lru.entries.last())
        assertFalse(lru.entries.contains(4))
        assertEquals(3, lru.entries.size)
        assertEquals(1, lru.getLastRecentUsed())

        lru.addOnLRU(1)
        assertEquals(1, lru.entries.last())
        assertEquals(3, lru.entries.size)
        assertEquals(2, lru.getLastRecentUsed())

        lru.addOnLRU(2)
        assertEquals(2, lru.entries.last())
        assertEquals(3, lru.entries.size)
        assertEquals(5, lru.getLastRecentUsed())

        lru.addOnLRU(3)
        assertEquals(3, lru.entries.last())
        assertFalse(lru.entries.contains(5))
        assertEquals(3, lru.entries.size)
        assertEquals(1, lru.getLastRecentUsed())


        lru.addOnLRU(4)
        assertEquals(4, lru.entries.last())
        assertFalse(lru.entries.contains(1))
        assertEquals(3, lru.entries.size)
        assertEquals(2, lru.getLastRecentUsed())

        lru.addOnLRU(5)
        assertEquals(5, lru.entries.last())
        assertFalse(lru.entries.contains(2))
        assertEquals(3, lru.entries.size)
        assertEquals(3, lru.getLastRecentUsed())

    }
}