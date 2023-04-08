class RAM(val frames: ArrayList<ArrayList<Int>?> = ArrayList(), val frameCount: Int) {

    private var occupiedFrames = 0
    init {
        for (i in 0 until frameCount) {
            frames.add(null)
        }
    }
    fun addPage(page: ArrayList<Int>, frameIndex: Int = occupiedFrames): Int {
        frames[frameIndex] = page
        if (occupiedFrames < frameCount) occupiedFrames++

        return frameIndex
    }
    fun isFull(): Boolean = occupiedFrames == frameCount
}
