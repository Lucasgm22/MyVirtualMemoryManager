package br.edu.ufabc.model.pagetable

class PageTable(val entries: ArrayList<PageEntry> = ArrayList(), val pageCount: Int) {
    class PageEntry {
        var validInvalidBit: Boolean = false
        var frameIndex: Int = -1
    }

    init {
        for (i in 1.. pageCount) {
            entries.add(PageEntry())
        }
    }


    fun removePageEntry(pageIndex: Int) {
        println("[${javaClass.simpleName}] pageReplacement, removing [$pageIndex] of memory at frame [${entries[pageIndex].frameIndex}]")
        entries[pageIndex].validInvalidBit = false
    }

    fun addFrameOnPageEntry(pageIndex: Int, frameIndex: Int) {
        println("[${javaClass.simpleName}] Added page [$pageIndex] in memory at frame [$frameIndex]")
        entries[pageIndex].frameIndex = frameIndex
        entries[pageIndex].validInvalidBit = true
    }

    fun getFrameByPage(pageIndex: Int): Int {
        if (!getValidBitByPage(pageIndex)) throw IllegalStateException("Page $pageIndex is not in memory, does not have a frame")

        return entries[pageIndex].frameIndex
    }

    fun getValidBitByPage(pageIndex: Int) = entries[pageIndex].validInvalidBit
}