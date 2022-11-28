package core.counters

import com.intellij.openapi.vfs.VfsUtil
import com.intellij.openapi.vfs.VfsUtilCore
import com.intellij.openapi.vfs.VirtualFile
import com.intellij.openapi.vfs.VirtualFileVisitor
import core.exceptions.NotFoundException
import core.models.CountResult
import java.nio.file.Path

class LinesCounter {
    fun count(folderPath: String, extensions: List<String>): List<CountResult> {
        val result = ArrayList<CountResult>()

        val directory =
            VfsUtil.findFile(Path.of(folderPath), true) ?: throw NotFoundException("Directory is not found!")
        directory.refresh(false, true)

        VfsUtilCore.visitChildrenRecursively(directory, object : VirtualFileVisitor<Any?>() {
            override fun visitFile(file: VirtualFile): Boolean {
                if (!file.isDirectory && extensions.contains(file.extension)) {
                    var extensionResult = result.find { element -> element.extension == file.extension }

                    if (extensionResult == null) {
                        val newExtensionCountResult = CountResult(extension = file.extension!!, 0)
                        result.add(newExtensionCountResult)
                        extensionResult = newExtensionCountResult
                    }

                    val fileInformation = file.inputStream.readAllBytes()

                    for (i in 0..(fileInformation.size - 2)) {
                        if (fileInformation[i] == 13.toByte() && fileInformation[i + 1] == 10.toByte()) {
                            extensionResult.count += 1
                        }
                    }
                }

                return true
            }
        })

        return result
    }
}