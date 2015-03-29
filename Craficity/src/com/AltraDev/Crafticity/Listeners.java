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
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
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
        final Zombie v = (Zombie) loc.getWorld().spawn(loc, Zombie.class);
        v.setCustomName(name);
        v.setCustomNameVisible(true);
        v.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 10000*10000, 20));
        v.setHealth(0.5);
        v.getRemoveWhenFarAway();
        v.setCanPickupItems(false);
        v.hasLineOfSight(null);
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin) this, new Runnable() {
			@Override 
		    public void run() {
		        v.setHealth(0.0);
		    }
		 
		}, 500L);
    }
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		
		Player p = e.getPlayer();

		p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 10, 1);
		
		}
	
	//Death Signs
	
	public void deathSign(Player p, Block b) {
		b.setType(Material.SIGN_POST);
		 
		final Sign s = (Sign) b.getState();
		s.setLine(0, ChatColor.RED + "Rest In Peace");
		s.setLine(2, ChatColor.GREEN + p.getName());
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
	public void onPlayerDeath(PlayerDeathEvent event) {
		
		Entity entity = event.getEntity();
		 
		Player p = (Player) entity;
		p.sendMessage(ChatColor.GREEN + "A sign has been placed at your death spot!");
		deathSign(p, event.getEntity().getLocation().getBlock());
		
	}

}
