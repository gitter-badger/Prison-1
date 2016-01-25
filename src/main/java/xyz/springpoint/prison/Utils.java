/*
 * Copyright (C) 2016  Springpoint Software
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package xyz.springpoint.prison;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * A useful class to remove duplicated code.
 *
 * @author SirFaizdat
 */
public class Utils {

    // =======================
    //  Static
    // =======================

    public static boolean writeToFile(String text, File file) {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(text);
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
