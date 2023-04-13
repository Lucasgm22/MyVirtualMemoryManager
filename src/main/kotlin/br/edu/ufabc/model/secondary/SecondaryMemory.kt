package br.edu.ufabc.model.secondary

import br.edu.ufabc.amountOfSearchesOnSecondaryMemory
import com.beust.klaxon.Klaxon

class SecondaryMemory {

    companion object {
        private const val pagesPath = "secondary_memory.json"
    }

    fun searchPage(pageIndex: Int): ArrayList<Int> {
        amountOfSearchesOnSecondaryMemory++
        println("[${javaClass.simpleName}] SearchPageOnSecondaryMemory, pageIndex = [${pageIndex}]")
        object {}.javaClass.getResourceAsStream(pagesPath)?.use {
            return Klaxon().parseArray<ArrayList<Int>>(it)?.get(pageIndex)
                ?: throw IllegalArgumentException("Page not found in secondary memory")
        } ?: throw IllegalStateException("Could not access secondary memory")
    }
}