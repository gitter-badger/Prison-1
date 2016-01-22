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
import xyz.springpoint.prison.integration.IntegrationManager;

/**
 * @author SirFaizdat
 */
public class Prison extends SpringPlugin {

    // =======================
    //  Fields
    // =======================

    CommandFeature commands; // Command framework system
    IntegrationManager integration; // Permissions and economy integration

    // =======================
    //  Override
    // =======================

    @Override
    protected boolean enable() {
        // Tell SpringCore which parts of it we want to use
        setLogPrefix("&8[&cPrison&8]");
        use("modules", "commands", "integration", "menus");

        commands = (CommandFeature) getFeatureManager().get("commands");
        if (!initIntegration()) return false; // Disable if no perm/eco plugin found

        return true;
    }

    // =======================
    //  Initialization
    // =======================

    private boolean initIntegration() {
        IntegrationFeature integrationFeature = (IntegrationFeature) getFeatureManager().get("integration");
        integration = new IntegrationManager(integrationFeature);

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

}
