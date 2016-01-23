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

package xyz.springpoint.prison.integration.permission;

import ml.springpoint.springcore.integration.IntegrationAbstract;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

/**
 * Integrate with PermissionsEx. This class is only meant to be
 * instantiated when it is added to the integration feature upon enable.
 *
 * @author SirFaizdat
 */
public class PermissionsExIntegration extends IntegrationAbstract implements PermissionIntegration {

    PermissionsEx perms;

    public PermissionsExIntegration() {
        super("PermissionsEx");
    }

    @Override
    public void activate() {
        perms = (PermissionsEx) Bukkit.getServer().getPluginManager().getPlugin("PermissionsEx");
    }

    @Override
    public String getPrimaryRank(Player player, String world) {
        return perms.getPermissionsManager().getUser(player).getGroupNames(world)[0];
    }

    @Override
    public String getPrimaryRank(Player player) {
        return perms.getPermissionsManager().getUser(player).getGroupNames()[0];
    }

    @Override
    public String[] getRanks(Player player, String world) {
        return perms.getPermissionsManager().getUser(player).getGroupNames(world);
    }

    @Override
    public String[] getRanks(Player player) {
        return perms.getPermissionsManager().getUser(player).getGroupNames();
    }

    @Override
    public void setRank(Player player, String rankName, String world) {
        // Is this the right way to do it?
        perms.getPermissionsManager().getUser(player).removeGroup(getPrimaryRank(player), world);
        perms.getPermissionsManager().getUser(player).addGroup(rankName, world);
    }

    @Override
    public void setRank(Player player, String rankName) {
        // Is this the right way to do it?
        perms.getPermissionsManager().getUser(player).removeGroup(getPrimaryRank(player));
        perms.getPermissionsManager().getUser(player).addGroup(rankName);
    }
}
