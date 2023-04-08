import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class MainKtTest {

    @Test
    fun testSearchPageOnSecondaryMemory_Success() {
        assertDoesNotThrow { searchPageOnSecondaryMemory(0) }
        assertDoesNotThrow { searchPageOnSecondaryMemory(127) }
        assertDoesNotThrow { searchPageOnSecondaryMemory(255) }
    }
    @Test
    fun testSearchPageOnSecondaryMemory_Fails() {
        assertThrows(IndexOutOfBoundsException::class.java) { searchPageOnSecondaryMemory(-1) }
        assertThrows(IndexOutOfBoundsException::class.java) { searchPageOnSecondaryMemory(256) }
    }

}