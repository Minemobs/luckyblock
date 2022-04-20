package fr.minemobs.luckyblock;

import fr.minemobs.luckyblock.object.LBEvents;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ReloadConfigCommand implements CommandExecutor {

    private final LuckyBlock lb;

    public ReloadConfigCommand(LuckyBlock lb) {
        this.lb = lb;
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if(!(sender instanceof Player)) return false;
        if (!lb.getConfigFile().exists()) lb.saveResource("config.json", false);
        try {
            lb.setEvents(lb.gson.fromJson(new FileReader(lb.getConfigFile()), LBEvents[].class));
            sender.sendMessage("§aConfig reloaded");
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
