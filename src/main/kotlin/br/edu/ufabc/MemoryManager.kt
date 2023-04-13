package br.edu.ufabc

import br.edu.ufabc.model.lru.LRU
import br.edu.ufabc.model.pagetable.PageTable
import br.edu.ufabc.model.ram.RAM
import br.edu.ufabc.model.secondary.SecondaryMemory

const val offsets = 128
const val ramFrameSize = 256
const val simpleName = "MemoryManager"
const val horizontalLine = "------------------------------------------"

var pageCount: Int = 256
var ramProcessFrameCount: Int? = null
lateinit var ram: RAM
lateinit var lru: LRU

lateinit var pageTable: PageTable

val secondaryMemory = SecondaryMemory()

var amountOfCpuPageRequests: Int? = null
var amountOfPageFaults = 0
var amountOfPageReplacements = 0
var amountOfSearchesOnSecondaryMemory = 0
var amountOfTimesPageInMemory = 0
var occupiedFrames = 0
var initialFrame = 0

fun main(args: Array<String>) {

    init(args)
    println("INITIAL ALLOCATION OF PAGES SEQUENTIALLY")
    println(horizontalLine)
    frameAllocation()
    println(horizontalLine)

    println("CPU REQUESTS")
    println(horizontalLine)

    for (i in 1..amountOfCpuPageRequests!!) {
        val pageIndex: Int = (0 until pageTable.pageCount).random() // PAGE AND OFFSET THAT WILL BE REQUESTED
        val offset: Int = (0 until offsets).random()

        println("[$simpleName] CPU request [$i], page [$pageIndex], offset [$offset]")
        frameAllocation(pageIndex, offset)
        println(horizontalLine)
    }
    println("STATISTICS")
    println(horizontalLine)
    println("[$simpleName] Amount of CPU page requests: $amountOfCpuPageRequests")
    println("[$simpleName] Amount of frames allocated on RAM: $ramProcessFrameCount")
    println("[$simpleName] Amount of Page Faults: $amountOfPageFaults")
    println("[$simpleName] Amount of Page Replacements: $amountOfPageReplacements")
    println("[$simpleName] Amount of Searches on Secondary Memory: $amountOfSearchesOnSecondaryMemory")
    println("[$simpleName] Amount of Searches on Secondary Memory (Excluding initial allocation): ${amountOfSearchesOnSecondaryMemory - ramProcessFrameCount!!}")
    println("[$simpleName] Amount of Times Page on Memory: $amountOfTimesPageInMemory")
    println(horizontalLine)
}

private fun frameAllocation(pageIndex: Int, offset: Int) {

    val frameIndex: Int

    val pageIndexLRU = lru.addOnLRU(pageIndex)

    if (pageTable.getValidBitByPage(pageIndex)) {
        amountOfTimesPageInMemory++
        frameIndex = pageTable.getFrameByPage(pageIndex)
        println("[$simpleName] Page [$pageIndex] is on memory at frame [$frameIndex]")
    } else {
        amountOfPageFaults++
        println("[$simpleName] Page [$pageIndex] is not in memory")
        val requestedPage = secondaryMemory.searchPage(pageIndex)
        if (occupiedFrames == ramProcessFrameCount!!) {
            amountOfPageReplacements++
            println("[$simpleName] There is no free space to allocate page [$pageIndex] in ram, calling pageReplacement algorithm")
            println("[$simpleName] Last recently used is page [$pageIndexLRU]")
            frameIndex = pageTable.getFrameByPage(pageIndexLRU)
            ram.frames[frameIndex] = requestedPage
            pageTable.removePageEntry(pageIndexLRU)
        } else {
            println("[$simpleName] There is free space to allocate page [$pageIndex] in memory")
            frameIndex = ram.addPage(requestedPage, initialFrame + occupiedFrames)
            occupiedFrames++
        }
        pageTable.addFrameOnPageEntry(pageIndex, frameIndex)
    }
    assert(ram.frames[frameIndex] != null)
    assert(ram.frames[frameIndex]?.get(offset) == 0 || ram.frames[frameIndex]?.get(offset) == 1)
    // deliver the page to the CPU with offset
}

private fun frameAllocation() {
    initialFrame = (0 ..  ramFrameSize - ramProcessFrameCount!!).random()
    println("[$simpleName] Allocation will be on frames [$initialFrame.. ${initialFrame + ramProcessFrameCount!!}] sequentially")
    for (i in 0 until ramProcessFrameCount!!) {
        val page = secondaryMemory.searchPage(i)
        lru.addOnLRU(i)
        ram.frames[initialFrame + i] = page
        pageTable.addFrameOnPageEntry(i, initialFrame + i)
        occupiedFrames++
    }
}

private fun init(args: Array<String>) {
    assert(args.size == 2)
    ramProcessFrameCount = args[0].toInt()
    if (ramProcessFrameCount!! <= 0 || ramProcessFrameCount!! > 256) throw IllegalStateException("Ram Allocation must be between 1 and 256 frames, receive $ramProcessFrameCount")
    amountOfCpuPageRequests = args[1].toInt()
    if (amountOfCpuPageRequests!! <= 0) throw IllegalStateException("CPU requests must be greater than 0, received $amountOfCpuPageRequests")

    ram = RAM(frameCount = ramFrameSize)
    pageTable = PageTable(pageCount = pageCount)
    lru = LRU(frameCount = ramProcessFrameCount!!)
    println(horizontalLine)
    println("CONFIGS")
    println(horizontalLine)
    println("[$simpleName] Amount of frames allocated on RAM: $ramProcessFrameCount")
    println("[$simpleName] Amount of CPU page requests: $amountOfCpuPageRequests")


    println(horizontalLine)
}
