package de.einFux.amethystra.commands;

import de.einFux.amethystra.main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EventStartCommand implements CommandExecutor {
    private final main plugin;

    public EventStartCommand(main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Nur Spieler können diesen Befehl benutzen.");
            return true;
        }

        if (!sender.hasPermission("endevent.admin")) {
            sender.sendMessage("§cDu hast keine Berechtigung, diesen Befehl auszuführen.");
            return true;
        }

        plugin.setEventActive(true);
        Bukkit.broadcastMessage("§eDas End ist jetzt geöffnet!");

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendTitle("§cBesiege den Enderdrachen!", "", 10, 70, 20);
            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1.0f, 1.0f);
        }

        return true;
    }
}
