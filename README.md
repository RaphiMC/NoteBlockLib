# NoteBlockLib
Library for reading, writing, manipulating and playing Minecraft note block songs.

To use NoteBlockLib in your application, check out the [Usage](#usage) section.  
For a reference implementation of NoteBlockLib, check out [NoteBlockTool](https://github.com/RaphiMC/NoteBlockTool).

## Features
- Reads .nbs, .mcsp2, .mid, .txt and .notebot files
- Can convert all of the above to .nbs
- Offers an easy way to play note block songs in your application
- Good MIDI importer
  - Supports most MIDI files
  - Supports velocity and panning
  - Can handle Black MIDI files
- Supports all NBS versions
  - Version 0 - 5
  - Supports undocumented features like Tempo Changers
- Many tools for manipulating songs
  - Optimize songs for use in Minecraft (Transposing, Resampling)
  - Resampling songs with a different TPS
  - Instrument replacement
  - Note deduplication (Useful for Black MIDI files)
  - Removal of quiet notes (Useful for Black MIDI files)
- Very fast and efficient

## Releases
### Gradle/Maven
To use NoteBlockLib with Gradle/Maven you can get it from [Maven Central](https://mvnrepository.com/artifact/net.raphimc/NoteBlockLib), [Lenni0451's Maven](https://maven.lenni0451.net/#/releases/net/raphimc/NoteBlockLib) or [Jitpack](https://jitpack.io/#RaphiMC/NoteBlockLib).
You can also find instructions how to implement it into your build script there.

### Jar File
If you just want the latest jar file you can download it from [GitHub Actions](https://github.com/RaphiMC/NoteBlockLib/actions/workflows/build.yml) or [Lenni0451's Jenkins](https://build.lenni0451.net/job/NoteBlockLib/).

## Usage
### Concepts and terminology
The main class of NoteBlockLib is the ``NoteBlockLib`` class. It contains all the methods for reading, writing and creating songs.  
The utils for manipulating songs are located in the ``util`` package.

#### Song
Song is a wrapper class around the Header, Data and the View of a song.  
The Header and Data classes are the low level representation of a song. They are used by I/O operations.  
The View class is a high level representation of a song and is generated from the Header and Data classes.

#### Header
The header usually contains the metadata of a song. This includes the author, the original author, the description, the tempo, the delay and the length of the song.

#### Data
The data contains all the notes of a song. This includes the tick at which the note should be played, the instrument and the key.

#### SongView
The SongView is a high level and generalized representation of a song. It contains only the most important information of a song.  
The view is used for most operations like playing a song or manipulating it. Due to the fact that the view is a high level representation of a song, it is not suitable for I/O operations directly.  
To create a low level representation (Song) from the view again you can use the ``NoteBlockLib.createSong(view, format)`` method.
The returned song only has the bare minimum of data required to be written to a file. You can use the setter methods of the Header and Data class to add more data to the song.  
The view is generated by default only once when the Song class is created. If you want to refresh the view you can use the ``Song.refreshView()`` method.

#### Note
The Note class is a wrapper class around the instrument and key of a note. Each format has its own Note class which can have additional data like volume or panning.  
One way of accessing that data is through the use of the ``NoteWithVolume`` and ``NoteWithPanning`` classes.

### Reading a song
To read a song you can use the ``NoteBlockLib.readSong(<input>, [format])`` method.
The input can be a File, InputStream or a byte array.
The format is optional and can be used to specify the format of the input. If the format is not specified, NoteBlockLib will try to guess the format based on the file extension.

### Writing a song
To write a song you can use the ``NoteBlockLib.writeSong(<song>, <output>)`` method.

### Creating a song
The easiest way to create a song is to create a SongView and then use the ``NoteBlockLib.createSongFromView(<view>, [format])`` method to create a Song from it.  
Alternatively you can create a Song directly by using the ``new Song(null, <header>, <data>)`` constructor. This requires you to create the Header and Data yourself.

### Playing a song
To play a song you can use the ``SongPlayer`` class. The SongPlayer provides basic controls like play, pause, stop and seek.  
To instantiate it you can use the ``new SongPlayer(<songView>, <callback>)`` constructor.  
The callback contains basic methods like ``onFinished`` and ``playNote`` to handle the playback of the song.

### Manipulating a song
There are multiple utils for manipulating a song.

#### SongResampler
The SongResampler can be used to resample a song to a different TPS without changing the musical speed or length of the song.  
This is very useful if you want to export the song as a schematic and play it in Minecraft as that is limited to 10 TPS.

#### MinecraftDefinitions
The MinecraftDefinitions class contains definitions and formulas for Minecraft related manipulations.  
This includes multiple methods for getting notes within the Minecraft octave range, converting between Minecraft and NBS id systems and more.

#### SongUtil
This class has some general utils for manipulating songs like applying a modification to all notes of a song.

## Examples
**Reading a song, transposing its notes and writing it back**
```java
Song<?, ?, ?> song = NoteBlockLib.readSong(new File("input.nbs"));

// Clamp the note key
// SongUtil.applyToAllNotes(song.getView(), MinecraftDefinitions::clampNoteKey);

// Transpose the note key
//SongUtil.applyToAllNotes(song.getView(), MinecraftDefinitions::transposeNoteKey);

// Shift the instrument of out of range notes to a higher/lower one. Sounds better than all above.
SongUtil.applyToAllNotes(song.getView(), MinecraftDefinitions::instrumentShiftNote);
// Clamp the remaining out of range notes
SongUtil.applyToAllNotes(song.getView(), MinecraftDefinitions::clampNoteKey);
        
NoteBlockLib.writeSong(song, new File("output.nbs"));
```
**Reading a MIDI, and writing it as NBS**
```java
Song<?, ?, ?> midiSong = NoteBlockLib.readSong(new File("input.mid"));
Song<?, ?, ?> nbsSong = NoteBlockLib.createSongFromView(midiSong.getView(), SongFormat.NBS);
NoteBlockLib.writeSong(nbsSong, new File("output.nbs"));
```
**Reading a song, changing its sample rate to 10 TPS and writing it back**
```java
Song<?, ?, ?> song = NoteBlockLib.readSong(new File("input.nbs"));

SongResampler.changeTickSpeed(song.getView(), 10F);

Song<?, ?, ?> newSong = NoteBlockLib.createSongFromView(song.getView(), SongFormat.NBS);
// For songs with custom instruments make sure to copy them over to the new song
// ((NbsSong)newSong).getData().setCustomInstruments(((NbsSong)song).getData().getCustomInstruments());

NoteBlockLib.writeSong(newSong, new File("output.nbs"));
```
**Creating a new song and saving it as NBS**
```java
// tick -> list of notes
Map<Integer, List<Note>> notes = new TreeMap<>();
// Add the notes to the song
notes.put(0, Lists.newArrayList(new NbsNote(Instrument.HARP.nbsId(), (byte) 46)));
notes.put(5, Lists.newArrayList(new NbsNote(Instrument.BASS.nbsId(), (byte) 60)));
notes.put(8, Lists.newArrayList(new NbsNote(Instrument.BIT.nbsId(), (byte) 84)));
SongView<Note> mySong = new SongView<>("My song" /*title*/, 10F /*ticks per second*/, notes);

Song<?, ?, ?> nbsSong = NoteBlockLib.createSongFromView(mySong, SongFormat.NBS);

NoteBlockLib.writeSong(nbsSong, new File("C:\\Users\\Koppe\\Desktop\\output.nbs"));
```
**Playing a song**
```java
Song<?, ?, ?> song = NoteBlockLib.readSong(new File("input.nbs"));

// Optionally apply a modification to all notes here (For example to transpose the note keys)

SongPlayer player = new SongPlayer(song.getView(), new ISongPlayerCallback() {
    @Override
    public void playNote(Note note) {
        // This method gets called in real time when the song is played.
        // NBS Notes have a fine pitch besides the normal key. To calculate the key which factors that in use the NbsDefinitions class.
        System.out.println(note.getInstrument() + " " + note.getKey());
    }

    // There are other methods like onFinished which can be overridden.
});

// Start playing
player.play();

// Pause
player.setPaused(true);

// Resume
player.setPaused(false);

// Seek
player.setTick(50);

// Stop
player.stop();
```

## Contact
If you encounter any issues, please report them on the
[issue tracker](https://github.com/RaphiMC/NoteBlockLib/issues).  
If you just want to talk or need help implementing NoteBlockLib feel free to join my
[Discord](https://discord.gg/dCzT9XHEWu).
