package net.raphimc.noteblocklib.format.midi;

import net.raphimc.noteblocklib.format.midi.data.MidiData;
import net.raphimc.noteblocklib.format.midi.header.MidiHeader;
import net.raphimc.noteblocklib.format.midi.note.MidiNote;
import net.raphimc.noteblocklib.model.Song;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static net.raphimc.noteblocklib.format.midi.MidiDefinitions.SONG_TICKS_PER_SECOND;

public class MidiSong extends Song<MidiHeader, MidiData, MidiNote> {

    private final String title;
    private final int length;

    public MidiSong(final File sourceFile, final MidiHeader header, final MidiData data) {
        super(sourceFile, header, data);

        final String midiTitle = (String) header.getMidiFileFormat().getProperty("title");
        this.title = midiTitle == null || midiTitle.isEmpty() ? this.getSourceFile() == null ? "MIDI Song" : this.getSourceFile().getName() : midiTitle;
        this.length = data.getNotes().keySet().stream().mapToInt(i -> i).max().orElse(0);
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public int getLength() {
        return this.length;
    }

    @Override
    public float getSpeed() {
        return SONG_TICKS_PER_SECOND;
    }

    @Override
    public List<MidiNote> getNotesAtTick(final int tick) {
        return this.getData().getNotes().getOrDefault(tick, new ArrayList<>());
    }

    @Override
    public Map<Integer, List<MidiNote>> getNotes() {
        return this.getData().getNotes();
    }

}
