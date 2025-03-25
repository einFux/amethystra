package de.einFux.amethystra;

import de.einFux.amethystra.commands.EndEventStartCommand;
import de.einFux.amethystra.commands.ToggleEndCommand;
import de.einFux.amethystra.commands.ServerRestartCommand;
import de.einFux.amethystra.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

    private boolean endEnabled = true;
    private boolean nextGenerationReached = false;
    private int heightLimit; // Variable für die maximale Bauhöhe

    @Override
    public void onEnable() {
        saveDefaultConfig();
        endEnabled = getConfig().getBoolean("end-enabled", true);
        heightLimit = getConfig().getInt("height-limit", 150); // Wert aus der config.yml laden

        // Commands registrieren
        getCommand("toggleend").setExecutor(new ToggleEndCommand(this));
        getCommand("endeventstart").setExecutor(new EndEventStartCommand(this));
        getCommand("serverrestart").setExecutor(new ServerRestartCommand(this));

        // Event-Listener registrieren
        Bukkit.getPluginManager().registerEvents(new EndPortalListener(this), this);
        Bukkit.getPluginManager().registerEvents(new EndEnterListener(this), this);
        Bukkit.getPluginManager().registerEvents(new EndBuildLimitListener(this), this);
        Bukkit.getPluginManager().registerEvents(new EndItemBlockerListener(), this);
        Bukkit.getPluginManager().registerEvents(this, this); // Für Advancement-Erkennung
    }

    public boolean isEndEnabled() {
        return endEnabled;
    }

    public void setEndEnabled(boolean enabled) {
        this.endEnabled = enabled;
        getConfig().set("end-enabled", enabled);
        saveConfig();
    }

    public boolean isNextGenerationReached() {
        return nextGenerationReached;
    }

    public void setNextGenerationReached(boolean reached) {
        this.nextGenerationReached = reached;
    }

    public int getHeightLimit() {
        return heightLimit; // Rückgabe der maximalen Bauhöhe
    }

    public void startEndEvent() {
        nextGenerationReached = false;
        endEnabled = true;
        getConfig().set("end-enabled", true);
        saveConfig();

        Bukkit.broadcastMessage("§cDas End-Event hat begonnen! Das End ist jetzt geöffnet!");
    }
}
