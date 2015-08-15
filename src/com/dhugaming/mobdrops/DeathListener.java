package com.dhugaming.mobdrops;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class DeathListener implements Listener {
    public mobdrops md;

    public DeathListener(mobdrops pl) {
        md = pl;
        pl.getServer().getPluginManager().registerEvents(this, pl);
    }
    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity() instanceof Player) {
            md.givePlayerHead(e.getEntity().getKiller(), e.getEntity().getName());
        }
        if (e.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent && e.getEntity().getKiller() instanceof Player) {
            Player killer = e.getEntity().getKiller();
            md.entityDeath(e, killer);
        }
    }
}
