package fr.minemobs.luckyblock;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.minemobs.luckyblock.object.LBEvents;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public final class LuckyBlock extends JavaPlugin {

    public final Gson gson = new GsonBuilder().create();
    private LBEvents[] events;
    private File configFile;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.addRecipe(new ShapedRecipe(new NamespacedKey(this, "lucky_block"), LuckyBlockListener.LUCKY_BLOCK.clone())
                .shape("GGG", "GDG", "GGG").setIngredient('G', Material.GOLD_INGOT).setIngredient('D', Material.DROPPER));
        configFile = new File(getDataFolder(), "config.json");
        if (!configFile.exists()) saveResource("config.json", false);
        try {
            events = gson.fromJson(new FileReader(configFile), LBEvents[].class);
        } catch (FileNotFoundException e) {
            getLogger().severe(() -> "Could not find config.json " + e.getMessage());
            e.printStackTrace();
            events = new LBEvents[0];
        }
        Bukkit.getPluginManager().registerEvents(new LuckyBlockListener(this), this);
        this.getCommand("reloadconfig").setExecutor(new ReloadConfigCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public LBEvents[] getEvents() {
        return events;
    }

    void setEvents(LBEvents[] events) {
        this.events = events;
    }

    public File getConfigFile() {
        return configFile;
    }
}