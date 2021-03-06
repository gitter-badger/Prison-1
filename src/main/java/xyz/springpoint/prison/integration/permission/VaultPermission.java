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

import net.milkbowl.vault.permission.Permission;
import org.bukkit.entity.Player;

/**
 * @author SirFaizdat
 */
public class VaultPermission implements PermissionIntegration {

    Permission permission;

    public VaultPermission(Permission permission) {
        this.permission = permission;
    }

    @Override
    public String getPrimaryRank(Player player, String world) {
        return permission.getPrimaryGroup(world, player);
    }

    @Override
    public String getPrimaryRank(Player player) {
        return permission.getPrimaryGroup(player);
    }

    @Override
    public String[] getRanks(Player player, String world) {
        return permission.getPlayerGroups(world, player);
    }

    @Override
    public String[] getRanks(Player player) {
        return permission.getPlayerGroups(player);
    }

    @Override
    public void setRank(Player player, String rankName, String world) {
        permission.playerRemoveGroup(world, player, getPrimaryRank(player, world));
        permission.playerAddGroup(world, player, rankName);
    }

    @Override
    public void setRank(Player player, String rankName) {
        permission.playerRemoveGroup(player, getPrimaryRank(player));
        permission.playerAddGroup(player, rankName);
    }
}
