package com.AltraDev.Crafticity;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import static org.bukkit.ChatColor.*;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class Crafticity extends JavaPlugin implements Listener {
	
	
	
	/*
	 TODO: 
	 	Make a announcer
	 	Make the join book (BookMeta now)
	 	Make the admin gem
	 */
	public ArrayList<UUID> cooldown = new ArrayList<UUID>(); //Poke CoolDown
	
	
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		Bukkit.getServer().getLogger().info("Crafticity has been enabled!");
		cooldown.clear();
	}
	
	public void onDisable() {
		Bukkit.getServer().getLogger().info("Crafticity has been disabled!");
		cooldown.clear();
	}
	
	public void playSmoke (Location loc) {
        
        World world = loc.getWorld();
        world.playEffect(loc, Effect.ENDER_SIGNAL, 20);

    }
	
	
// BEGINNING OF COMMANDS!
	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		Player player = (Player) sender;
		// Start of Poke Command
		if (cmd.getName().equalsIgnoreCase("poke")) {
			
			if (args.length == 0) {
				sender.sendMessage(GOLD + "Use this command to poke someone!");
				return true;
			}
			
			if (cooldown.contains(player.getUniqueId())) {
				player.sendMessage(RED + "Poke is in cooldown!");
				return true;
			}
			
			Player target = Bukkit.getServer().getPlayer(args[0]);
			
			if (target == null) {
				sender.sendMessage(GOLD + "Could not find player");
				return true;
			}
			
			if (target == sender) {
				sender.sendMessage(RED + "You can not poke yourself!");
				return true;
			}
			
			sender.sendMessage(GOLD + "You have poked " + AQUA + target.getName() + GOLD + "!");
			
			target.playSound(target.getLocation(), Sound.NOTE_PLING, 10, 1);
			target.sendMessage(AQUA + sender.getName() + GOLD + " has poked you!");
			cooldown(player);
			
		return true;
		}
		//End of poke command!
		if (cmd.getName().equalsIgnoreCase("Testbook")) {
			ItemStack bs = new ItemStack(Material.WRITTEN_BOOK);
			BookMeta bm = (BookMeta) bs.getItemMeta();
			bm.addPage(DARK_AQUA + "     " + BOLD + "Crafticity \n"
					);
			bs.setItemMeta(bm);
			Inventory inv = player.getInventory();
			inv.clear();
			player.getInventory().setItem(2, bs);
			return true;
		}
		return true;
	}
// END OF COMMANDS

//COOLDOWN BEGIN
	 public void cooldown(final Player p) {
		  final UUID uuid = p.getUniqueId();
		  if(cooldown.contains(uuid)) { return; }
		  cooldown.add(uuid);
		  p.sendMessage(RED + "Poke is now in cooldown.");
		  Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
		   public void run() {
		    cooldown.remove(uuid);
		    p.sendMessage(GREEN + "You can now poke somone again!");
		   }
		  }, 200);
		 }
//COOLDOWN END
	 
// EVENT HANDLERS
		@EventHandler
		public void onPlayerDeath(PlayerDeathEvent event) {
			
			Entity entity = event.getEntity();
			 
			Player p = (Player) entity;
			p.sendMessage(GREEN + "A sign has been placed at your death spot!");
			deathSign(p, event.getEntity().getLocation().getBlock());
			
		}
		
		@EventHandler
		public void deathSign(Player p, Block b) {
			b.setType(Material.SIGN_POST);
			 
			final Sign s = (Sign) b.getState();
			s.setLine(0, RED + "Rest In Peace");
			s.setLine(2, GREEN + p.getName());
			s.update(true);
	        Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
	      	  public void run() {
	      	    s.getBlock().breakNaturally();
	      	  }
	      	}, 500L);
			}
		
		@EventHandler
		public void onPlayerJoin(PlayerJoinEvent e) {
			Player p = e.getPlayer();
			if (!p.hasPlayedBefore()) {
				p.playSound(p.getLocation(), Sound.NOTE_PLING, 10, 1);
			} else {
				p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 10, 1);
			}
			
		}
		
		@EventHandler
		public void playerLeave(PlayerQuitEvent e) {
			Player p = e.getPlayer();
			playSmoke(p.getLocation());
		}
// END OF EVENT HANDLERS
}
