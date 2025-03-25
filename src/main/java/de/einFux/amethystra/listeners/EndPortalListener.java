package de.einFux.amethystra.listeners;

import de.einFux.amethystra.Main;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class EndPortalListener implements Listener {

    private final Main plugin;

    public EndPortalListener(Main plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent event) {
        Player player = event.getPlayer();

        // OPs haben immer einen Bypass
        if (player.isOp()) return;

        // End ist mit /toggleend deaktiviert -> Niemand kann rein
        if (!plugin.isEndEnabled()) {
            event.setCancelled(true);
            player.sendMessage("§cDas End ist momentan deaktiviert!");
            return;
        }

        // Wenn das Event aktiv ist und der Spieler schon im End war, darf er nicht nochmal rein
        if (!plugin.isNextGenerationReached() && hasAdvancement(player, "end/root")) {
            event.setCancelled(true);
            player.sendMessage("§cDu kannst nur einmal ins End!");
        }
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();

        // OPs haben immer einen Bypass
        if (player.isOp()) return;

        // Spieler versucht, sich mit einer Enderperle ins End zu teleportieren
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL &&
                event.getTo().getWorld().getEnvironment() == World.Environment.THE_END) {

            boolean endDeaktiviert = !plugin.isEndEnabled(); // End ist geschlossen
            boolean eventAktiv = !plugin.isNextGenerationReached(); // Event ist aktiv
            boolean warSchonImEnd = hasAdvancement(player, "end/root"); // Spieler hat "The End?" Advancement

            // Blockiere die Teleportation, wenn das End deaktiviert ist oder das Event aktiv ist und der Spieler schon mal im End war
            if (endDeaktiviert || (eventAktiv && warSchonImEnd)) {
                event.setCancelled(true);
                player.sendMessage("§cDu kannst dich nicht mit einer Enderperle ins End teleportieren!");
            }
        }
    }

    @EventHandler
    public void onAdvancementDone(PlayerAdvancementDoneEvent event) {
        Player player = event.getPlayer();
        String key = event.getAdvancement().getKey().getKey();

        if (key.equals("end/dragon_egg")) {  // "The Next Generation"-Advancement
            plugin.setNextGenerationReached(true);
            Bukkit.broadcastMessage("§6" + player.getName() + " hat The Next Generation erreicht!");
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.sendTitle("§6" + player.getName(), "§ehat The Next Generation erreicht!", 10, 70, 20);
            }
        }
    }

    private boolean hasAdvancement(Player player, String advancementKey) {
        Advancement advancement = Bukkit.getAdvancement(org.bukkit.NamespacedKey.minecraft(advancementKey));
        if (advancement == null) return false;
        AdvancementProgress progress = player.getAdvancementProgress(advancement);
        return progress.isDone();
    }
}
