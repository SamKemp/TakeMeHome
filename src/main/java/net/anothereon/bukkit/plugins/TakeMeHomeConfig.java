package net.anothereon.bukkit.plugins;

/*
    This file is part of TakeMeHome

    TakeMeHome is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    TakeMeHome is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
 */

import org.bukkit.configuration.file.FileConfiguration;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TakeMeHomeConfig {
    private String world;
    private int dimension;

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public int getDimension() { return this.dimension; }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public TakeMeHomeConfig(FileConfiguration config) {
        if (config == null) {
            Logger.getLogger("Minecraft").log(Level.SEVERE, "[TakeMeHome] Config cannot be null!");
            return;
        }

        this.setWorld(config.getString("main-world"));
        this.setDimension(config.getInt("spawn-dimension-id"));
    }

    public void save(FileConfiguration config) {
        config.set("main-world", this.getWorld());
        config.set("spawn-dimension-id", this.getDimension());
    }
}
