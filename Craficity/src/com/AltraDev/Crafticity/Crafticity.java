package com.AltraDev.Crafticity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

import org.bukkit.Bukkit;

import static org.bukkit.ChatColor.*;

import org.bukkit.Effect;
import org.bukkit.GameMode;
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
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class Crafticity extends JavaPlugin implements Listener {
	
	
	
	/*
	 TODO: 
	 	Make a announcer - done
	 	Make the join book (BookMeta now)
	 	Make the admin gem
	 */
	public ArrayList<UUID> cooldown = new ArrayList<UUID>(); //Poke CoolDown
	private Inventory inv, adminInv, md, gm, players, time, test;
	
	String ct = GRAY + "[" + RED + "Crafticity" + GRAY + "] ";
	
	Player pl;
	ItemStack dia = new ItemStack(Material.DIAMOND);
	ItemMeta diaMeta = dia.getItemMeta();
	ItemStack admin = new ItemStack(Material.REDSTONE);
	ItemMeta aMeta = admin.getItemMeta();
	ItemStack mod = new ItemStack(Material.EMERALD);
	ItemMeta mMeta = mod.getItemMeta();
	
	ItemStack bs = new ItemStack(Material.WRITTEN_BOOK);
	BookMeta bm = (BookMeta) bs.getItemMeta();
	public void onEnable() {
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		Bukkit.getServer().getLogger().info("Crafticity has been enabled!");
		cooldown.clear();
		
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			public void run() {
				Bukkit.getServer().broadcastMessage(ct + DARK_AQUA + "Join our website and donate:"+ AQUA + " crafticity.enjin.com");
				Bukkit.getServer().broadcastMessage(ct + DARK_AQUA + "Type /vote and vote for the server to receive rewards!");
			}
		}, 20, 20 * 60 * 20);
		
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
			if(player.hasPermission("crafticity.poke")) {
			
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
		}
		//End of poke command!
		//START OF THE SERVER BOOK COMMAND
		if (cmd.getName().equalsIgnoreCase("serverbook")) {
			bm.addPage(DARK_AQUA + "Crafticity \n" + "\n"+ DARK_GREEN + "Server Rules: \n" + BLUE + " 1. No Advertising \n" + DARK_GRAY + "(Will result in ban or mute) \n" + BLUE + " 2. No impersonating a staff member\n" + DARK_GRAY + "(Will result in mute or ban)\n" + BLUE + " 3. Keep swearing to a minimal\n" + DARK_GRAY + "(Will result in mute)\n", BLUE + " 4. No Sexual, Racial, Rude comments towards anyone\n" + DARK_GRAY + "(Will result in mute)\n" + BLUE + " 5. DO NOT disrespect staff\n" + DARK_GRAY + "(Will result in ban or mute)" + BLUE + " 6. Do spam or use all caps\n" + DARK_GRAY + "(Will result in mute)",
				DARK_GREEN + "Server Store: \n" + DARK_AQUA + "Type /shop in-game! \n" + BLUE + "Welcome to the Crafticity shop! All details of the items are in the shop!\n" + DARK_GREEN + "Ranks: \n" + DARK_GRAY + "[Coal]" + BLUE + "- $5\n" + GRAY + "[Iron]" + BLUE + "- $10\n" + GOLD + "[Gold]" + BLUE + "- $25\n" + RED + "[Redstone]" + BLUE + "- $35\n" + AQUA + "[Diamond]" + BLUE + "- $50",
				DARK_GREEN + "In-Game Money: \n" + "\n" + GOLD + "[10k]" + BLUE + " - $10\n" + GOLD + "[25k]" + BLUE + "- $20\n" + GOLD + "[50k]" + BLUE + "- $35\n" + GOLD + "[100k]" + BLUE + "- $50",
				DARK_GREEN + "Others: \n" + "\n" + RED + "[Unban]" + BLUE + "- $10\n" + RED + "[Custom Prefix]" + BLUE + "- $5");
			bm.setAuthor(DARK_AQUA + "Crafticity");
			bm.setTitle(GOLD + "Crafticity Server");
			bs.setItemMeta(bm);
			player.getInventory().addItem(bs);
			return true;
		} //END OF THE SERVER BOOK COMMAND
		//START OF THE ADMINGEM
		if (cmd.getName().equalsIgnoreCase("admingem")) {
			if (!player.hasPermission("crafticity.admingem.use")) {
				player.sendMessage(RED + "You do not have permission for an " + DARK_AQUA + "AdminGem" + RED + "!");
			} else {
				diaMeta.setDisplayName(DARK_AQUA + "AdminGem");
				dia.setItemMeta(diaMeta);
				player.getInventory().setItem(8, dia);
			}
			return true;
		} //END OF THE ADMINGEM
		if(cmd.getName().equalsIgnoreCase("vote")) {
			sender.sendMessage(DARK_AQUA + "Website 1: http://www.minestatus.net/130797-crafticity \n" +  "Website 2: http://minecraftservers.org/server/226381\n" + "Website 3: [Disabled] \n");
		return true;
		}
		//START OF INFINITE EFFECT
		if (cmd.getName().equalsIgnoreCase("ieffect")) {	
			int amp = 1;
			if (!player.hasPermission("crafticity.ieffect")) {
				player.sendMessage(RED + "You do not have permission to use " + DARK_AQUA + "iEffect" + RED + "!");
			} else {
				if (args.length == 0) {
					player.sendMessage(GOLD + "Enables you to create infinite effects");
					player.sendMessage(GOLD + "Usage: " + RED +"/ieffect EFFECT STRENGTH");
					player.sendMessage(GOLD + "To see a list of effects do " + RED + "/ieffect list");
				} else {
					switch (args[0].toLowerCase()) {
					case "list": 
						player.sendMessage(GOLD + "Available Effects: " + DARK_AQUA + "Absorption" + GOLD + ", " + DARK_AQUA + "Blindness" + GOLD + ", " + DARK_AQUA + "Confusion" + GOLD + ", " + DARK_AQUA + "Resistance" + GOLD + ", " + DARK_AQUA + "Haste" + GOLD + ", " + DARK_AQUA + "Fire_Resistance" + GOLD + ", " + DARK_AQUA + "Harm" + GOLD + ", " + DARK_AQUA + "Heal" + GOLD + ", " + DARK_AQUA + "Health_Boost" + GOLD + ", " + DARK_AQUA + "Hunger" + GOLD + ", " + DARK_AQUA + "Strength" + GOLD + ", " + DARK_AQUA + "Invisibility" + GOLD + ", " + DARK_AQUA + "Jump" + GOLD + ", " + DARK_AQUA + "Night_Vision" + GOLD + ", " + DARK_AQUA + "Poison" + GOLD + ", " + DARK_AQUA + "Regeneration" + GOLD + ", " + DARK_AQUA + "Saturation" + GOLD + ", " + DARK_AQUA + "Slowness" + GOLD + ", " + DARK_AQUA + "Mining_Fatigue" + GOLD + ", " + DARK_AQUA + "Speed" + GOLD + ", " + DARK_AQUA + "Water_Breathing" + GOLD + ", " + DARK_AQUA + "Weakness" + GOLD + ", " + DARK_AQUA + "Wither");
						break;
					case "absorption":
						if (!(args.length == 2)) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, Integer.MAX_VALUE, 1));
							break;
						}
						amp = Integer.parseInt(args[1]);
						player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, Integer.MAX_VALUE, amp));
						break;
					case "blindness":
						if (!(args.length == 2)) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 1));
							break;
						}
						amp = Integer.parseInt(args[1]);
						player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, amp));
						break;
					case "confusion":
						if (!(args.length == 2)) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, Integer.MAX_VALUE, 1));
							break;
						}
						amp = Integer.parseInt(args[1]);
						player.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, Integer.MAX_VALUE, amp));
						break;
					case "resistance":
						if (!(args.length == 2)) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1));
							break;
						}
						amp = Integer.parseInt(args[1]);
						player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, amp));
						break;
					case "haste":
						if (!(args.length == 2)) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1));
							break;
						}
						amp = Integer.parseInt(args[1]);
						player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, amp));
						break;
					case "fire_resistance":
						if (!(args.length == 2)) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 1));
							break;
						}
						amp = Integer.parseInt(args[1]);
						player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, amp));
						break;
					case "harm":
						if (!(args.length == 2)) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HARM, Integer.MAX_VALUE, 1));
							break;
						}
						amp = Integer.parseInt(args[1]);
						player.addPotionEffect(new PotionEffect(PotionEffectType.HARM, Integer.MAX_VALUE, amp));
						break;
					case "heal":
						if (!(args.length == 2)) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, Integer.MAX_VALUE, 1));
							break;
						}
						amp = Integer.parseInt(args[1]);
						player.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, Integer.MAX_VALUE, amp));
						break;
					case "health_boost":
						if (!(args.length == 2)) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, 1));
							break;
						}
						amp = Integer.parseInt(args[1]);
						player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, Integer.MAX_VALUE, amp));
						break;
					case "hunger":
						if (!(args.length == 2)) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, Integer.MAX_VALUE, 1));
							break;
						}
						amp = Integer.parseInt(args[1]);
						player.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, Integer.MAX_VALUE, amp));
						break;
					case "strength":
						if (!(args.length == 2)) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1));
							break;
						}
						amp = Integer.parseInt(args[1]);
						player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, amp));
						break;
					case "invisibility":
						if (!(args.length == 2)) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1));
							break;
						}
						amp = Integer.parseInt(args[1]);
						player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, amp));
						break;
					case "jump":
						if (!(args.length == 2)) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 1));
							break;
						}
						amp = Integer.parseInt(args[1]);
						player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, amp));
						break;
					case "night_vision":
						if (!(args.length == 2)) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 1));
							break;
						}
						amp = Integer.parseInt(args[1]);
						player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, amp));
						break;
					case "poison":
						if (!(args.length == 2)) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, Integer.MAX_VALUE, 1));
							break;
						}
						amp = Integer.parseInt(args[1]);
						player.addPotionEffect(new PotionEffect(PotionEffectType.POISON, Integer.MAX_VALUE, amp));
						break;
					case "regeneration":
						if (!(args.length == 2)) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 1));
							break;
						}
						amp = Integer.parseInt(args[1]);
						player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, amp));
						break;
					case "saturation":
						if (!(args.length == 2)) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, 1));
							break;
						}
						amp = Integer.parseInt(args[1]);
						player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, Integer.MAX_VALUE, amp));
						break;
					case "slowness":
						if (!(args.length == 2)) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1));
							break;
						}
						amp = Integer.parseInt(args[1]);
						player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, amp));
						break;
					case "mining_fatigue":
						if (!(args.length == 2)) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, 1));
							break;
						}
						amp = Integer.parseInt(args[1]);
						player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, Integer.MAX_VALUE, amp));
						break;
					case "speed":
						if (!(args.length == 2)) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1));
							break;
						}
						amp = Integer.parseInt(args[1]);
						player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, amp));
						break;
					case "water_breathing":
						if (!(args.length == 2)) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 1));
							break;
						}
						amp = Integer.parseInt(args[1]);
						player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, amp));
						break;
					case "weakness":
						if (!(args.length == 2)) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE, 1));
							break;
						}
						amp = Integer.parseInt(args[1]);
						player.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, Integer.MAX_VALUE, amp));
						break;
					case "wither":
						if (!(args.length == 2)) {
							player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, Integer.MAX_VALUE, 1));
							break;
						}
						amp = Integer.parseInt(args[1]);
						player.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, Integer.MAX_VALUE, amp));
						break;
					default:
					break;
					}
				}
			}
		}
		//END OF INFINITE EFFECT
		//BEGIN CLEAR EFFECT
		if (cmd.getName().equalsIgnoreCase("iclear")) {
			if (!(player.hasPermission("crafticity.iclear"))) {
				player.sendMessage(RED + "You do not have permission to use " + DARK_AQUA + "iClear" + RED + "!");
			} else {
				for(PotionEffect effect : player.getActivePotionEffects())
				{
					player.removePotionEffect(effect.getType());
				}
			}
		}
		//END CLEAR EFFECT
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
		public void onInteractEvent(PlayerInteractEvent e) {
			Player p = e.getPlayer();
			if (!(e.getAction() == Action.RIGHT_CLICK_AIR)) return;
				if (e.getItem().getType() == Material.DIAMOND) {
					if (e.getItem().getItemMeta().equals(diaMeta)) {
						inv = Bukkit.getServer().createInventory(null, 9, DARK_AQUA + "AdminGem");
						aMeta.setDisplayName(RED + "Admin");
						admin.setItemMeta(aMeta);
						inv.setItem(2, admin);
						mMeta.setDisplayName(GREEN + "Mod");
						mod.setItemMeta(mMeta);
						inv.setItem(6, mod);
						p.openInventory(inv);
					}
				}
		}
		@EventHandler
		public void InventoryClick(InventoryClickEvent e) {
			//GAMEMODE INVENTORY
			gm = Bukkit.getServer().createInventory(null, 9, DARK_AQUA + "Gamemode");
			ItemStack creative = new ItemStack(Material.LAPIS_BLOCK);
			ItemMeta creativeMeta = creative.getItemMeta();
			creativeMeta.setDisplayName(RED + "Creative");
			creative.setItemMeta(creativeMeta);
			ItemStack adventure = new ItemStack(Material.REDSTONE_BLOCK);
			ItemMeta adventureMeta = adventure.getItemMeta();
			adventureMeta.setDisplayName(RED + "Adventure");
			adventure.setItemMeta(adventureMeta);
			ItemStack survival = new ItemStack(Material.EMERALD_BLOCK);
			ItemMeta survivalMeta = survival.getItemMeta();
			survivalMeta.setDisplayName(RED + "Survival");
			survival.setItemMeta(survivalMeta);
			ItemStack back = new ItemStack(Material.ARROW);
			ItemMeta backMeta = back.getItemMeta();
			backMeta.setDisplayName(BLUE + "Go Back");
			back.setItemMeta(backMeta);
			gm.setItem(0, creative);
			gm.setItem(3, adventure);
			gm.setItem(6, survival);
			gm.setItem(8, back);
			//END OF GAMEMODE INVENTORY
			
			ItemStack aCreative = new ItemStack(Material.GRASS);
			ItemMeta aMetaC = aCreative.getItemMeta();
			aMetaC.setDisplayName(GREEN + "Change your gamemodes!");
			aCreative.setItemMeta(aMetaC);
			
			ItemStack aTime = new ItemStack(Material.WATCH);
			ItemMeta aTMeta = aTime.getItemMeta();
			aTMeta.setDisplayName(GREEN + "Change the time!");
			aTime.setItemMeta(aTMeta);
			
			ItemStack aSkull = new ItemStack(Material.SKULL_ITEM);
			ItemMeta aSMeta = aSkull.getItemMeta();
			aSMeta.setDisplayName(GREEN + "Players");
			aSkull.setItemMeta(aSMeta);
			
			ItemStack aStop = new ItemStack(Material.REDSTONE_BLOCK);
			ItemMeta aStopM = aStop.getItemMeta();
			aStopM.setDisplayName(RED + "" + BOLD + "EMERGANCY STOP!");
			aStop.setItemMeta(aStopM);
			
			Player p = (Player) e.getWhoClicked();
			
			md = Bukkit.getServer().createInventory(null, 36, GREEN + "Mod Section");
			adminInv = Bukkit.getServer().createInventory(null, 36, RED + "Admin Section");
			
			if (e.getInventory().equals(inv)) {
			if (e.getCurrentItem().getItemMeta().equals(aMeta)) {
				if (p.hasPermission("admingem.admin")) {
					//Beginning of the Admin inventory
					adminInv.setItem(10, aCreative);
					adminInv.setItem(13, aTime);
					adminInv.setItem(16, aSkull);
					adminInv.setItem(31, aStop);
					adminInv.setItem(28, back);
					p.openInventory(adminInv);
					}
				}
				if (e.getCurrentItem().getItemMeta().equals(mMeta)) {
					md = Bukkit.getServer().createInventory(null, 36, GREEN + "Mod Section");
					p.openInventory(md);
				}
			}
			if (e.getInventory().equals(adminInv)) {
				if (e.getCurrentItem().getItemMeta().equals(aMetaC)) {
					e.setCancelled(true);
					p.openInventory(gm);
				}
			}
		}
		
			/*if (e.getInventory().equals(ad)) {
				if (e.getCurrentItem().getItemMeta().equals(aCMeta)) {
					e.getWhoClicked().openInventory(gm);
				}
			}
			
			if (e.getInventory().equals(gm)) {
			if(e.getCurrentItem().getItemMeta().equals(creativeMeta)) {
				e.getWhoClicked().setGameMode(GameMode.CREATIVE);
			}
			if (e.getCurrentItem().getItemMeta().equals(adventureMeta)) {
				e.getWhoClicked().setGameMode(GameMode.ADVENTURE);
			}
			if (e.getCurrentItem().getItemMeta().equals(survivalMeta)) {
				e.getWhoClicked().setGameMode(GameMode.SURVIVAL);
				}
			} */
					
					//Beginning of the Mod inventory

	
			
		
			
		
		@EventHandler
		public void onPlayerJoin(PlayerJoinEvent e) {
			Player p = e.getPlayer();
			
			if (p.hasPlayedBefore()) {
				p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 10, 1);
				
			} else {
				p.playSound(p.getLocation(), Sound.NOTE_PLING, 10, 1);
				bm.addPage(DARK_AQUA + "Crafticity \n" + "\n"+ DARK_GREEN + "Server Rules: \n" + BLUE + " 1. No Advertising \n" + DARK_GRAY + "(Will result in ban or mute) \n" + BLUE + " 2. No impersonating a staff member\n" + DARK_GRAY + "(Will result in mute or ban)\n" + BLUE + " 3. Keep swearing to a minimal\n" + DARK_GRAY + "(Will result in mute)\n", BLUE + " 4. No Sexual, Racial, Rude comments towards anyone\n" + DARK_GRAY + "(Will result in mute)\n" + BLUE + " 5. DO NOT disrespect staff\n" + DARK_GRAY + "(Will result in ban or mute)" + BLUE + " 6. Do spam or use all caps\n" + DARK_GRAY + "(Will result in mute)",
						DARK_GREEN + "Server Store: \n" + DARK_AQUA + "Type /shop in-game! \n" + BLUE + "Welcome to the Crafticity shop! All details of the items are in the shop!\n" + DARK_GREEN + "Ranks: \n" + DARK_GRAY + "[Coal]" + BLUE + "- $5\n" + GRAY + "[Iron]" + BLUE + "- $10\n" + GOLD + "[Gold]" + BLUE + "- $25\n" + RED + "[Redstone]" + BLUE + "- $35\n" + AQUA + "[Diamond]" + BLUE + "- $50",
						DARK_GREEN + "In-Game Money: \n" + "\n" + GOLD + "[10k]" + BLUE + " - $10\n" + GOLD + "[25k]" + BLUE + "- $20\n" + GOLD + "[50k]" + BLUE + "- $35\n" + GOLD + "[100k]" + BLUE + "- $50",
						DARK_GREEN + "Others: \n" + "\n" + RED + "[Unban]" + BLUE + "- $10\n" + RED + "[Custom Prefix]" + BLUE + "- $5");
					bm.setAuthor(DARK_AQUA + "Crafticity");
					bm.setTitle(GOLD + "Crafticity Server");
					bs.setItemMeta(bm);
				p.getInventory().setItem(8, bs);
			}
			
		}
		
		
		@EventHandler
		public void playerLeave(PlayerQuitEvent e) {
			Player p = e.getPlayer();
			playSmoke(p.getLocation());
		}
// END OF EVENT HANDLERS
	
}
