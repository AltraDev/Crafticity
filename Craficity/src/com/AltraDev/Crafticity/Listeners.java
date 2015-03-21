package com.AltraDev.Crafticity;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class Listeners implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		p.playSound(p.getLocation(), Sound.GHAST_MOAN, 9, 1);
	}
	
	public void deathSign(Player p, Block b) {
		b.setType(Material.SIGN_POST);
		 
		Sign s = (Sign) b.getState();
		s.setLine(0, ChatColor.RED + "Rest In Peace");
		s.setLine(2, ChatColor.GOLD + p.getName());
		s.update(true);
		}
	
	@EventHandler
	public void onPlayerDeath(EntityDeathEvent event) {
		
		Entity entity = event.getEntity();
		 
		if (entity instanceof Player) {
			Player p = (Player) entity;
			p.sendMessage(ChatColor.GREEN + "A sign has been placed at your death spot!");
			deathSign(p, event.getEntity().getLocation().getBlock());
		
		}
		
	}
}
