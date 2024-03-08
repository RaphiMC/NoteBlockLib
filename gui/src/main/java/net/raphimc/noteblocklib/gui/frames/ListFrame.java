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
package net.raphimc.noteblocklib.gui.frames;

import net.lenni0451.commons.swing.GBC;
import net.raphimc.noteblocklib.NoteBlockLib;
import net.raphimc.noteblocklib.format.SongFormat;
import net.raphimc.noteblocklib.format.mcsp.McSpSong;
import net.raphimc.noteblocklib.format.nbs.NbsSong;
import net.raphimc.noteblocklib.gui.elements.DragTable;
import net.raphimc.noteblocklib.gui.elements.DragTableDropTargetListener;
import net.raphimc.noteblocklib.gui.elements.DragTableModel;
import net.raphimc.noteblocklib.gui.elements.NoteBlockFileFilter;
import net.raphimc.noteblocklib.model.Song;

import javax.swing.*;
import java.awt.*;
import java.awt.dnd.DropTarget;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ListFrame extends JFrame {

    private final List<LoadedSong> loadedSongs = new ArrayList<>();
    private final DragTable table = new DragTable();
    private final JButton addButton = new JButton("Add");
    private final JButton removeButton = new JButton("Remove");
    private final JButton editButton = new JButton("Edit");
    private final JButton playButton = new JButton("Play");
    private final JButton exportButton = new JButton("Export NBS");
    private SongPlayerFrame songPlayerFrame;

    public ListFrame() {
        this.setTitle("NoteBlockLib GUI");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 400);
        this.setLocationRelativeTo(null);

        this.initComponents();
        this.refreshButtons();

        this.setMinimumSize(this.getSize());
        this.setVisible(true);
    }

    private void initComponents() {
        final JPanel root = new JPanel();
        root.setLayout(new BorderLayout());
        this.setContentPane(root);

        root.add(new JScrollPane(this.table), BorderLayout.CENTER);
        new DropTarget(this, new DragTableDropTargetListener(this, this::load));
        this.table.getSelectionModel().addListSelectionListener(e -> this.refreshButtons());
        this.table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) ListFrame.this.removeButton.doClick();
            }
        });

        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GBC.create(buttonPanel).gridx(0).insets(5, 5, 5, 0).anchor(GBC.LINE_START).add(this.addButton, () -> {
            this.addButton.addActionListener(e -> {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Add Songs");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                fileChooser.setMultiSelectionEnabled(true);
                fileChooser.setFileFilter(new NoteBlockFileFilter());
                for (SongFormat songFormat : SongFormat.values()) fileChooser.addChoosableFileFilter(new NoteBlockFileFilter(songFormat));
                if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    this.load(fileChooser.getSelectedFiles());
                }
            });
        });
        GBC.create(buttonPanel).gridx(1).insets(5, 5, 5, 0).anchor(GBC.LINE_START).add(this.removeButton, () -> {
            this.removeButton.addActionListener(e -> {
                final int[] selectedRows = this.table.getSelectedRows();
                for (int i = selectedRows.length - 1; i >= 0; i--) {
                    final LoadedSong song = (LoadedSong) this.table.getValueAt(selectedRows[i], 0);
                    this.loadedSongs.remove(song);
                    ((DragTableModel) this.table.getModel()).removeRow(selectedRows[i]);
                }
            });
        });
        GBC.create(buttonPanel).gridx(2).weightx(1).fill(GBC.HORIZONTAL).add(Box.createVerticalGlue());
        GBC.create(buttonPanel).gridx(3).insets(5, 5, 5, 0).anchor(GBC.LINE_START).add(this.editButton);
        GBC.create(buttonPanel).gridx(4).insets(5, 5, 5, 0).anchor(GBC.LINE_START).add(this.playButton, () -> {
            this.playButton.addActionListener(e -> {
                final int[] rows = this.table.getSelectedRows();
                if (rows.length == 1) {
                    final LoadedSong song = (LoadedSong) this.table.getValueAt(rows[0], 0);
                    if (this.songPlayerFrame != null) this.songPlayerFrame.dispose();
                    this.songPlayerFrame = new SongPlayerFrame(song);
                }
            });
        });
        GBC.create(buttonPanel).gridx(5).insets(5, 5, 5, 5).anchor(GBC.LINE_START).add(this.exportButton, () -> {
            this.exportButton.addActionListener(e -> {
                final int[] rows = this.table.getSelectedRows();
                if (rows.length == 1) {
                    final LoadedSong song = (LoadedSong) this.table.getValueAt(rows[0], 0);
                    final JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Export Song");
                    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    fileChooser.setMultiSelectionEnabled(false);
                    fileChooser.setAcceptAllFileFilterUsed(false);
                    fileChooser.setFileFilter(new NoteBlockFileFilter(SongFormat.NBS));
                    if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        if (!file.getName().toLowerCase().endsWith(".nbs")) file = new File(file.getParentFile(), file.getName() + ".nbs");
                        try {
                            NoteBlockLib.writeSong(song.getSong(), file);
                        } catch (Throwable t) {
                            t.printStackTrace();
                            JOptionPane.showMessageDialog(this, "Failed to export song:\n" + song.getFile().getAbsolutePath() + "\n" + t.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else if (rows.length > 1) {
                    final JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setDialogTitle("Export Songs");
                    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                    fileChooser.setMultiSelectionEnabled(false);
                    if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                        final File directory = fileChooser.getSelectedFile();
                        for (int row : rows) {
                            final LoadedSong song = (LoadedSong) this.table.getValueAt(row, 0);
                            final File file = new File(directory, song.getFile().getName().substring(0, song.getFile().getName().lastIndexOf('.')) + ".nbs");
                            try {
                                NoteBlockLib.writeSong(song.getSong(), file);
                            } catch (Throwable t) {
                                t.printStackTrace();
                                JOptionPane.showMessageDialog(this, "Failed to export song:\n" + song.getFile().getAbsolutePath() + "\n" + t.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
            });
        });
        root.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void refreshButtons() {
        this.removeButton.setEnabled(this.table.getSelectedRows().length > 0);
        this.editButton.setEnabled(this.table.getSelectedRows().length > 0);
        this.playButton.setEnabled(this.table.getSelectedRows().length == 1);
        this.exportButton.setEnabled(this.table.getSelectedRows().length > 0);
    }

    private void load(final File... files) {
        for (File file : files) {
            if (file.isDirectory()) {
                final File[] subFiles = file.listFiles();
                if (subFiles != null) this.load(subFiles);
            } else if (file.isFile()) {
                if (this.loadedSongs.stream().anyMatch(s -> s.getFile().equals(file))) continue;
                try {
                    final Song<?, ?, ?> song = NoteBlockLib.readSong(file);
                    final LoadedSong loadedSong = new LoadedSong(file, song);
                    this.loadedSongs.add(loadedSong);
                    this.table.addRow(loadedSong);
                } catch (Throwable t) {
                    t.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Failed to load song:\n" + file.getAbsolutePath() + "\n" + t.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }


    public static class LoadedSong {
        private final File file;
        private final Song<?, ?, ?> song;

        public LoadedSong(final File file, final Song<?, ?, ?> song) {
            this.file = file;
            this.song = song;
        }

        public File getFile() {
            return this.file;
        }

        public Song<?, ?, ?> getSong() {
            return this.song;
        }

        public String getLength() {
            int msLength = (int) (this.song.getView().getLength() / this.song.getView().getSpeed() * 1000);
            return String.format("%02d:%02d:%02d", msLength / 3600000, (msLength / 60000) % 60, (msLength / 1000) % 60);
        }

        public Optional<String> getAuthor() {
            String author = "";
            if (this.song instanceof NbsSong) {
                final NbsSong nbsSong = (NbsSong) this.song;
                author = nbsSong.getHeader().getAuthor();
            } else if (this.song instanceof McSpSong) {
                final McSpSong mcSpSong = (McSpSong) this.song;
                author = mcSpSong.getHeader().getAuthor();
            }
            if (author.isEmpty()) return Optional.empty();
            else return Optional.of(author);
        }

        public Optional<String> getOriginalAuthor() {
            String originalAuthor = "";
            if (this.song instanceof NbsSong) {
                final NbsSong nbsSong = (NbsSong) this.song;
                originalAuthor = nbsSong.getHeader().getOriginalAuthor();
            } else if (this.song instanceof McSpSong) {
                final McSpSong mcSpSong = (McSpSong) this.song;
                originalAuthor = mcSpSong.getHeader().getOriginalAuthor();
            }
            if (originalAuthor.isEmpty()) return Optional.empty();
            else return Optional.of(originalAuthor);
        }

        public Optional<String> getDescription() {
            String description = "";
            if (this.song instanceof NbsSong) {
                final NbsSong nbsSong = (NbsSong) this.song;
                description = nbsSong.getHeader().getDescription();
            }
            if (description.isEmpty()) return Optional.empty();
            else return Optional.of(description);
        }

        public int getNoteCount() {
            return (int) this.song.getView().getNotes().values().stream().mapToLong(List::size).sum();
        }

        @Override
        public String toString() {
            return this.file.getAbsolutePath();
        }
    }

}
