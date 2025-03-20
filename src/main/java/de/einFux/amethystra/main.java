package de.einFux.amethystra;

import de.einFux.amethystra.commands.EventStartCommand;
import de.einFux.amethystra.listeners.EndEnterListener;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {
    private boolean eventActive = false;  // Status, ob das Event aktiv ist

    @Override
    public void onEnable() {
        saveDefaultConfig();

        // Listener registrieren
        getServer().getPluginManager().registerEvents(new EndEnterListener(this), this);

        // Befehl registrieren
        getCommand("event").setExecutor(new EventStartCommand(this));
    }

    public boolean isEventActive() {
        return eventActive;
    }

    public void setEventActive(boolean active) {
        this.eventActive = active;
        getConfig().set("event-active", active);
        saveConfig();
    }
}
