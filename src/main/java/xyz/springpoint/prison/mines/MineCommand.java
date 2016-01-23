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

package xyz.springpoint.prison.mines;

import com.sk89q.worldedit.bukkit.selections.Selection;
import ml.springpoint.springcore.command.Command;
import ml.springpoint.springcore.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * @author SirFaizdat
 */
public class MineCommand {

    // =======================
    //  Fields
    // =======================

    private Mines mines;

    // =======================
    //  Constructor
    // =======================

    public MineCommand(Mines mines) {
        this.mines = mines;
    }

    // =======================
    //  Commands
    // =======================

    @Command(name = "mines", permission = "prison.mines", description = "Manage the mines module.", usage = "/mines")
    public void baseCommand(CommandArgs args) {
        args.getSender().sendMessage("Use the command properly!");
    }

    @Command(name = "mines.create", permission = "prison.mines.create", description = "Create a new mine.", usage = "/mines create <name>", minArgs = 1, inGameOnly = true)
    public void createMine(CommandArgs args) {
        Player player = (Player) args.getSender();
        // Get selection
        Selection sel = mines.worldEdit.getSelection(player);
        if (sel == null) {
            mines.getMessages().send(player, "selection_needed");
            return;
        }
        // Get name
        String name = args.getArgs(0);
        if (mines.getMine(name) != null) {
            mines.getMessages().send(player, "mine_exists", name);
            return;
        }
        // Make the mine
        Mine m = new Mine();
        m.name = name;
        m.world = sel.getWorld().getName();
        m.min = sel.getMinimumPoint();
        m.max = sel.getMaximumPoint();
        mines.add(m); // This also saves it for us
        // Done!
        mines.getMessages().send(player, "mine_created", name);
    }

    @Command(name = "mines.reset", permission = "prison.mines.reset", description = "Reset a mine.", usage = "/mines reset <name>", minArgs = 1, inGameOnly = true)
    public void resetMine(CommandArgs args) {
        // Get the mine (after making sure it exists of course)
        String name = args.getArgs(0);
        Mine m = mines.getMine(name);
        if (m == null) {
            mines.getMessages().send(args.getSender(), "mine_does_not_exist", name);
            return;
        }
        mines.reset(m); // Reset and done
        mines.getMessages().send(args.getSender(), "mine_manual_reset", name);
    }

}
