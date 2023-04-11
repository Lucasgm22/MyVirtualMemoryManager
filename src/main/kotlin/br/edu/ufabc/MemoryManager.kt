package br.edu.ufabc

import br.edu.ufabc.model.lru.LRU
import br.edu.ufabc.model.pagetable.PageTable
import br.edu.ufabc.model.ram.RAM
import br.edu.ufabc.model.secondary.SecondaryMemory

val offsets = 128
var pageCount: Int = 256
var ramFrameCount: Int? = null
lateinit var ram: RAM
lateinit var lru: LRU

lateinit var pageTable: PageTable

val secondaryMemory = SecondaryMemory()

var amountOfCpuPageRequests: Int? = null


val simpleName = "MemoryManager"

fun main(args: Array<String>) {

    init(args)

    for (i in 1..amountOfCpuPageRequests!!) {
        val pageIndex: Int = (0 until pageTable.pageCount).random() // PAGE AND OFFSET THAT WILL BE REQUESTED
        val offset: Int = (0 until offsets).random()

        var cpuRequestedPage: ArrayList<Int>?
        var frameIndex: Int?

        // ADD TO LRU AND RETURN LAST RECENTLY USED
        val pageIndexLRU = lru.addOnLRU(pageIndex)

        if (pageTable.getValidBitByPage(pageIndex)) {
            // PAGE IS ON MEMORY
            frameIndex = pageTable.getFrameByPage(pageIndex)
        } else {
            // PAGE IS NOT IN MEMORY
            // SEARCH ON SECONDARY MEMORY
            val requestedPage = secondaryMemory.searchPage(pageIndex)
            if (ram.isFull()) {
                // TAKE LRU AND REMOVE FROM RAM
                frameIndex = pageTable.getFrameByPage(pageIndexLRU)
                ram.frames[frameIndex] = requestedPage
                pageTable.removePageEntry(pageIndexLRU)
            } else {
                // ADD PAGE ON RAM
                frameIndex = ram.addPage(requestedPage)
            }
            pageTable.addFrameOnPageEntry(pageIndex, frameIndex)
        }
        cpuRequestedPage = ram.frames[frameIndex]
        assert(cpuRequestedPage != null)
        assert(cpuRequestedPage?.get(offset) == 0 || cpuRequestedPage?.get(offset) == 1)
        // deliver the page to the CPU with offset
    }
}

private fun init(args: Array<String>) {
    assert(args.size == 2)
    ramFrameCount = args[0].toInt()
    assert(ramFrameCount!! > 0)
    amountOfCpuPageRequests = args[1].toInt()
    assert(amountOfCpuPageRequests!! > 0)

    ram = RAM(frameCount = ramFrameCount!!)
    pageTable = PageTable(pageCount = pageCount)
    lru = LRU(frameCount = ramFrameCount!!)
    println("[$simpleName] amount of frames on RAM: ${ram.frameCount}")
    println("[$simpleName] amount of CPU page requests: $amountOfCpuPageRequests")
}
