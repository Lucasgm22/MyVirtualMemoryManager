package br.edu.ufabc.model.ram

class RAM(val frameAllocation: ArrayList<ArrayList<Int>?> = ArrayList(), val frameCount: Int) {

    private var occupiedFrames = 0
    init {
        for (i in 1..frameCount) {
            frameAllocation.add(null)
        }
    }

    fun addPage(page: ArrayList<Int>): Int {
        if (isFull()) throw IllegalStateException("Can not add page, RAM is full")
        frameAllocation[occupiedFrames] = page
        println("[${javaClass.simpleName}] Added on frame [$occupiedFrames]")
        return occupiedFrames++
    }

    fun addPage(page: ArrayList<Int>, frameIndex: Int): Int {
        frameAllocation[frameIndex] = page
        println("[{${javaClass.simpleName}}] Added on frame [$frameIndex]")
        return frameIndex
    }
    fun isFull(): Boolean = occupiedFrames == frameCount
}
