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
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.*;
import java.io.File;
import java.util.List;
import java.util.function.Consumer;

public class DragTableDropTargetListener implements DropTargetListener {

    private final JFrame frame;
    private final Consumer<File[]> fileConsumer;
    private TextOverlayPanel textOverlayPanel;

    public DragTableDropTargetListener(final JFrame frame, final Consumer<File[]> fileConsumer) {
        this.frame = frame;
        this.fileConsumer = fileConsumer;
    }

    private boolean isSupported(final Transferable transferable) {
        return transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
    }

    private void showOverlay() {
        this.textOverlayPanel = new TextOverlayPanel("Drop files here to add them");
        this.frame.setGlassPane(this.textOverlayPanel);
        this.textOverlayPanel.setVisible(true);
    }

    private void hideOverlay() {
        if (this.textOverlayPanel != null) {
            this.textOverlayPanel.setVisible(false);
            this.frame.setGlassPane(new JPanel());
        }
    }

    @Override
    public void dragEnter(DropTargetDragEvent event) {
        if (this.isSupported(event.getTransferable())) {
            event.acceptDrag(DnDConstants.ACTION_COPY);
            this.showOverlay();
        } else {
            event.rejectDrag();
        }
    }

    @Override
    public void dragOver(DropTargetDragEvent event) {
        if (this.isSupported(event.getTransferable())) {
            event.acceptDrag(DnDConstants.ACTION_COPY);
        } else {
            event.rejectDrag();
        }
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent event) {
        if (this.isSupported(event.getTransferable())) {
            event.acceptDrag(DnDConstants.ACTION_COPY);
        } else {
            event.rejectDrag();
        }
    }

    @Override
    public void dragExit(DropTargetEvent event) {
        this.hideOverlay();
    }

    @Override
    public void drop(DropTargetDropEvent event) {
        this.hideOverlay();
        if (!this.isSupported(event.getTransferable())) {
            event.rejectDrop();
            return;
        }

        event.acceptDrop(DnDConstants.ACTION_COPY);
        try {
            final List<File> files = (List<File>) event.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
            this.fileConsumer.accept(files.toArray(new File[0]));
        } catch (Exception e) {
            e.printStackTrace();
        }
        event.dropComplete(true);
    }

}
