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

package xyz.springpoint.prison.ranks;

import ml.springpoint.springcore.json.GsonFactory;
import ml.springpoint.springcore.module.Module;
import ml.springpoint.springcore.module.ModuleDef;
import xyz.springpoint.prison.Prison;
import xyz.springpoint.prison.Utils;
import xyz.springpoint.prison.mines.Mine;

import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * The ranks module.
 *
 * @author SirFaizdat
 */
@ModuleDef(name = "Ranks")
public class Ranks extends Module {

    // =======================
    //  Fields
    // =======================

    private File savesFolder;
    private List<Rank> ranks;

    // =======================
    //  Override
    // =======================

    @Override
    protected boolean enable() {
        // "saves" folder
        savesFolder = new File(Prison.get().getDataFolder(), "saves");
        if (!savesFolder.exists()) savesFolder.mkdir();

        // Load ranks
        ranks = new ArrayList<>();
        loadAll();

        // Done!
        return true;
    }

    // =======================
    //  Initialization
    // =======================

    private void loadAll() {
        File[] files = savesFolder.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".rank.json");
            }
        });

        for (File rankFile : files) {
            Rank rank = load(rankFile);
            if (rank == null) break;
            ranks.add(rank);
        }

        log("&7Loaded &b%d&7 ranks.", ranks.size());
    }

    // =======================
    //  Methods
    // =======================

    public Rank load(File file) {
        try {
            String json = new String(Files.readAllBytes(Paths.get(file.getPath())));
            return GsonFactory.getCompactGson().fromJson(json, Rank.class);
        } catch (IOException e) {
            log("&cError: &7Failed to read rank file &c%s&7.", file.getPath());
            e.printStackTrace();
            return null;
        }
    }

    public boolean save(Rank rank, File file) {
        String json = GsonFactory.getPrettyGson().toJson(rank);
        return Utils.writeToFile(json, file);
    }

    public void add(Rank rank) {
        ranks.add(rank);
        save(rank, new File(savesFolder, rank.name + ".rank.json"));
    }

    public boolean delete(Rank rank) {
        ranks.remove(rank);
        return new File(savesFolder, rank.name + ".rank.json").delete();
    }

}
