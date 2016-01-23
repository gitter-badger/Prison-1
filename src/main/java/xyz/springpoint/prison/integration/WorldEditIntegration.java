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

package xyz.springpoint.prison.integration;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.bukkit.selections.Selection;
import ml.springpoint.springcore.integration.IntegrationAbstract;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * @author SirFaizdat
 */
public class WorldEditIntegration extends IntegrationAbstract {

    // =======================
    //  Fields
    // =======================

    WorldEditPlugin worldEdit;

    // =======================
    //  Constructor
    // =======================

    public WorldEditIntegration() {
        super("WorldEdit");
    }

    // =======================
    //  Override
    // =======================

    @Override
    public void activate() {
        worldEdit = (WorldEditPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");
    }


    // =======================
    //  Getters
    // =======================

    public Selection getSelection(Player player) {
        return worldEdit.getSelection(player);
    }

}
