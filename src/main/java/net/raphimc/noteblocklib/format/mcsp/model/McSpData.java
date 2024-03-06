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
package net.raphimc.noteblocklib.format.mcsp.model;

import net.raphimc.noteblocklib.model.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class McSpData implements Data<McSpNote> {

    private List<McSpLayer> layers;

    private static final Pattern NOTE_DATA_PATTERN = Pattern.compile("(\\d+)?>(.)");

    public McSpData(final Scanner scanner) {
        this.layers = new ArrayList<>();
        scanner.useDelimiter("[|\\n]");
        for (int i = 0; i < 6; i++) {
            scanner.next(); // skip header
        }

        int tick = 0;
        while (scanner.hasNext()) {
            tick += scanner.nextInt();
            final Matcher noteData = NOTE_DATA_PATTERN.matcher(scanner.next());

            int layer = 0;
            while (noteData.find()) {
                if (noteData.groupCount() == 2) {
                    layer += Integer.parseInt(noteData.group(1));
                    while (this.layers.size() <= layer) {
                        this.layers.add(new McSpLayer());
                    }
                    this.layers.get(layer).getNotesAtTick().put(tick, new McSpNote(noteData.group(2).charAt(0)));
                } else if (noteData.groupCount() == 1) {
                    if (this.layers.isEmpty()) {
                        this.layers.add(new McSpLayer());
                    }
                    this.layers.get(layer).getNotesAtTick().put(tick, new McSpNote(noteData.group(1).charAt(0)));
                } else if (noteData.groupCount() != 0) {
                    throw new IllegalArgumentException("Invalid note data: " + noteData.group(0));
                }
            }
        }
    }

    public McSpData(final List<McSpLayer> layers) {
        this.layers = layers;
    }

    /**
     * @return The layers of this song
     */
    public List<McSpLayer> getLayers() {
        return this.layers;
    }

    /**
     * @param layers The layers of this song
     */
    public void setLayers(final List<McSpLayer> layers) {
        this.layers = layers;
    }

}
