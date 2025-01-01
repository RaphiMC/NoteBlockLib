# NoteBlockLib
Library for reading, writing, manipulating and playing Minecraft note block songs.

To use NoteBlockLib in your application, check out the [Usage](#usage) section.  
For a reference implementation of NoteBlockLib, check out [NoteBlockTool](https://github.com/RaphiMC/NoteBlockTool).

## Features
- Reads .nbs, .mid, .txt, .mcsp, .mcsp2 and .notebot files
- Can convert all of the above to .nbs
- Offers an easy way to play note block songs in your application
- Good MIDI importer
  - Supports most MIDI files
  - Supports velocity and panning
  - Can handle Black MIDI files
- Supports all NBS versions
  - Version 0 - 5
  - Supports Tempo Changers
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
Some utils for manipulating and getting metrics about songs are located in the ``util`` package.

The ``Song`` class is the main data structure for parsed songs. It contains generalized and format specific data.  
The generalized data only includes the most important data of songs like the format, the title, the description, the author, the notes and the tempo.  
To access format specific data you have to cast the song instance to a specific format (``NbsSong`` for example).  
Most of the time you will only need the generalized data and all the methods for manipulating, playing and converting songs will work with the generalized data.

All data structures in NoteBlockLib are mutable and can be modified at any time. All data structures also have a ``copy`` method to create a deep copy of the data structure.

### Reading a song
To read a song you can use the ``NoteBlockLib.readSong(<input>, [format])`` method.
The input can be a File, InputStream or a byte array.
The format is optional and can be used to specify the format of the input. If the format is not specified, NoteBlockLib will try to guess the format based on the file extension.

### Writing a song
To write a song you can use the ``NoteBlockLib.writeSong(<song>, <output>)`` method.

### Creating a song
The easiest way to create a song is to create a ``new GenericSong()``, fill in the data and then use the ``NoteBlockLib.convertSong(<song>, <format>)`` method to create a format specific song from it.  
Alternatively you can create a format specific Song directly by using for example the ``new NbsSong()`` constructor. This requires you to fill in all the format specific data yourself.

### Playing a song
To play a song you can use the ``SongPlayer`` class. The SongPlayer provides basic controls like play, pause, stop and seek.  
To create a SongPlayer implementation, you have to create a class which extends the ``SongPlayer`` class.
The SongPlayer class requires you to implement the ``playNotes`` method, but also offers several optional methods like ``onFinished``.

### Manipulating a song
There are multiple utils for manipulating a song.

#### SongResampler
The SongResampler can be used to resample a song to a different TPS without changing the musical speed or length of the song.  
This is very useful if you want to export the song as a schematic and play it in Minecraft as that is limited to 10 TPS.

#### MinecraftDefinitions
The MinecraftDefinitions class contains definitions and formulas for Minecraft related manipulations.  
This for example includes multiple methods for getting notes within the Minecraft octave range, such as transposing them.

#### SongUtil
This class has some general utils for getting various metrics about a song.

## Examples
**Reading a song, transposing its notes and writing it back**
```java
Song song = NoteBlockLib.readSong(new File("input.nbs"));

// Clamp the note key
// song.getNotes().forEach(MinecraftDefinitions::clampNoteKey);

// Transpose the note key
// song.getNotes().forEach(MinecraftDefinitions::transposeNoteKey);

// Shift the instrument of out of range notes to a higher/lower one. Sounds better than all above.
song.getNotes().forEach(MinecraftDefinitions::instrumentShiftNote);
// Clamp the remaining out of range notes
song.getNotes().forEach(MinecraftDefinitions::clampNoteKey);

// The operations above work with the generalized song model. If you want to write it back to a specific format, you need to convert it first.
Song convertedSong = NoteBlockLib.convertSong(song, SongFormat.NBS);

NoteBlockLib.writeSong(convertedSong, new File("output.nbs"));
```
**Reading a MIDI, and writing it as NBS**
```java
Song midiSong = NoteBlockLib.readSong(new File("input.mid"));
Song nbsSong = NoteBlockLib.convertSong(midiSong, SongFormat.NBS);
NoteBlockLib.writeSong(nbsSong, new File("output.nbs"));
```
**Reading a song, changing its tempo to 10 TPS and writing it back**
```java
Song song = NoteBlockLib.readSong(new File("input.nbs"));
SongResampler.changeTickSpeed(song, 10F);
// The operations above work with the generalized song model. If you want to write it back to a specific format, you need to convert it first.
Song newSong = NoteBlockLib.convertSong(song, SongFormat.NBS);
NoteBlockLib.writeSong(newSong, new File("output.nbs"));
```
**Creating a new song and saving it as NBS**
```java
Song mySong = new GenericSong();
mySong.setTitle("My song");
mySong.getTempoEvents().setTempo(0, 10F); // set the tempo to 10 ticks per second
mySong.getNotes().add(0, new Note().setInstrument(MinecraftInstrument.HARP).setNbsKey((byte) 46));
mySong.getNotes().add(5, new Note().setInstrument(MinecraftInstrument.BASS).setNbsKey((byte) 60));
mySong.getNotes().add(8, new Note().setInstrument(MinecraftInstrument.BIT).setNbsKey((byte) 84));
Song nbsSong = NoteBlockLib.convertSong(mySong, SongFormat.NBS);
NoteBlockLib.writeSong(nbsSong, new File("output.nbs"));
```
**Playing a song**

Create the custom SongPlayer implementation:
```java
public class MySongPlayer extends SongPlayer {

    public MySongPlayer(Song song) {
        super(song);
    }

    @Override
    protected void playNotes(List<Note> notes) {
        for (Note note : notes) {
            // This method gets called in real time as the song is played.
            // Make sure to check the javadoc of the various methods from the Note class to see how you should use the returned values.
            System.out.println(note.getInstrument() + " " + note.getPitch() + " " + note.getVolume() + " " + note.getPanning());
        }
    }

    // There are other methods like onFinished which can be overridden.
}
```

Start playing the song;
```java
Song song = NoteBlockLib.readSong(new File("input.nbs"));

// Optionally apply a modification to all notes here (For example to transpose the note keys)

// Create a song player
SongPlayer player = new MySongPlayer(song);

// Start playing
player.start();

// Pause
player.setPaused(true);

// Resume
player.setPaused(false);

// Seek to a specific tick
player.setTick(50);

// Seek to a specific time
player.setMillisecondPosition(1000);

// Get the current millisecond position
player.getMillisecondPosition();

// Stop
player.stop();
```

## Contact
If you encounter any issues, please report them on the
[issue tracker](https://github.com/RaphiMC/NoteBlockLib/issues).  
If you just want to talk or need help implementing NoteBlockLib feel free to join my
[Discord](https://discord.gg/dCzT9XHEWu).
