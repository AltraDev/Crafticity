package com.AltraDev.Crafticity;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.AltraDev.Crafticity.Listeners;

public class Crafticity extends JavaPlugin {
	
	private ArrayList<UUID> cooldown = new ArrayList<UUID>();
	
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(new Listeners(), this);
		Bukkit.getServer().getLogger().info("Crafticity has been enabled!");
		cooldown.clear();
	}
	
	public void onDisable() {
		Bukkit.getServer().getLogger().info("Crafticity has been disabled!");
		cooldown.clear();
	}

	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player player = (Player) sender;
		// Start of Poke Command
		if (cmd.getName().equalsIgnoreCase("poke")) {
			if (args.length == 0) {
				sender.sendMessage(ChatColor.GOLD + "Use this command to poke someone!");
			}
			if (cooldown.contains(player.getUniqueId())) {
				player.sendMessage(ChatColor.RED + "Poke is in cooldown!");
				return true;
			}
			@SuppressWarnings("deprecation")
			Player target = Bukkit.getServer().getPlayer(args[0]);
			if (target == null) {
				sender.sendMessage(ChatColor.GOLD + "Could not find player");
				return true;
			}
			if (target == sender) {
				sender.sendMessage(ChatColor.RED + "You can not poke yourself!");
				return true;
			}
			sender.sendMessage(ChatColor.GOLD + "You have poked " + ChatColor.AQUA + target.getName() + ChatColor.GOLD + "!");
			
			target.playSound(target.getLocation(), Sound.NOTE_PLING, 10, 1);
			target.sendMessage(ChatColor.AQUA + sender.getName() + ChatColor.GOLD + " has poked you!");
		return true;
		//End of poke command!
		}
		//Beginning of Warning command for staff members
		@SuppressWarnings("deprecation")
		Player target = Bukkit.getServer().getPlayer(args[0]);
		
		if (cmd.getName().equalsIgnoreCase("warning")) {
			if (!sender.hasPermission("crafticity.warning")) {
				sender.sendMessage("You do not has permssion to create a warning!");
				return true;
			}
			if (args.length == 0) {
				sender.sendMessage("Usage: /warning <type> <level> <message>");
				return true;
			}
			if (cmd.getName().equalsIgnoreCase("red")) {
				if (args.length == 1) {
				target.sendMessage(ChatColor.DARK_RED +"Warning! Warning! Code" + ChatColor.RED + "RED" + ChatColor.RED + "from" + ChatColor.RED + player);
				return true;
				}
			}
			
			return true;
		}
		return true;
	}
	
	
	
	 public void cooldown(final Player p) {
		  final UUID uuid = p.getUniqueId();
		  if(cooldown.contains(uuid)) { return; }
		  cooldown.add(uuid);
		  p.sendMessage(ChatColor.RED + "Poke is now in cooldown.");
		  Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
		   public void run() {
		    cooldown.remove(uuid);
		    p.sendMessage(ChatColor.GREEN + "You can now poke somone again!");
		   }
		  }, 100L);
		 }
}
