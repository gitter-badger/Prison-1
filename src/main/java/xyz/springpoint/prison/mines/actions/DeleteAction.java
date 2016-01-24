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

package xyz.springpoint.prison.mines.actions;

import org.bukkit.command.CommandSender;
import xyz.springpoint.prison.Action;
import xyz.springpoint.prison.mines.Mine;
import xyz.springpoint.prison.mines.Mines;

/**
 * @author SirFaizdat
 */
public class DeleteAction implements Action {

    // =======================
    //  Fields
    // =======================

    private Mines mines;

    // =======================
    //  Constructor
    // =======================

    public DeleteAction(Mines mines) {
        this.mines = mines;
    }

    // =======================
    //  Override
    // =======================

    @Override
    public void run(Object actor, CommandSender sender, String... args) {
        // Get the mine (after making sure it exists of course)
        Mine m = mines.getMine((String) actor);
        if (m == null) {
            mines.getMessages().send(sender, "mine_does_not_exist", (String) actor);
            return;
        }
        mines.delete(m);
        mines.getMessages().send(sender, "mine_deleted", m.name);
    }

    @Override
    public String description() {
        return "Delete a mine and its save file.";
    }

}
