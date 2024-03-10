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
package net.raphimc.noteblocktool.elements.formatter;

import javax.swing.*;
import java.text.DecimalFormat;
import java.text.ParseException;

public class DoubleFormatterFactory extends JFormattedTextField.AbstractFormatterFactory {

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.##");

    static {
        DECIMAL_FORMAT.setGroupingUsed(false);
    }


    private final String prefix;

    public DoubleFormatterFactory(final String prefix) {
        this.prefix = prefix;
    }

    @Override
    public JFormattedTextField.AbstractFormatter getFormatter(JFormattedTextField tf) {
        return new JFormattedTextField.AbstractFormatter() {
            @Override
            public Object stringToValue(String text) throws ParseException {
                try {
                    text = text.replace(prefix, "");
                    return Double.parseDouble(text);
                } catch (Throwable t) {
                    throw new ParseException("Invalid number", 0);
                }
            }

            @Override
            public String valueToString(Object value) throws ParseException {
                return DECIMAL_FORMAT.format(value) + prefix;
            }
        };
    }

}
