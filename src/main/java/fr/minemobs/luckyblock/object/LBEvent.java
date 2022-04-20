package fr.minemobs.luckyblock.object;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface LBEvent {

    void execute(Player player, Location loc);

}
