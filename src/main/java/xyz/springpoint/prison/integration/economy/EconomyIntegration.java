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

package xyz.springpoint.prison.integration.economy;

import org.bukkit.entity.Player;

/**
 * All integrations dealing with economy.
 *
 * @author SirFaizdat
 */
public interface EconomyIntegration {

    double getBalance(Player player);

    void setBalance(Player player, double amount);

    void addBalance(Player player, double amount);

    void removeBalance(Player player, double amount);

}
