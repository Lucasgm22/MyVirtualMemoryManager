package br.edu.ufabc.model.nonvolatilememory

import com.beust.klaxon.Klaxon

class NonVolatileMemory {

    companion object {
        private val pagesPath = "non_volatile_memory.json"
    }

    fun searchPageOnSecondaryMemory(pageIndex: Int): ArrayList<Int> {
        println("searchPageOnSecondaryMemory, pageIndex = [${pageIndex}]")
        object {}.javaClass.getResourceAsStream(pagesPath)?.use {
            return Klaxon().parseArray<ArrayList<Int>>(it)?.get(pageIndex)
                ?: throw IllegalArgumentException("Page not found in secondary memory")
        } ?: throw IllegalStateException("Could not access secondary memory")
    }
}