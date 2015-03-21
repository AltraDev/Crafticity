package com.AltraDev.Crafticity;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.AltraDev.Crafticity.References;
import com.AltraDev.Crafticity.Listeners;

public class Crafticity extends JavaPlugin {
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(new Listeners(), this);
		Bukkit.getServer().getLogger().info("Crafticity has been enabled!");
	}
	
	public void onDisable() {
		Bukkit.getServer().getLogger().info("Crafticity has been disabled!");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player player = (Player) sender;
		// Start of Poke Command
		if (cmd.getName().equalsIgnoreCase("poke")) {
			   if (args.length == 0) {
				  
			   sender.sendMessage(ChatColor.GOLD + "Use this command to poke someone!");
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
			   
			   //End of poke command
		}
		return true;
	}
}
