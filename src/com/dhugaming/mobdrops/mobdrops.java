package com.dhugaming.mobdrops;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public class mobdrops extends JavaPlugin implements Listener {
    HashMap<String, String> eggID = new HashMap();
    String rmdPrefix = ChatColor.DARK_GRAY + "[RMD]: " + ChatColor.AQUA;
    String version = "0.0.9";
    public void populateIDs() {
        eggID.put("Creeper", "50");
        eggID.put("Skeleton", "51");
        eggID.put("Spider", "52");
        eggID.put("Zombie", "54");
        eggID.put("Slime", "55");
        eggID.put("Ghast", "56");
        eggID.put("Zombie Pigman", "57");
        eggID.put("Enderman", "58");
        eggID.put("Cave Spider", "59");
        eggID.put("Silverfish", "60");
        eggID.put("Blaze", "61");
        eggID.put("Magma Cube", "62");
        eggID.put("Bat", "65");
        eggID.put("Witch", "66");
        eggID.put("Endermite", "67");
        eggID.put("Guardian", "68");
        eggID.put("Shulker", "69");
        eggID.put("Pig", "90");
        eggID.put("Sheep", "91");
        eggID.put("Cow", "92");
        eggID.put("Chicken", "93");
        eggID.put("Squid", "94");
        eggID.put("Wolf", "95");
        eggID.put("Mooshroom", "96");
        eggID.put("Ocelot", "98");
        eggID.put("Horse", "100");
        eggID.put("Rabbit", "101");
        eggID.put("Villager", "120");
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("rmd")) {
            sender.sendMessage(rmdPrefix + "RMD v" + version + " by DHU Gaming.");
            return true;
        }
        return false;
    }
    public boolean percentChance(double percent) {
        double d = Math.random();
        if (d <= percent / 100) {
            return true;
        }
        return false;
    }
    public boolean dropHeadChance() {
        if (percentChance(1)) {
            return true;
        }
        return false;
    }
    public boolean dropEggChance() {
        if (percentChance(0.5)) {
            return true;
        }
        return false;
    }
    public String parseName(String name) {
        int i = name.lastIndexOf("]");
        name = name.substring(i + 1);
        return name;
    }
    public void givePlayerHead(Player toP, String from) {
        String to = toP.getDisplayName();
        if (percentChance(20)) {
            to = parseName(to);
            from = parseName(from);
            String command = "give " + to + " 397:3 1 {SkullOwner:" + from + "}";
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            getLogger().info(command);
            toP.sendMessage(rmdPrefix + "You cut off " + to + "'s head!");
            getLogger().info(to);
        }
    }
    public void entityDeath(EntityDeathEvent event, Player killer) {
        if (!(event.getEntity() instanceof Player)) {
            if (dropHeadChance()) {
                String eName = event.getEntity().getName();
                if (eName.equals("Zombie Pigman")) {
                    eName = "PigZombie";
                }
                if (eName.equals("Cave Spider")) {
                    eName = "CaveSpider";
                }
                if (eName.equals("Magma Cube")) {
                    eName = "LavaSlime";
                }
                if (eName.equals("Mooshroom")) {
                    eName = "MushroomCow";
                }
                String playerName = killer.getDisplayName();
                int ind = playerName.lastIndexOf("]");
                playerName = playerName.substring(ind + 1, playerName.length() - 2);
                killer.sendMessage(rmdPrefix + "You cut off the " + event.getEntity().getName().toLowerCase() + "'s head!");
                String command = "give " + playerName + " 397:3 1 player:MHF_" + eName;
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            }
            if (dropEggChance()) {
                String id = eggID.get(event.getEntity().getName());
                String playerName = killer.getDisplayName();
                int ind = playerName.lastIndexOf("]");
                playerName = playerName.substring(ind + 1, playerName.length() - 2);
                killer.sendMessage(rmdPrefix + "You hit the " + event.getEntity().getName().toLowerCase() + " so hard, it dropped an egg.");
                String command = "give " + playerName + " 383:" + id + " 1";
                getLogger().info(command);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
            }
        }
    }
    @Override
    public void onDisable() {

    }

    @Override
    public void onEnable() {
        new DeathListener(this);
        eggID.clear();
        populateIDs();
    }
}
