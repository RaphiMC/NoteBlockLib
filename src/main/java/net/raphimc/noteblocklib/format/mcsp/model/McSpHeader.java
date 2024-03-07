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

import net.raphimc.noteblocklib.model.Header;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class McSpHeader implements Header {

    private int version;
    private String title = "";
    private String author = "";
    private String originalAuthor = "";
    private int speed = 10;
    private int autoSaveInterval;
    private int leftClicks;
    private int rightClicks;
    private int noteBlocksAdded;
    private int noteBlocksRemoved;
    private int minutesSpent;

    public McSpHeader(final Scanner scanner) {
        scanner.useDelimiter("\\|");
        this.version = scanner.nextInt();
        if (this.version != 0 && this.version != 2) {
            throw new IllegalStateException("Unsupported MCSP version: " + this.version);
        }
        if (this.version == 2) {
            this.autoSaveInterval = scanner.nextInt();
            this.title = scanner.next();
            this.author = scanner.next();
            this.originalAuthor = scanner.next();
            if (!scanner.next().isEmpty()) {
                throw new IllegalStateException("Invalid MCSP header");
            }
            try { // Optional metadata
                scanner.nextLine();
                this.speed = scanner.nextInt();
                this.leftClicks = scanner.nextInt();
                this.rightClicks = scanner.nextInt();
                this.noteBlocksAdded = scanner.nextInt();
                this.noteBlocksRemoved = scanner.nextInt();
                this.minutesSpent = scanner.nextInt();
            } catch (NoSuchElementException ignored) {
            }
        }
    }

    public McSpHeader(final int version) {
        this.version = version;
    }

    public McSpHeader(final int version, final int autoSaveInterval, final String title, final String author, final String originalAuthor, final int speed, final int leftClicks, final int rightClicks, final int noteBlocksAdded, final int noteBlocksRemoved, final int minutesSpent) {
        this.version = version;
        this.autoSaveInterval = autoSaveInterval;
        this.title = title;
        this.author = author;
        this.originalAuthor = originalAuthor;
        this.speed = speed;
        this.leftClicks = leftClicks;
        this.rightClicks = rightClicks;
        this.noteBlocksAdded = noteBlocksAdded;
        this.noteBlocksRemoved = noteBlocksRemoved;
        this.minutesSpent = minutesSpent;
    }

    public McSpHeader() {
    }

    /**
     * @return The version of the MCSP format.
     */
    public int getVersion() {
        return this.version;
    }

    /**
     * @param version The version of the MCSP format.
     */
    public void setVersion(final int version) {
        this.version = version;
    }

    /**
     * @return The name of the song.
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * @param title The name of the song.
     */
    public void setTitle(final String title) {
        this.title = title;
    }

    /**
     * @return The author of the song.
     */
    public String getAuthor() {
        return this.author;
    }

    /**
     * @param author The author of the song.
     */
    public void setAuthor(final String author) {
        this.author = author;
    }

    /**
     * @return The original author of the song.
     */
    public String getOriginalAuthor() {
        return this.originalAuthor;
    }

    /**
     * @param originalAuthor The original author of the song.
     */
    public void setOriginalAuthor(final String originalAuthor) {
        this.originalAuthor = originalAuthor;
    }

    /**
     * @return The tempo of the song. Measured in ticks per second.
     */
    public int getSpeed() {
        return this.speed;
    }

    /**
     * @param speed The tempo of the song. Measured in ticks per second.
     */
    public void setSpeed(final int speed) {
        this.speed = speed;
    }

    /**
     * @return The amount of minutes between each auto-save (0 indicates that auto-save is disabled) (0-60).
     */
    public int getAutoSaveInterval() {
        return this.autoSaveInterval;
    }

    /**
     * @param autoSaveInterval The amount of minutes between each auto-save (0 indicates that auto-save is disabled) (0-60).
     */
    public void setAutoSaveInterval(final int autoSaveInterval) {
        this.autoSaveInterval = autoSaveInterval;
    }

    /**
     * @return Amount of times the user has left-clicked.
     */
    public int getLeftClicks() {
        return this.leftClicks;
    }

    /**
     * @param leftClicks Amount of times the user has left-clicked.
     */
    public void setLeftClicks(final int leftClicks) {
        this.leftClicks = leftClicks;
    }

    /**
     * @return Amount of times the user has right-clicked.
     */
    public int getRightClicks() {
        return this.rightClicks;
    }

    /**
     * @param rightClicks Amount of times the user has right-clicked.
     */
    public void setRightClicks(final int rightClicks) {
        this.rightClicks = rightClicks;
    }

    /**
     * @return Amount of times the user has added a note block.
     */
    public int getNoteBlocksAdded() {
        return this.noteBlocksAdded;
    }

    /**
     * @param noteBlocksAdded Amount of times the user has added a note block.
     */
    public void setNoteBlocksAdded(final int noteBlocksAdded) {
        this.noteBlocksAdded = noteBlocksAdded;
    }

    /**
     * @return Amount of times the user has removed a note block.
     */
    public int getNoteBlocksRemoved() {
        return this.noteBlocksRemoved;
    }

    /**
     * @param noteBlocksRemoved Amount of times the user has removed a note block.
     */
    public void setNoteBlocksRemoved(final int noteBlocksRemoved) {
        this.noteBlocksRemoved = noteBlocksRemoved;
    }

    /**
     * @return Amount of minutes spent on the project.
     */
    public int getMinutesSpent() {
        return this.minutesSpent;
    }

    /**
     * @param minutesSpent Amount of minutes spent on the project.
     */
    public void setMinutesSpent(final int minutesSpent) {
        this.minutesSpent = minutesSpent;
    }

}
