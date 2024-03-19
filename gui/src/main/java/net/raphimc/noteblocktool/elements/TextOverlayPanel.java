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
package net.raphimc.noteblocktool.elements;

import net.lenni0451.commons.swing.components.OverlayPanel;

import java.awt.*;

public class TextOverlayPanel extends OverlayPanel {

    private String text;

    public TextOverlayPanel(final String text) {
        super(new Color(50, 50, 50, 150));
        this.text = text;

        this.setOpaque(false);
    }

    @Override
    protected void paintOverlay(Graphics g) {
        g.setColor(Color.WHITE);
        final FontMetrics metrics = g.getFontMetrics();
        final String[] lines = this.text.split("\n");
        int y = ((this.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();
        for (int i = 0; i < lines.length; i++) {
            if (i == 1) g.setColor(Color.GRAY);
            String line = lines[i];
            int x = (this.getWidth() - metrics.stringWidth(line)) / 2;
            g.drawString(line, x, y);
            y += metrics.getHeight();
        }
    }

    public void setText(final String text) {
        this.text = text;
        this.invalidate();
        this.repaint();
    }

}
