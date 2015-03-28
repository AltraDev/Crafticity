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
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;



public class Listeners implements Listener {
	
	
    public void playSmoke (Location loc) {
        
        World world = loc.getWorld();
        world.playEffect(loc, Effect.ENDER_SIGNAL, 20);

    }
    
    public void spawnVillager(Location loc, String name) {
        final Villager v = (Villager) loc.getWorld().spawn(loc, Villager.class);
        v.setCustomName(name);
        v.setCustomNameVisible(true);
        v.setAdult();
        v.setAgeLock(true);
        v.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10000*10000, 20));
        Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin) this, new Runnable() {
     	   public void run() {
     	    v.setHealth(0.0);
     	   }
     	  }, 300L); // 15s
    }
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		
		Player p = e.getPlayer();
		
			if (p.hasPlayedBefore()) return;

			p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 10, 1);
		}
	
	//Death Signs
	
	public void deathSign(Player p, Block b) {
		b.setType(Material.SIGN_POST);
		 
		Sign s = (Sign) b.getState();
		s.setLine(0, ChatColor.RED + "Rest In Peace");
		s.setLine(2, ChatColor.GOLD + p.getName());
		s.update(true);
		}
	//Villager spawning
	

	@EventHandler
	public void playerLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		playSmoke(p.getLocation());
		spawnVillager(p.getLocation(), ChatColor.DARK_RED + "Log Out: " + ChatColor.RED + p.getName());
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
