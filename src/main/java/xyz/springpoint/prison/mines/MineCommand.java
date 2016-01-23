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
import xyz.springpoint.prison.Action;
import xyz.springpoint.prison.Prison;
import xyz.springpoint.prison.mines.actions.CreateAction;
import xyz.springpoint.prison.mines.actions.ResetAction;

import java.util.HashMap;
import java.util.Map;

/**
 * @author SirFaizdat
 */
public class MineCommand {

    // =======================
    //  Fields
    // =======================

    private Map<String, Action> actions;

    // =======================
    //  Constructor
    // =======================

    public MineCommand(Mines mines) {
        actions = new HashMap<>();
        actions.put("create", new CreateAction(mines));
        actions.put("reset", new ResetAction(mines));
    }

    // =======================
    //  Commands
    // =======================

    @Command(name = "mines", permission = "prison.mines", aliases = {"mine"}, description = "Manage the mines module.", usage = "/mines <mine> <action> [args...]", minArgs = 2)
    public void baseCommand(CommandArgs args) {
        // Handles the Actions now.
        String mineName = args.getArgs(0);
        String actionName = args.getArgs(1);
        String[] newArgs = shortenArray(args.getArgs(), 2); // Remove args 0 and 1 (name and action)
        Action action = actions.get(actionName.toLowerCase());
        if (action == null) Prison.get().getMessages().send(args.getSender(), "action_not_found", actionName);
        else action.run(mineName, args.getSender(), newArgs);
    }

    @Command(name = "mines.help", permission = "prison.mines", aliases = {"mine.help"}, description= "Help using the /mines command.", usage = "/mines help")
    public void helpCommand(CommandArgs args) {
        args.getSender().sendMessage("To be created.");
    }

    // =======================
    //  Utilities
    // =======================

    private String[] shortenArray(String[] oldArray, int toShorten) {
        int n = oldArray.length - toShorten;
        String[] newArray = new String[n];
        System.arraycopy(oldArray, toShorten, newArray, 0, n);
        return newArray;
    }

}
