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

package xyz.springpoint.prison;

import ml.springpoint.springcore.SpringPlugin;
import ml.springpoint.springcore.command.CommandFeature;
import ml.springpoint.springcore.integration.IntegrationFeature;
import ml.springpoint.springcore.module.ModuleFeature;
import xyz.springpoint.prison.integration.IntegrationManager;
import xyz.springpoint.prison.integration.WorldEditIntegration;
import xyz.springpoint.prison.mines.Mines;

/**
 * @author SirFaizdat
 */
public class Prison extends SpringPlugin {

    // =======================
    //  Fields
    // =======================

    private static Prison instance;
    public CommandFeature commands; // Command framework system
    public ModuleFeature modules; // Module system
    public IntegrationFeature integrationFeature; // The actual integration feature
    public IntegrationManager integration; // Permissions and economy integration

    // =======================
    //  Override
    // =======================

    @Override
    protected boolean enable() {
        instance = this;
        // Tell SpringCore which parts of it we want to use
        setLogPrefix("&8[&cPrison&8]");
        use("modules", "commands", "integration", "menus");

        if (!initMessages()) return false; // Disable if messages.json could not be loaded
        if (!initIntegration()) return false; // Disable if no perm/eco plugin found
        commands = (CommandFeature) getFeatureManager().get("commands");

        modules = (ModuleFeature) getFeatureManager().get("modules");
        modules.load(new Mines());

        return true;
    }

    // =======================
    //  Initialization
    // =======================

    private boolean initIntegration() {
        integrationFeature = (IntegrationFeature) getFeatureManager().get("integration");
        integration = new IntegrationManager(integrationFeature);

        // Register WorldEdit, but don't integrate yet.
        integrationFeature.add("worldedit", new WorldEditIntegration());

        if (!integration.initializePermissions()) {
            log("&cError: &7No permissions plugin found.");
            return false;
        }
        if (!integration.initializeEconomy()) {
            log("&cError: &7No economy plugin found.");
            return false;
        }
        return true;
    }

    private boolean initMessages() {
        getMessages().addDefault("action_not_found", "&cError: &7The action &c%s&7 does not exist.");
        return getMessages().load();
    }

    // =======================
    //  Getters
    // =======================

    public static Prison get() {
        return instance;
    }
}
