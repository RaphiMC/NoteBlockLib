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

import net.raphimc.noteblocklib.format.SongFormat;

import javax.swing.filechooser.FileFilter;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class NoteBlockFileFilter extends FileFilter {

    private final List<String> extensions;
    private final String description;

    public NoteBlockFileFilter() {
        this.extensions = Arrays.stream(SongFormat.values()).flatMap(format -> format.getExtensions().stream()).collect(Collectors.toList());
        this.description = "NoteBlockLib Song Files (" + this.extensions.stream().map(s -> "*." + s).collect(Collectors.joining(", ")) + ")";
    }

    public NoteBlockFileFilter(final SongFormat songFormat) {
        this.extensions = songFormat.getExtensions();
        this.description = songFormat.getName().toUpperCase() + " Song File (" + this.extensions.stream().map(s -> "*." + s).collect(Collectors.joining(", ")) + ")";
    }

    @Override
    public boolean accept(File f) {
        if (f.isDirectory()) return true;
        if (!f.isFile()) return false;
        String extension = f.getName().substring(f.getName().lastIndexOf(".") + 1);
        return this.extensions.contains(extension.toLowerCase());
    }

    @Override
    public String getDescription() {
        return this.description;
    }

}
