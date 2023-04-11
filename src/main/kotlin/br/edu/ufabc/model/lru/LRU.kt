package br.edu.ufabc.model.lru

class LRU(val entries: ArrayList<Int> = ArrayList(), val frameCount: Int) {
    fun addOnLRU(pageIndex: Int) : Int {

        if (entries.contains(pageIndex)) {
            if (entries.last() == pageIndex) return entries.first()

            entries.remove(pageIndex)
            entries.add(pageIndex)
            return entries.first()
        }

        if (entries.size < frameCount) {
            entries.add(pageIndex)
            return entries.first()
        } else {
            entries.add(pageIndex)
            return entries.removeFirst()
        }
    }

    fun getLastRecentUsed() = entries.first()
}