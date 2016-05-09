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

import com.comphenix.protocol.wrappers.nbt.NbtCompound;
import com.comphenix.protocol.wrappers.nbt.NbtFactory;
import pw.untamemadman.plugins.takemehome.TakeMeHome;
import pw.untamemadman.plugins.takemehome.util.NbtUtils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;

public class TakeMeHomeCommand implements CommandExecutor {

    private TakeMeHome plugin;

    public TakeMeHomeCommand(TakeMeHome plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender.hasPermission("takemehome.admin.tmh") || sender.isOp()) {
            if (args.length != 1) {
                sender.sendMessage(ChatColor.YELLOW + "Usage: " + ChatColor.WHITE + "/tmh <UUID>");
                plugin.getLogger().severe("command called without a UUID argument!");
                return false;
            }

            UUID uuid;
            try {
                uuid = UUID.fromString(args[0]);
            } catch (IllegalArgumentException ex)
            {
                sender.sendMessage(ChatColor.RED + "Argument is not a valid UUID!");
                plugin.getLogger().severe("TakeMeHome command called with invalid UUID argument!");
                return false;
            }

            if (uuid == null) {
                sender.sendMessage(ChatColor.RED + "Argument is not a valid UUID!");
                plugin.getLogger().severe("TakeMeHome command called with invalid UUID argument!");
                return false;
            }

            World world = plugin.getServer().getWorld(plugin.getTakeMeHomeConfig().getWorld());
            Location loc = plugin.getServer().getWorld(world.getName()).getSpawnLocation();

            try {
                NbtCompound playerData = NbtUtils.loadPlayerData(world, uuid);

                playerData.put("Dimension", plugin.getTakeMeHomeConfig().getDimension());
                playerData.put("Pos", NbtFactory.ofList("Pos", (double) loc.getBlockX(), (double) loc.getBlockY(), (double) loc.getBlockZ()));

                NbtUtils.savePlayerData(world, uuid, playerData);
            } catch (IOException ex) {
                plugin.getLogger().log(Level.SEVERE, "[TakeMeHome] Error setting position in player data!", ex);
                return false;
            }

            sender.sendMessage(ChatColor.GREEN + "Position of " + args[0] + " successfully set to spawn!");

        } else {
            sender.sendMessage(ChatColor.RED + "You do not have permissions for this command.");
            return false;
        }
        return true;
    }
}
