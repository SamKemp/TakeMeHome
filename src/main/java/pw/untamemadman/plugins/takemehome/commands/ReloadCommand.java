package pw.untamemadman.plugins.takemehome.commands;

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

import pw.untamemadman.plugins.takemehome.TakeMeHome;
import pw.untamemadman.plugins.takemehome.TakeMeHomeConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    private TakeMeHome plugin;

    public ReloadCommand(TakeMeHome plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender.hasPermission("takemehome.admin.reload") || sender.isOp()) {
            plugin.setConfig(new TakeMeHomeConfig(plugin.getConfig()));
            sender.sendMessage(ChatColor.GREEN + "TakeMeHome successfully reloaded!");
            plugin.getLogger().info("[TakeMeHome] Successfully reloaded config!");
        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permissions for this command.");
            return false;
        }
        return true;
    }
}
