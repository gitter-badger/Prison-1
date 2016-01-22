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

import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import xyz.springpoint.prison.integration.economy.EconomyIntegration;

/**
 * An EconomyIntegration wrapper for Vault.
 *
 * @author SirFaizdat
 */
public class VaultEconomy implements EconomyIntegration {

    Economy eco;

    public VaultEconomy(Economy eco) {
        this.eco = eco;
    }

    @Override
    public double getBalance(Player player) {
        return eco.getBalance(player);
    }

    @Override
    public void setBalance(Player player, double amount) {
        // Vault doesn't have a set method, so we just
        // set balance to 0 and then put in what we want.
        eco.withdrawPlayer(player, getBalance(player));
        eco.depositPlayer(player, amount);
    }

    @Override
    public void addBalance(Player player, double amount) {
        eco.depositPlayer(player, amount);
    }

    @Override
    public void removeBalance(Player player, double amount) {
        eco.withdrawPlayer(player, amount);
    }
}
