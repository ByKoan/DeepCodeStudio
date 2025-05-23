package com.dr10.editor.ui.tabs.components

import com.dr10.common.ui.AppIcons
import com.dr10.common.ui.ThemeApp
import com.dr10.common.ui.extensions.mouseEventListener
import com.dr10.common.utilities.ColorUtils.toAWTColor
import com.dr10.common.utilities.FlowStateHandler
import com.dr10.common.utilities.setState
import com.dr10.editor.ui.viewModels.EditorTabViewModel
import java.awt.Graphics
import java.awt.Graphics2D
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.RenderingHints
import javax.swing.JLabel
import javax.swing.JPanel

/**
 * Custom [JPanel] that implements a toggle button for read-only/editable state
 *
 * @property viewModel The view model that handles the editor ui state
 * @property state The state wrapper that maintains the editor tab's current state
 */
class ReadOnlyButton(
    private val viewModel: EditorTabViewModel,
    private val state: FlowStateHandler.StateWrapper<EditorTabViewModel.EditorTabState>
): JPanel() {

    private var backgroundColor = ThemeApp.colors.secondColor.toAWTColor()

    init { onCreate() }

    private fun onCreate() {
        layout = GridBagLayout()

        this.mouseEventListener(
            onEnter = { backgroundColor = ThemeApp.colors.hoverTab.toAWTColor() },
            onExit = { backgroundColor = ThemeApp.colors.secondColor.toAWTColor() },
            onClick = { viewModel.setIsEditable(!state.value.isEditable) }
        )

        add(
            JLabel().apply {
                setState(state, EditorTabViewModel.EditorTabState::isEditable) { isEditable ->
                    icon = if (isEditable) AppIcons.writeEnableIcon else AppIcons.readOnlyIcon
                }
            },
            GridBagConstraints().apply {
                anchor = GridBagConstraints.CENTER
            }
        )

    }

    override fun paintComponent(graphics: Graphics) {
        val graphics2D = graphics as Graphics2D
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON)

        graphics.color = backgroundColor
        graphics.fillRect(0, 0, width - 1, height - 1)

    }

}