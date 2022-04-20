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
    private static LBEvents[] events;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.addRecipe(new ShapedRecipe(new NamespacedKey(this, "lucky_block"), LuckyBlockListener.LUCKY_BLOCK)
                .shape("GGG", "GDG", "GGG").setIngredient('G', Material.GOLD_INGOT).setIngredient('D', Material.DROPPER));
        Bukkit.getPluginManager().registerEvents(new LuckyBlockListener(), this);
        File configFile = new File(getDataFolder(), "config.json");
        if (!configFile.exists()) saveResource("config.json", false);
        try {
            events = gson.fromJson(new FileReader(configFile), LBEvents[].class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static LBEvents[] getEvents() {
        return events;
    }
}
