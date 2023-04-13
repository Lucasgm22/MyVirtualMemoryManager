package br.edu.ufabc

import br.edu.ufabc.model.lru.LRU
import br.edu.ufabc.model.pagetable.PageTable
import br.edu.ufabc.model.ram.RAM
import br.edu.ufabc.model.secondary.SecondaryMemory

const val offsets = 128
const val simpleName = "MemoryManager"
const val horizontalLine = "------------------------------------------"

var pageCount: Int = 256
var ramFrameCount: Int? = null
lateinit var ram: RAM
lateinit var lru: LRU

lateinit var pageTable: PageTable

val secondaryMemory = SecondaryMemory()

var amountOfCpuPageRequests: Int? = null
var amountOfPageFaults = 0
var amountOfPageReplacements = 0
var amountOfSearchesOnSecondaryMemory = 0
var amountOfTimesPageInMemory = 0

fun main(args: Array<String>) {

    init(args)
    println("CPU REQUESTS")
    println(horizontalLine)

    for (i in 1..amountOfCpuPageRequests!!) {
        val pageIndex: Int = (0 until pageTable.pageCount).random() // PAGE AND OFFSET THAT WILL BE REQUESTED
        val offset: Int = (0 until offsets).random()

        var cpuRequestedPage: ArrayList<Int>?
        var frameIndex: Int?

        println("[$simpleName] CPU request [$i], page [$pageIndex], offset [$offset]")
        val pageIndexLRU = lru.addOnLRU(pageIndex)

        if (pageTable.getValidBitByPage(pageIndex)) {
            amountOfTimesPageInMemory++
            frameIndex = pageTable.getFrameByPage(pageIndex)
            println("[$simpleName] Page [$pageIndex] is on memory at frame [$frameIndex]")
        } else {
            amountOfPageFaults++
            println("[$simpleName] Page [$pageIndex] is not in memory")
            val requestedPage = secondaryMemory.searchPage(pageIndex)
            if (ram.isFull()) {
                amountOfPageReplacements++
                println("[$simpleName] There is no free space to allocate page [$pageIndex] in ram, calling pageReplacement algorithm")
                frameIndex = pageTable.getFrameByPage(pageIndexLRU)
                ram.frameAllocation[frameIndex] = requestedPage
                pageTable.removePageEntry(pageIndexLRU)
            } else {
                println("[$simpleName] There is free space to allocate page [$pageIndex] in memory")
                frameIndex = ram.addPage(requestedPage)
            }
            pageTable.addFrameOnPageEntry(pageIndex, frameIndex)
        }
        cpuRequestedPage = ram.frameAllocation[frameIndex]
        assert(cpuRequestedPage != null)
        assert(cpuRequestedPage?.get(offset) == 0 || cpuRequestedPage?.get(offset) == 1)
        // deliver the page to the CPU with offset
        println(horizontalLine)
    }
    println("STATISTICS")
    println(horizontalLine)
    println("[$simpleName] Amount of CPU page requests: $amountOfCpuPageRequests")
    println("Amount of Page Faults: $amountOfPageFaults")
    println("Amount of Page Replacements: $amountOfPageReplacements")
    println("Amount of Searches on Secondary Memory: $amountOfSearchesOnSecondaryMemory")
    println("Amount of Times Page on Memory: $amountOfTimesPageInMemory")
    println(horizontalLine)
}

private fun init(args: Array<String>) {
    assert(args.size == 2)
    ramFrameCount = args[0].toInt()
    if (ramFrameCount!! <= 0 || ramFrameCount!! > 256) throw IllegalStateException("Ram Allocation must be between 1 and 256 frames, receive $ramFrameCount")
    amountOfCpuPageRequests = args[1].toInt()
    if (amountOfCpuPageRequests!! <= 0) throw IllegalStateException("CPU requests must be greater than 0, received $amountOfCpuPageRequests")

    ram = RAM(frameCount = ramFrameCount!!)
    pageTable = PageTable(pageCount = pageCount)
    lru = LRU(frameCount = ramFrameCount!!)
    println(horizontalLine)
    println("CONFIGS")
    println(horizontalLine)
    println("[$simpleName] Amount of frames on RAM: ${ram.frameCount}")
    println("[$simpleName] Amount of CPU page requests: $amountOfCpuPageRequests")
    println(horizontalLine)
}
