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
package net.raphimc.noteblocklib.format;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public enum SongFormat {

    NBS("nbs"), // Minecraft Note Block Studio (https://opennbs.org/)
    MCSP("mcsp2", "mcsp"), // Minecraft Song Planner (Ancestor of Minecraft Note Block Studio) (https://web.archive.org/web/20110923175931/http://www.minecraftforum.net/topic/136749-minecraft-song-planner-25-a-tool-for-note-block-musicians/)
    TXT("txt"), // BleachHack NoteBot (https://github.com/BleachDev/BleachHack)
    FUTURE("notebot"), // Future client NoteBot (https://futureclient.net/)
    MIDI("mid", "midi"), // Standard MIDI (https://en.wikipedia.org/wiki/MIDI)
    ;

    private final List<String> extensions;

    SongFormat(final String... extension) {
        this.extensions = Arrays.asList(extension);
    }

    public static SongFormat getByExtension(final String extension) {
        for (final SongFormat format : values()) {
            if (format.extensions.contains(extension.toLowerCase(Locale.ROOT))) {
                return format;
            }
        }

        return null;
    }

    public static SongFormat getByName(final String name) {
        for (final SongFormat format : values()) {
            if (format.name().equalsIgnoreCase(name)) {
                return format;
            }
        }

        return null;
    }

    public String getName() {
        return this.name().toLowerCase();
    }

    public List<String> getExtensions() {
        return this.extensions;
    }

}
