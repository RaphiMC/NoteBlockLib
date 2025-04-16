/*
 * This file is part of NoteBlockLib - https://github.com/RaphiMC/NoteBlockLib
 * Copyright (C) 2022-2025 RK_01/RaphiMC and contributors
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
package net.raphimc.noteblocklib.util;

public class TimerHack {

    /**
     * Set this to false (Before using the NoteBlockLib API) to disable the timer hack (Useful if you have your own way of enabling high resolution timers)
     */
    public static boolean ENABLED = true;
    private static Thread THREAD;

    /**
     * Starts a thread which indefinitely sleeps to force the JVM to enable high resolution timers on Windows.
     */
    public static synchronized void ensureRunning() {
        if (ENABLED && THREAD == null) {
            THREAD = new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(Integer.MAX_VALUE);
                    } catch (InterruptedException ignored) {
                    }
                }
            }, "NoteBlockLib-TimerHack");
            THREAD.setDaemon(true);
            THREAD.start();
        }
    }

}
