package com.AltraDev.Crafticity;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import com.AltraDev.Crafticity.References;

import com.AltraDev.Crafticity.Listeners;

public class crafticity extends JavaPlugin {
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(new Listeners(), this);
		Bukkit.getServer().getLogger().info("Crafticity has been enabled!");
	}
	
	public void onDisable() {
		Bukkit.getServer().getLogger().info("Crafticity has been disabled!");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player player = (Player) sender;
		
		
		return true;
	}
}
