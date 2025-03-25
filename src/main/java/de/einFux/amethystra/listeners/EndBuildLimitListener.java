package de.einFux.amethystra.listeners;

import de.einFux.amethystra.Main;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class EndBuildLimitListener implements Listener {

    private final Main plugin;

    public EndBuildLimitListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getPlayer().isOp()) return;

        if (event.getBlock().getWorld().getEnvironment() == World.Environment.THE_END
                && event.getBlock().getY() > plugin.getHeightLimit()) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("§cDu kannst nicht höher als " + plugin.getHeightLimit() + " bauen!");
        }
    }
}
