package userInterface.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import userInterface.windows.CountLinesPopupWindow

class CountLinesAction : AnAction() {
    override fun actionPerformed(event: AnActionEvent) {
        val dialog = CountLinesPopupWindow(event.project)
        dialog.title = "Count Lines"

        dialog.show()
    }
}