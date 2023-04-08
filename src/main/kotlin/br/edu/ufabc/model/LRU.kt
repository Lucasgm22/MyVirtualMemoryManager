package br.edu.ufabc.model

class LRU(val entries: ArrayList<Int> = ArrayList(), val frameCount: Int) {
    fun addOnLRU(pageIndex: Int) {

        if (entries.contains(pageIndex)) {
            entries.remove(pageIndex)
            entries.add(pageIndex)
            return
        }

        if (entries.size < frameCount) {
            entries.add(pageIndex)
        } else {
            entries.removeFirst()
            entries.add(pageIndex)
        }
    }

    fun getLastRecentUsed() = entries.first()
}