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

import org.bukkit.command.CommandSender;

/**
 * @author SirFaizdat
 */
public interface Action {

    /**
     * Runs this action.
     *
     * @param actor  The object this is being performed on. Usually, this would be the String name
     *               of an object.
     * @param sender The CommandSender running this action.
     * @param args   Any additional arguments.
     */
    void run(Object actor, CommandSender sender, String... args);

    /**
     * @return A description of what the action does.
     */
    String description();

}
