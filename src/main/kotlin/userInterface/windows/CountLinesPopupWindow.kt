package userInterface.windows

import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import core.counters.LinesCounter
import javax.swing.*

class CountLinesPopupWindow(project: Project?) : DialogWrapper(project) {
    private var countLinesPanel: JPanel? = null
    private var folderPath: JTextField? = null
    private var extensions: JTextField? = null
    private var countButton: JButton? = null
    private var linesCountResults: JTextArea? = null

    private val linesCounter: LinesCounter = LinesCounter()

    init {
        countButton!!.addActionListener {
            linesCountResults!!.text = ""
            val countResults = linesCounter.count(folderPath!!.text, extensions!!.text.split(" "))

            countResults.forEach {
                linesCountResults!!.text += "${it.extension}: ${it.count}\r\n"
            }
        }

        init()
    }

    override fun createCenterPanel(): JComponent? {
        return countLinesPanel
    }

    override fun createSouthPanel(): JComponent? {
        return null
    }
}