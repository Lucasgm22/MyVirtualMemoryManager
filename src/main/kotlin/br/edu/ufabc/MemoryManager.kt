package br.edu.ufabc

import br.edu.ufabc.model.lru.LRU
import br.edu.ufabc.model.pagetable.PageTable
import br.edu.ufabc.model.ram.RAM
import br.edu.ufabc.model.secondary.SecondaryMemory
import com.beust.klaxon.Klaxon

val ramFrameCount = 256 // Quantidade de frames na RAM
val ram = RAM(frameCount = ramFrameCount)

val pageCount = 256 // Quantidade de páginas na memória virtual
val pageTable = PageTable(pageCount = pageCount)

val secondaryMemory = SecondaryMemory()

val lru = LRU(frameCount = ramFrameCount)

fun main() {

    while (true) {
        val pageIndex: Int = 0 // PAGE AND OFFSET THAT WILL BE REQUESTED
        val offset: Int = 0


        // GET LRU AND ADD TO RLU
        val pageIndexLRU = lru.getLastRecentUsed()
        lru.addOnLRU(pageIndex)

        if (pageTable.getValidBitByPage(pageIndex)) {
            // PAGE IS ON MEMORY
            val requestedPage = ram.frames[pageTable.getFrameByPage(pageIndex)]
        } else {
            // PAGE IS NOT IN MEMORY

            // SEARCH ON SECONDARY MEMORY
            val resquestedPage = secondaryMemory.searchPage(pageIndex)
            if (ram.isFull()) {
                // TAKE LRU AND REMOVE FROM RAM
                val frameIndex = pageTable.getFrameByPage(pageIndexLRU)
                ram.frames[frameIndex] = resquestedPage
                pageTable.removePageEntry(pageIndexLRU)
            } else {
                // ADD PAGE ON RAM
               val frameIndex = ram.addPage(resquestedPage)
                // UPDATE PAGE TABLE
                pageTable.addFrameOnPageEntry(pageIndex, frameIndex)
            }
        }
    }

}

fun searchPageOnSecondaryMemory(pageIndex: Int): ArrayList<Int> {
    println("searchPageOnSecondaryMemory, pageIndex = [${pageIndex}]")
    object {}.javaClass.getResourceAsStream("br/edu/ufabc/model/secondary/non_volatile_memory.json")?.use {
        return Klaxon().parseArray<ArrayList<Int>>(it)?.get(pageIndex)
            ?: throw IllegalArgumentException("Page not found in secondary memory")
    } ?: throw IllegalStateException("Could not access secondary memory")
}
