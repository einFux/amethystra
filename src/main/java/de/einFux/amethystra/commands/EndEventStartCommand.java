package de.einFux.amethystra.commands;

import de.einFux.amethystra.Main;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EndEventStartCommand implements CommandExecutor {

    private final Main plugin;

    public EndEventStartCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) || sender.isOp()) {
            plugin.startEndEvent();
            Bukkit.broadcastMessage("§c⚔ Das End-Event hat begonnen! Besiegt den Enderdrachen! ⚔");

            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendTitle("§c⚔ BESIEGE DEN ENDERDRACHEN ⚔", "", 10, 60, 10);
                player.playSound(player.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1.0f, 1.0f);
            }
            return true;
        } else {
            sender.sendMessage("§cDu hast keine Berechtigung für diesen Befehl!");
            return true;
        }
    }
}
