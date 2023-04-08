class RAM(val frames: ArrayList<ArrayList<Int>?> = ArrayList(), val frameCount: Int) {

    private var occupiedFrames = 0
    init {
        for (i in 0 until frameCount) {
            frames.add(null)
        }
    }

    fun addPage(page: ArrayList<Int>): Int {
        if (isFull()) throw IllegalStateException("Can not add page, RAM is full")
        frames[occupiedFrames] = page
        return occupiedFrames++
    }

    fun addPage(page: ArrayList<Int>, frameIndex: Int): Int {
        frames[frameIndex] = page

        return frameIndex
    }
    fun isFull(): Boolean = occupiedFrames == frameCount
}
