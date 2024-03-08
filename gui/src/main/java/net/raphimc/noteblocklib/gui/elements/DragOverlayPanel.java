/*
 * This file is part of NoteBlockLib - https://github.com/RaphiMC/NoteBlockLib
 * Copyright (C) 2022-2024 RK_01/RaphiMC and contributors
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.raphimc.noteblocklib.gui.elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.MouseAdapter;

public class DragOverlayPanel extends JPanel {

    private static final MouseAdapter VOID_MOUSE_ADAPTER = new MouseAdapter() {
    };
    private static final KeyAdapter VOID_KEY_ADAPTER = new KeyAdapter() {
    };
    private static final FocusAdapter STEALING_FOCUS_ADAPTER = new FocusAdapter() {
        @Override
        public void focusLost(FocusEvent e) {
            e.getComponent().requestFocus();
        }
    };


    public DragOverlayPanel() {
        this.setOpaque(false);
    }

    @Override
    public void addNotify() {
        super.addNotify();

        this.addMouseListener(VOID_MOUSE_ADAPTER);
        this.addMouseMotionListener(VOID_MOUSE_ADAPTER);
        this.addMouseWheelListener(VOID_MOUSE_ADAPTER);
        this.addKeyListener(VOID_KEY_ADAPTER);
        this.addFocusListener(STEALING_FOCUS_ADAPTER);
    }

    @Override
    public void removeNotify() {
        super.removeNotify();

        this.removeMouseListener(VOID_MOUSE_ADAPTER);
        this.removeMouseMotionListener(VOID_MOUSE_ADAPTER);
        this.removeMouseWheelListener(VOID_MOUSE_ADAPTER);
        this.removeKeyListener(VOID_KEY_ADAPTER);
        this.removeFocusListener(STEALING_FOCUS_ADAPTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(new Color(50, 50, 50, 150));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        String text = "Drop files here to add them";
        FontMetrics metrics = g.getFontMetrics();
        int x = (this.getWidth() - metrics.stringWidth(text)) / 2;
        int y = ((this.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
        g.setColor(Color.WHITE);
        g.drawString(text, x, y);
        super.paintComponent(g);
    }

}
