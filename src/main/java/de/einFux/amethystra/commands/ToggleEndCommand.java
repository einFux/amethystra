package de.einFux.amethystra.commands;

import de.einFux.amethystra.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleEndCommand implements CommandExecutor {

    private final Main plugin;

    public ToggleEndCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) || sender.isOp()) {
            plugin.setEndEnabled(!plugin.isEndEnabled());

            String status = plugin.isEndEnabled() ? "§a✅ END GEÖFFNET ✅" : "§c❌ END GESCHLOSSEN ❌";

            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendTitle(status, "§7Geh durch ein Endportal!", 10, 40, 10);
                player.playSound(player.getLocation(), Sound.BLOCK_END_PORTAL_SPAWN, 1.0f, 1.0f);
            }

            sender.sendMessage("§aDas End ist jetzt " + (plugin.isEndEnabled() ? "§aaktiviert" : "§cdeaktiviert") + "!");
            return true;
        } else {
            sender.sendMessage("§cDu hast keine Berechtigung für diesen Befehl!");
            return true;
        }
    }
}
