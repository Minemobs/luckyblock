package fr.minemobs.luckyblock.object;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public record Explosion(int radius, boolean breakBlocks, boolean burnBlocks) implements LBEvent {

    @Override
    public void execute(Player player, Location location) {
        location.getWorld().createExplosion(location, radius, breakBlocks, burnBlocks);
    }
}
