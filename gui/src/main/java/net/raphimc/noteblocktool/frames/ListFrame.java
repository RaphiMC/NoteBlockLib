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
package net.raphimc.noteblocktool.frames;

import net.lenni0451.commons.swing.GBC;
import net.raphimc.noteblocklib.NoteBlockLib;
import net.raphimc.noteblocklib.format.SongFormat;
import net.raphimc.noteblocklib.format.mcsp.McSpSong;
import net.raphimc.noteblocklib.format.nbs.NbsSong;
import net.raphimc.noteblocklib.model.Song;
import net.raphimc.noteblocklib.model.SongView;
import net.raphimc.noteblocktool.elements.NoteBlockFileFilter;
import net.raphimc.noteblocktool.elements.TextOverlayPanel;
import net.raphimc.noteblocktool.elements.drag.DragTable;
import net.raphimc.noteblocktool.elements.drag.DragTableDropTargetListener;
import net.raphimc.noteblocktool.elements.drag.DragTableModel;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.dnd.DropTarget;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.List;
import java.util.Queue;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public class ListFrame extends JFrame {

    private final List<LoadedSong> loadedSongs = new CopyOnWriteArrayList<>();
    private final DragTable table = new DragTable();
    private final JButton addButton = new JButton("Add");
    private final JButton removeButton = new JButton("Remove");
    private final JButton editButton = new JButton("Edit");
    private final JButton playButton = new JButton("Play");
    private final JButton exportButton = new JButton("Export NBS");
    private DropTarget dropTarget;
    private TextOverlayPanel textOverlayPanel;

    public ListFrame() {
        this.setTitle("NoteBlockTool");
        this.setIconImage(new ImageIcon(this.getClass().getResource("/icon.png")).getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800, 400);
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
        this.dropTarget = new DropTarget(this, new DragTableDropTargetListener(this, this::load));
        this.table.getSelectionModel().addListSelectionListener(e -> this.refreshButtons());
        this.table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DELETE) ListFrame.this.removeButton.doClick();
            }
        });
        this.addContextMenu();

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
        GBC.create(buttonPanel).gridx(3).insets(5, 5, 5, 0).anchor(GBC.LINE_START).add(this.editButton, () -> {
            this.editButton.addActionListener(e -> {
                final int[] rows = this.table.getSelectedRows();
                if (rows.length > 0) {
                    final List<LoadedSong> songs = new ArrayList<>();
                    for (int row : rows) songs.add((LoadedSong) this.table.getValueAt(row, 0));
                    new EditFrame(songs, this.table::refreshRow);
                }
            });
        });
        GBC.create(buttonPanel).gridx(4).insets(5, 5, 5, 0).anchor(GBC.LINE_START).add(this.playButton, () -> {
            this.playButton.addActionListener(e -> {
                final int[] rows = this.table.getSelectedRows();
                if (rows.length == 1) {
                    final LoadedSong song = (LoadedSong) this.table.getValueAt(rows[0], 0);
                    SongPlayerFrame.open(song);
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

    private void addContextMenu() {
        JPopupMenu contextMenu = new JPopupMenu();
        contextMenu.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                int[] selectedRows = ListFrame.this.table.getSelectedRows();
                int hoveredRow = ListFrame.this.table.rowAtPoint(contextMenu.getInvoker().getMousePosition());
                if (hoveredRow >= 0 && Arrays.stream(selectedRows).noneMatch(i -> i == hoveredRow)) {
                    ListFrame.this.table.setRowSelectionInterval(hoveredRow, hoveredRow);
                }
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
            }
        });

        JMenuItem contextMenuRemove = new JMenuItem("Remove");
        contextMenuRemove.addActionListener(e -> this.removeButton.doClick());
        contextMenu.add(contextMenuRemove);
        JMenuItem contextMenuEdit = new JMenuItem("Edit");
        contextMenuEdit.addActionListener(e -> this.editButton.doClick());
        contextMenu.add(contextMenuEdit);
        JMenuItem contextMenuPlay = new JMenuItem("Play");
        contextMenuPlay.addActionListener(e -> this.playButton.doClick());
        contextMenu.add(contextMenuPlay);
        this.table.getSelectionModel().addListSelectionListener(e -> contextMenuPlay.setEnabled(this.table.getSelectedRows().length == 1));
        JMenuItem contextMenuExport = new JMenuItem("Export NBS");
        contextMenuExport.addActionListener(e -> this.exportButton.doClick());
        contextMenu.add(contextMenuExport);
        this.table.setComponentPopupMenu(contextMenu);
    }

    private void refreshButtons() {
        this.removeButton.setEnabled(this.table.getSelectedRows().length > 0);
        this.editButton.setEnabled(this.table.getSelectedRows().length > 0);
        this.playButton.setEnabled(this.table.getSelectedRows().length == 1);
        this.exportButton.setEnabled(this.table.getSelectedRows().length > 0);
    }

    private void load(final File... files) {
        this.runSync(() -> {
            this.setDropTarget(null);
            this.textOverlayPanel = new TextOverlayPanel("Loading Songs...");
            this.setGlassPane(this.textOverlayPanel);
            this.textOverlayPanel.setVisible(true);
        });
        final Map<File, Throwable> failedFiles = new HashMap<>();
        CompletableFuture.runAsync(() -> {
            final Queue<File> queue = new ArrayDeque<>(Arrays.asList(files));
            while (!queue.isEmpty()) {
                final File file = queue.poll();
                if (file.isDirectory()) {
                    final File[] subFiles = file.listFiles();
                    if (subFiles != null) queue.addAll(Arrays.asList(subFiles));
                } else if (file.isFile()) {
                    if (this.loadedSongs.stream().anyMatch(s -> s.getFile().equals(file))) continue;
                    try {
                        final Song<?, ?, ?> song = NoteBlockLib.readSong(file);
                        final LoadedSong loadedSong = new LoadedSong(file, song);
                        this.loadedSongs.add(loadedSong);
                        this.runSync(() -> {
                            this.table.addRow(loadedSong);
                            StringBuilder text = new StringBuilder("Loading Songs (" + this.loadedSongs.size() + ")...\n");
                            for (int i = 0; i < 5; i++) {
                                int index = this.loadedSongs.size() - i - 1;
                                if (index < 0) break;
                                text.append(this.loadedSongs.get(index).getFile().getName()).append("\n");
                            }
                            if (text.toString().endsWith("\n")) text = new StringBuilder(text.substring(0, text.length() - 1));
                            this.textOverlayPanel.setText(text.toString());
                        });
                    } catch (Throwable t) {
                        t.printStackTrace();
                        failedFiles.put(file, t);
                    }
                }
            }
        }).thenAcceptAsync(v -> {
            this.runSync(() -> {
                this.textOverlayPanel.setVisible(false);
                this.setGlassPane(new JPanel());
                this.setDropTarget(this.dropTarget);
            });
            if (!failedFiles.isEmpty()) {
                String message;
                if (failedFiles.size() == 1) {
                    Map.Entry<File, Throwable> entry = failedFiles.entrySet().iterator().next();
                    message = "Failed to load song:\n" + entry.getKey().getAbsolutePath() + "\n" + entry.getValue().getMessage();
                } else {
                    message = "Failed to load " + failedFiles.size() + " songs";
                }
                JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void runSync(final Runnable task) {
        if (SwingUtilities.isEventDispatchThread()) {
            task.run();
        } else {
            try {
                SwingUtilities.invokeAndWait(task);
            } catch (Throwable t) {
                throw new RuntimeException("Failed to run task", t);
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
            return this.getLength(this.song.getView());
        }

        public String getLength(final SongView<?> view) {
            int length = (int) Math.ceil(view.getLength() / view.getSpeed());
            return String.format("%02d:%02d:%02d", length / 3600, (length / 60) % 60, length % 60);
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
            return this.getNoteCount(this.song.getView());
        }

        public int getNoteCount(final SongView<?> view) {
            return (int) view.getNotes().values().stream().mapToLong(List::size).sum();
        }

        @Override
        public String toString() {
            return this.file.getAbsolutePath();
        }
    }

}
