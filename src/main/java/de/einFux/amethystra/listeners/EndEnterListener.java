package de.einFux.amethystra.listeners;

import de.einFux.amethystra.Main;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class EndEnterListener implements Listener {

    private final Main plugin;

    public EndEnterListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerEnterEnd(PlayerTeleportEvent event) {
        Player player = event.getPlayer();

        // OPs können immer ins End
        if (player.isOp()) return;

        // Wenn "The Next Generation" erreicht wurde -> keine Restriktionen mehr
        if (plugin.isNextGenerationReached()) return;

        if (event.getTo().getWorld().getEnvironment() == World.Environment.THE_END) {
            // Prüfen, ob der Spieler bereits das "The End?"-Advancement hat
            if (hasAdvancement(player, "end/root")) {
                event.setCancelled(true);
                player.sendMessage("§cDu kannst nur einmal ins End!");
                return;
            }

            // Sound & Titel für Spieler
            player.sendTitle("§c§lBESIEGE DEN ENDERDRACHEN", "§7Kämpft um das EI!", 10, 60, 10);
            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1.0f, 1.0f);
        }
    }

    @EventHandler
    public void onPlayerAdvancementDone(PlayerAdvancementDoneEvent event) {
        Player player = event.getPlayer();

        // Nur wenn das Event aktiv ist
        if (!plugin.isNextGenerationReached() && event.getAdvancement().getKey().getKey().equals("end/dragon_egg")) {
            plugin.setNextGenerationReached(true);

            Bukkit.broadcastMessage(ChatColor.GREEN + "Das Dragon Egg wurde aufgesammelt, das End ist jetzt für alle frei!");

            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendTitle("§aENDERDRACHE BESIEGT!", "§e" + player.getName() + " hat das Drachenei!", 10, 80, 20);
                p.playSound(p.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.0f, 1.0f);
            }
        }
    }

    // Prüft, ob ein Spieler ein bestimmtes Advancement hat
    private boolean hasAdvancement(Player player, String advancementKey) {
        Advancement advancement = Bukkit.getAdvancement(NamespacedKey.minecraft(advancementKey));
        if (advancement == null) return false;
        AdvancementProgress progress = player.getAdvancementProgress(advancement);
        return progress.isDone();
    }
}
