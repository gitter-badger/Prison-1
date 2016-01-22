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
import org.bukkit.entity.Player;

/**
 * @author SirFaizdat
 */
public class GroupManagerIntegration extends IntegrationAbstract implements PermissionIntegration {

    public GroupManagerIntegration() {
        super("GroupManager");
    }

    @Override
    public String getPrimaryRank(Player player) {
        return null;
    }

    @Override
    public String[] getRanks(Player player) {
        return new String[0];
    }

    @Override
    public void setRank(Player player, String rankName) {

    }
}
