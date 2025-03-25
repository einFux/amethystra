package de.einFux.amethystra.listeners;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class EndItemBlockerListener implements Listener {

    @EventHandler
    public void onItemUse(PlayerInteractEvent event) {
        if (event.getPlayer().isOp()) return;
        if (event.getItem() != null) {
            Material itemType = event.getItem().getType();
            World world = event.getPlayer().getWorld();

            // Blockiere Items nur im End (aber keine Enderperlen!)
            if (world.getEnvironment() == World.Environment.THE_END &&
                    (itemType == Material.END_CRYSTAL ||
                            itemType == Material.RESPAWN_ANCHOR ||
                            isBed(itemType))) {

                event.setCancelled(true);
                event.getPlayer().sendMessage("§cDieses Item ist im End verboten!");
            }
        }
    }

    private boolean isBed(Material material) {
        return material.name().endsWith("_BED");
    }
}
