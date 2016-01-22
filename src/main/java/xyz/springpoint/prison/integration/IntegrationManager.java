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

import ml.springpoint.springcore.integration.IntegrationAbstract;
import ml.springpoint.springcore.integration.IntegrationFeature;
import ml.springpoint.springcore.integration.vault.VaultIntegration;
import org.bukkit.Bukkit;
import xyz.springpoint.prison.integration.economy.EconomyIntegration;
import xyz.springpoint.prison.integration.economy.EssentialsIntegration;
import xyz.springpoint.prison.integration.economy.VaultEconomy;
import xyz.springpoint.prison.integration.economy.iConomyIntegration;
import xyz.springpoint.prison.integration.permission.GroupManagerIntegration;
import xyz.springpoint.prison.integration.permission.PermissionIntegration;
import xyz.springpoint.prison.integration.permission.PermissionsExIntegration;
import xyz.springpoint.prison.integration.permission.VaultPermission;

/**
 * Attempts to integrate with the permissions
 * and economy plugins in sequence, and falls back to Vault if
 * there is no alternative.
 *
 * @author SirFaizdat
 */
public class IntegrationManager {

    // =======================
    //  Fields
    // =======================

    public EconomyIntegration economy;
    public PermissionIntegration permission;

    private IntegrationFeature integration;

    // =======================
    //  Constructor
    // =======================

    public IntegrationManager(IntegrationFeature integration) {
        this.integration = integration;
    }

    // =======================
    //  Initialization
    // =======================

    public boolean initializeEconomy() {
        // Essentials economy
        if(hasPlugin("Essentials")) {
            EssentialsIntegration essentialsIntegration = new EssentialsIntegration();
            if (tryIntegration(essentialsIntegration)) {
                economy = essentialsIntegration;
                return true;
            }
        }
        // iConomy
        if(hasPlugin("iConomy")) {
            iConomyIntegration iConomyIntegration = new iConomyIntegration();
            if (tryIntegration(iConomyIntegration)) {
                economy = iConomyIntegration;
                return true;
            }
        }
        // Vault fallback
        if(hasPlugin("Vault")) {
            VaultIntegration vaultIntegration = (VaultIntegration) integration.integrate("vault");
            if (vaultIntegration == null) return false;
            if (vaultIntegration.getEconomy() == null) return false;
            else {
                economy = new VaultEconomy(vaultIntegration.getEconomy());
                return true;
            }
        }
        return false;
    }

    public boolean initializePermissions() {
        // PermissionsEx
        if(hasPlugin("PermissionsEx")) {
            PermissionsExIntegration permissionsExIntegration = new PermissionsExIntegration();
            if (tryIntegration(permissionsExIntegration)) {
                permission = permissionsExIntegration;
                return true;
            }
        }
        // GroupManager
        if(hasPlugin("GroupManager")) {
            GroupManagerIntegration groupManagerIntegration = new GroupManagerIntegration();
            if (tryIntegration(groupManagerIntegration)) {
                permission = groupManagerIntegration;
                return true;
            }
        }
        // Vault fallback
        if(hasPlugin("Vault")) {
            VaultIntegration vaultIntegration = (VaultIntegration) integration.integrate("vault");
            if (vaultIntegration == null) return false;
            if (vaultIntegration.getPermission() == null) return false;
            else {
                permission = new VaultPermission(vaultIntegration.getPermission());
                return true;
            }
        }
        return false;
    }

    private boolean hasPlugin(String plugin) {
        return Bukkit.getPluginManager().getPlugin(plugin) != null;
    }

    private boolean tryIntegration(IntegrationAbstract toIntegrate) {
        IntegrationAbstract retVal =
                integration.add(toIntegrate.getPlugin().toLowerCase(), toIntegrate).integrate(toIntegrate.getPlugin().toLowerCase());
        return retVal != null;
    }

    // =======================
    //  Getters
    // =======================

    public EconomyIntegration getEconomy() {
        return economy;
    }

    public PermissionIntegration getPermission() {
        return permission;
    }
}
