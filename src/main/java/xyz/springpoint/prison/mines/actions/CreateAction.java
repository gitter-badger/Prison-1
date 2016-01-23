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

import com.sk89q.worldedit.bukkit.selections.Selection;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.springpoint.prison.Action;
import xyz.springpoint.prison.mines.Mine;
import xyz.springpoint.prison.mines.Mines;

/**
 * Create a mine.
 *
 * @author SirFaizdat
 */
public class CreateAction implements Action {

    // =======================
    //  Fields
    // =======================

    private Mines mines;

    // =======================
    //  Constructor
    // =======================

    public CreateAction(Mines mines) {
        this.mines = mines;
    }

    // =======================
    //  Override
    // =======================

    @Override
    public void run(Object actor, CommandSender sender, String... args) {
        if (!(sender instanceof Player)) return;
        Player player = (Player) sender;
        // Get selection
        Selection sel = mines.worldEdit.getSelection(player);
        if (sel == null || sel.getWorld() == null) {
            mines.getMessages().send(player, "selection_needed");
            return;
        }
        // Get name
        if (mines.getMine((String) actor) != null) {
            mines.getMessages().send(player, "mine_exists", (String) actor);
            return;
        }
        // Make the mine
        Mine m = new Mine();
        m.name = (String) actor;
        m.world = sel.getWorld().getName();
        m.min = sel.getMinimumPoint();
        m.max = sel.getMaximumPoint();
        mines.add(m); // This also saves it for us
        // Done!
        mines.getMessages().send(player, "mine_created", m.name);
    }
}
