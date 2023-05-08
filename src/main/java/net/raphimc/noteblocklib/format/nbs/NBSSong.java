package net.raphimc.noteblocklib.format.nbs;

import net.raphimc.noteblocklib.format.nbs.data.NBSData;
import net.raphimc.noteblocklib.format.nbs.data.layer.NBSLayer;
import net.raphimc.noteblocklib.format.nbs.header.NBSv0Header;
import net.raphimc.noteblocklib.format.nbs.note.NBSNote;
import net.raphimc.noteblocklib.model.Song;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class NBSSong extends Song<NBSv0Header, NBSData, NBSNote> {

    private final String title;
    private final int length;

    public NBSSong(final File sourceFile, final NBSv0Header header, final NBSData data) {
        super(sourceFile, header, data);

        this.title = header.getTitle().isEmpty() ? this.getSourceFile() == null ? "NBS Song" : this.getSourceFile().getName() : header.getTitle();
        this.length = data.getLayers().values().stream()
                .map(l -> l.getNotesAtTick().keySet())
                .flatMapToInt(ints -> ints.stream().mapToInt(i -> i))
                .max().orElse(0);
    }

    /**
     * @return The title of the song
     */
    @Override
    public String getTitle() {
        return this.title;
    }

    /**
     * @return The length of the song, measured in ticks.
     */
    @Override
    public int getLength() {
        return this.length;
    }

    /**
     * @return The tempo of the song, measured in ticks per second.
     */
    @Override
    public float getSpeed() {
        return this.getHeader().getSpeed() / 100F;
    }

    @Override
    public List<NBSNote> getNotesAtTick(final int tick) {
        return this.getData().getLayers().values().stream()
                .map(NBSLayer::getNotesAtTick)
                .filter(m -> m.containsKey(tick))
                .map(m -> m.get(tick))
                .collect(Collectors.toList());
    }

    @Override
    public Map<Integer, List<NBSNote>> getNotes() {
        final Map<Integer, List<NBSNote>> notes = new HashMap<>();
        for (Map.Entry<Integer, NBSLayer> layer : this.getData().getLayers().entrySet()) {
            for (Map.Entry<Integer, NBSNote> note : layer.getValue().getNotesAtTick().entrySet()) {
                notes.computeIfAbsent(note.getKey(), k -> new ArrayList<>()).add(note.getValue());
            }
        }
        return notes;
    }

}
