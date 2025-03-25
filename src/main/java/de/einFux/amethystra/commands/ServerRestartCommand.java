package de.einFux.amethystra.commands;

import de.einFux.amethystra.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ServerRestartCommand implements CommandExecutor {

    private final Main plugin;

    public ServerRestartCommand(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) || sender.isOp()) {
            startRestartCountdown();
            return true;
        } else {
            sender.sendMessage(ChatColor.RED + "Du hast keine Berechtigung für diesen Befehl!");
            return true;
        }
    }

    private void startRestartCountdown() {
        new BukkitRunnable() {
            int timeLeft = 60;

            @Override
            public void run() {
                if (timeLeft > 0) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendActionBar(ChatColor.RED + "🔄 Server-Neustart in " + timeLeft + " Sekunden!");
                    }
                    timeLeft--;
                } else {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "server lobby " + player.getName());
                    }

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "restart");
                        }
                    }.runTaskLater(plugin, 100);

                    cancel();
                }
            }
        }.runTaskTimer(plugin, 0, 20);
    }
}
