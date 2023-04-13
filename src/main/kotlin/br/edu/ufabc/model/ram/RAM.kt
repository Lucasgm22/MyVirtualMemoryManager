package br.edu.ufabc.model.ram

class RAM(val frames: ArrayList<ArrayList<Int>?> = ArrayList(), val frameCount: Int) {
    
    init {
        for (i in 1..frameCount) {
            frames.add(null)
        }
    }

    fun addPage(page: ArrayList<Int>, frameIndex: Int): Int {
        frames[frameIndex] = page
        println("[{${javaClass.simpleName}}] Added on frame [$frameIndex]")
        return frameIndex
    }
}
