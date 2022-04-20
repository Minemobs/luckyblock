package fr.minemobs.luckyblock.object;

import fr.minemobs.luckyblock.LuckyBlockListener;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class RandomRemoveItem implements LBEvent {

    @Override
    public void execute(Player player, Location loc) {
        LuckyBlockListener.removeRandomItems(player.getInventory());
    }
}
