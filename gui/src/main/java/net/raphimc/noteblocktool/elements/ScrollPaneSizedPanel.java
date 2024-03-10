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

import javax.swing.*;
import java.awt.*;

public class ScrollPaneSizedPanel extends JPanel {

    private final JScrollPane scrollPane;

    public ScrollPaneSizedPanel(final JScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension preferred = super.getPreferredSize();
        JViewport viewport = this.scrollPane.getViewport();
        preferred.width = Math.min(preferred.width, viewport.getWidth());
//        preferred.height = Math.min(preferred.height, viewport.getHeight());
        return preferred;
    }

}
