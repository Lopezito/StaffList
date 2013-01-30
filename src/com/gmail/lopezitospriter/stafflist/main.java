package com.gmail.lopezitospriter.stafflist;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.kitteh.tag.PlayerReceiveNameTagEvent;
import org.mcstats.BukkitMetrics;

public class main extends JavaPlugin {


	private Listener listener = new Listener(){
    @EventHandler
	public void onNameTag(PlayerReceiveNameTagEvent event) {
        int maxbucle = getConfig().getInt("TotalGroups");
        for (int i = 1; i <= maxbucle; i++)
        {
        	List<String> list = getConfig().getStringList("members" + i);
    	    String color = getConfig().getString("color" + i).toUpperCase();
            for (String l : list) {
            	if (event.getNamedPlayer().getName().equals(l)) {
	            event.setTag(ChatColor.valueOf(color)+l);
            	}
            }
        }
	}
	};
	
	private Logger log = Logger.getLogger("Minecraft"); 

    public void load() {
    	if (!getDataFolder().exists())
			getDataFolder().mkdir();
    	try{
			getConfig().load(new File(getDataFolder(), "config.yml"));
    	}catch(FileNotFoundException e){
			log.info("[" + getDescription().getName() + "] Creating configuration file.");
			try{
				new File(getDataFolder(), "config.yml").createNewFile();
				String[] membersex = {"Nick", "AnOtherNick"};
				String[] membersex2 = {"Nick", "AnOtherNick"};
				getConfig().set("ServerName", "MyServerName");
				getConfig().set("ColorTitle", "GOLD");
				getConfig().set("TotalGroups", 1);
				getConfig().set("group1", "test");
				getConfig().set("prefix1", "test");
				getConfig().set("color1", "RED");
				getConfig().set("members1", membersex);
				getConfig().set("group2", "test2");
				getConfig().set("prefix2", "test2");
				getConfig().set("color2", "GREEN");
				getConfig().set("members2", membersex2);
				try{
					getConfig().save(new File(getDataFolder(), "config.yml"));
					load();
				}catch (IOException e2){
					e2.printStackTrace();
				}
			}catch(Exception e1){
				e1.printStackTrace();
			}
    	}catch (IOException e){
			log.warning("[" + getDescription().getName() + "] Cannot load configuration: " + e);
		}catch (InvalidConfigurationException e){
			log.warning("[" + getDescription().getName() + "] Cannot read configuration: " + e);
		}
    }
    
	
	@Override
	public void onEnable(){
		log.info("[" + getDescription().getName() + "] v" + getDescription().getVersion() + " is enabled.");
		getServer().getPluginManager().registerEvents(listener, this);
	    try {
	        BukkitMetrics metrics = new BukkitMetrics(this);
	        metrics.start();
	    } catch (IOException e) {
	        // Failed to submit the stats :-(
	    }
	    load();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args){
	if(commandLabel.equalsIgnoreCase("staff")) {
        sender.sendMessage(ChatColor.valueOf(getConfig().getString("ColorTitle")) + "-Staff de " + getConfig().getString("ServerName") + "-");
        int maxbucle = getConfig().getInt("TotalGroups");
        
        for (int i = 1; i <= maxbucle; i++)
        {
        	List<String> list = getConfig().getStringList("members" + i);
            for (String l : list) {
            String name = l.toString();
    	    Player player = (Bukkit.getServer().getPlayer(name));
    	    String prefix = getConfig().getString("prefix" + i);
    	    String color = getConfig().getString("color" + i).toUpperCase();
            if (player == null) { 
    	    	sender.sendMessage(ChatColor.valueOf(color) + "[" + prefix + "]" + name + ChatColor.RED + " - Offline");
            }
            else {
        	    sender.sendMessage(ChatColor.valueOf(color) + "[" + prefix + "]" + name + ChatColor.GREEN +" - Online");
            }
        	}
        }
	}
    return false;
	}
	
	@Override
	public void onDisable(){
		log.info("[" + getDescription().getName() + "] v" + getDescription().getVersion() + " is disabled.");
	}

}
