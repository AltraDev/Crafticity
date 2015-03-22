package com.AltraDev.Crafticity;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listeners implements Listener {
	int nPlay = 0;
	
    public void playSmoke (Location loc) {
        
        World world = loc.getWorld();
        world.playEffect(loc, Effect.ENDER_SIGNAL, 20);

    }
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		nPlay = nPlay + 1;
		Player p = e.getPlayer();
			if (p.hasPlayedBefore()) {
		p.playSound(p.getLocation(), Sound.GHAST_MOAN, 9, 1);
		return;
		} else {
			p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 10, 1);
			Bukkit.getServer().broadcastMessage(ChatColor.GREEN + "Welcome " + ChatColor.AQUA + p.getDisplayName() + ChatColor.GREEN + " to the server!  " + ChatColor.AQUA + nPlay + ChatColor.GREEN + " players have joined " + ChatColor.GOLD + "Crafticity");
		}
	}
	
	public void deathSign(Player p, Block b) {
		b.setType(Material.SIGN_POST);
		 
		Sign s = (Sign) b.getState();
		s.setLine(0, ChatColor.RED + "Rest In Peace");
		s.setLine(2, ChatColor.GOLD + p.getName());
		s.update(true);
		}
	
	@EventHandler
	public void playerLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		playSmoke(p.getLocation());
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
