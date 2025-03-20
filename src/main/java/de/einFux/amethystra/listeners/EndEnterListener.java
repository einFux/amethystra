package de.einFux.amethystra.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import de.einFux.amethystra.main;

import java.util.HashSet;
import java.util.UUID;

public class EndEnterListener implements Listener {
    private final main plugin;
    private final HashSet<UUID> enteredEnd = new HashSet<>();

    public EndEnterListener(main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEndEnter(PlayerPortalEvent event) {
        Player player = event.getPlayer();
        World end = Bukkit.getWorld("world_the_end");

        // Prüfen, ob das Event aktiv ist
        if (!plugin.isEventActive()) {
            event.setCancelled(true);
            player.sendMessage("§cDas End ist aktuell nicht zugänglich!");
            return;
        }

        // Prüfen, ob der Spieler schon einmal im End war
        if (event.getTo().getWorld().equals(end)) {
            if (!player.hasPermission("endevent.admin") && enteredEnd.contains(player.getUniqueId())) {
                event.setCancelled(true);
                player.sendMessage("§cDu kannst das End nur einmal betreten!");
                player.teleport(player.getBedSpawnLocation() != null ? player.getBedSpawnLocation() : player.getWorld().getSpawnLocation());
            } else {
                enteredEnd.add(player.getUniqueId());
                player.sendTitle("§eViel Glück im End!", "", 10, 70, 20);
                player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.0f, 1.0f);
            }
        }
    }
}
