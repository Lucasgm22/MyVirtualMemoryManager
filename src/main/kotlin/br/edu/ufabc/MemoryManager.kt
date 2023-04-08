package br.edu.ufabc

import br.edu.ufabc.model.lru.LRU
import br.edu.ufabc.model.pagetable.PageTable
import br.edu.ufabc.model.ram.RAM
import com.beust.klaxon.Klaxon

val ramFrameCount = 256 // Quantidade de frames na RAM
val ram = RAM(frameCount = ramFrameCount)

val pageCount = 256 // Quantidade de páginas na memória virtual
val pageTable = PageTable(pageCount = pageCount)

val lru = LRU(frameCount = ramFrameCount)

fun main() {
    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Hello World")



}

fun searchPageOnSecondaryMemory(pageIndex: Int): ArrayList<Int> {
    println("searchPageOnSecondaryMemory, pageIndex = [${pageIndex}]")
    object {}.javaClass.getResourceAsStream("br/edu/ufabc/model/nonvolatilememory/non_volatile_memory.json")?.use {
        return Klaxon().parseArray<ArrayList<Int>>(it)?.get(pageIndex)
            ?: throw IllegalArgumentException("Page not found in secondary memory")
    } ?: throw IllegalStateException("Could not access secondary memory")
}
