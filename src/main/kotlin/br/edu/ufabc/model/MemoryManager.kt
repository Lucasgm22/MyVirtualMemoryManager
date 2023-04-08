package br.edu.ufabc.model

val ramFrameCount = 256 // Quantidade de frames na RAM
val ram = RAM(frameCount = ramFrameCount)

val pageCount = 256 // Quantidade de páginas na memória virtual
val pageTable = PageTable(pageCount = pageCount)

val lru = LRU(frameCount = ramFrameCount)

fun main() {
    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Hello World")

}
