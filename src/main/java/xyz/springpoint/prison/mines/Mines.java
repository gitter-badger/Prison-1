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

import ml.springpoint.springcore.integration.IntegrationAbstract;
import ml.springpoint.springcore.json.GsonFactory;
import ml.springpoint.springcore.module.Module;
import ml.springpoint.springcore.module.ModuleDef;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import xyz.springpoint.prison.Prison;
import xyz.springpoint.prison.integration.WorldEditIntegration;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * The main mines module.
 * Contains methods for dealing with mines (such as reset(), load(), save(), etc.)
 *
 * @author SirFaizdat
 */
@ModuleDef(name = "Mines")
public class Mines extends Module {

    // =======================
    //  Fields
    // =======================

    public WorldEditIntegration worldEdit;
    File savesFolder;
    private List<Mine> mines;

    // =======================
    //  Override
    // =======================

    @Override
    protected boolean enable() {
        // Require WorldEdit
        IntegrationAbstract rawWE = Prison.get().integrationFeature.integrate("worldedit");
        if (rawWE == null) return false;
        worldEdit = (WorldEditIntegration) rawWE;

        // Initialize files
        if (!initMessages()) return false;

        // Initialize mines
        savesFolder = new File(getDataFolder(), "saves");
        if (!savesFolder.exists()) savesFolder.mkdir();
        mines = new ArrayList<>();
        loadAll();

        // Other stuff
        Prison.get().commands.registerCommands(new MineCommand(this));

        return true;
    }

    // =======================
    //  Initialization
    // =======================

    private boolean initMessages() {
        getMessages().addDefault("selection_needed", "&cError: &7You must make a WorldEdit selection first. &8To do this, type //wand.");
        getMessages().addDefault("mine_exists", "&cError: &7A mine called &c%s&7 already exists.");
        getMessages().addDefault("mine_does_not_exist", "&cError: &7A mine called &c%s&7 does not exist.");
        getMessages().addDefault("mine_created", "&aSuccess: &7Created a mine called &a%s&7.");
        getMessages().addDefault("mine_manual_reset", "&aSuccess: &7Reset the mine &a%s&7.");
        getMessages().addDefault("mine_deleted", "&aSuccess: &7Deleted the mine &a%s&7.");
        return getMessages().load();
    }

    private void loadAll() {
        // Only files that are of the type we want (.mine.json)
        File[] files = savesFolder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".mine.json");
            }
        });

        for (File file : files) mines.add(load(file));

        Prison.get().log("&7Loaded &b%d&7 mines.", mines.size());
    }

    // =======================
    //  Methods
    // =======================

    /**
     * Adds a mine to the list, and saves it.
     *
     * @param m The Mine object.
     */
    public void add(Mine m) {
        save(m, new File(savesFolder, m.name + ".mine.json"));
        mines.add(m);
    }

    /**
     * Load a mine from a file.
     *
     * @param file The mine's file. It must exist, and it must be of the type .mine.json.
     * @return The Mine's object, or null if it failed (it will also print an error message if it fails).
     */
    public Mine load(File file) {
        try {
            String json = new String(Files.readAllBytes(Paths.get(file.getPath())));
            return GsonFactory.getCompactGson().fromJson(json, Mine.class);
        } catch (IOException e) {
            Prison.get().log("&cError: &7Failed to read mine file &c%s&7.", file.getPath());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Save a mine to a file.
     *
     * @param m    The Mine object.
     * @param file The file to save to.
     * @return true if the save succeeded, false otherwise.
     */
    public boolean save(Mine m, File file) {
        String json = GsonFactory.getPrettyGson().toJson(m);
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean delete(Mine m) {
        return mines.remove(m) && new File(savesFolder, m.name + ".mine.json").delete();
    }

    /**
     * Reset a mine.
     *
     * @param m The Mine object of the mine to reset.
     * @return true if the reset succeeded (normally it does), false otherwise.
     */
    public boolean reset(Mine m) {
        // Store all the positions
        int minX = m.min.getBlockX();
        int minY = m.min.getBlockY();
        int minZ = m.min.getBlockZ();
        int maxX = m.max.getBlockX();
        int maxY = m.max.getBlockY();
        int maxZ = m.max.getBlockZ();
        // Iterate
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                for (int z = minZ; z <= maxZ; z++) {
                    Bukkit.getWorld(m.world).getBlockAt(x, y, z).setType(Material.BEDROCK);
                }
            }
        }
        // Notify
        return true;
    }

    // =======================
    //  Getters
    // =======================

    public Mine getMine(String name) {
        for (Mine m : mines) if (m.name.equals(name)) return m;
        return null;
    }
}
