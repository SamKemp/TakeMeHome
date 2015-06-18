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

import net.anothereon.bukkit.plugins.commands.ReloadCommand;
import net.anothereon.bukkit.plugins.commands.TakeMeHomeCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class TakeMeHome extends JavaPlugin {

    private TakeMeHomeConfig config;

    public TakeMeHomeConfig getTakeMeHomeConfig() {
        return config;
    }

    public void setConfig(TakeMeHomeConfig config) {
        this.config = config;
    }

	public void onEnable() { 

        saveDefaultConfig();
        this.setConfig(new TakeMeHomeConfig(getConfig()));

        getCommand("takemehome").setExecutor(new TakeMeHomeCommand(this));
        getCommand("takemehomereload").setExecutor(new ReloadCommand(this));
	}
}
