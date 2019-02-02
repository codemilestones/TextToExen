import java.io.File

class FileScaner(scanPath: String, savePath: String) {
    var root = scanPath
    var save = savePath
    val adapter = EnexAdapter()

    private fun readTextToEnex(file: File) {
        adapter.setText(file.parentFile.name + "\\" + file.name.split('.')[0], file.readText(), file.lastModified())
        adapter.getAllText()
    }

    private fun writeTextToFile(name: String, data: String) {
        val file = File(save, name + ".enex")
        file.writeText(data)
    }

    fun traverseFileTree() {
        val systemDir = File(root)
        val fileTree: FileTreeWalk = systemDir.walk()
        fileTree.maxDepth(10)
            .filter { it.isFile }
            .filter { it.extension == "txt" }
            .forEach {
                readTextToEnex(it)
            }
        writeTextToFile("export", adapter.export())
    }
}

fun main() {
    val scaner = FileScaner("scan path", "Export path")
    scaner.traverseFileTree()
}