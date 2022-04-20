package fr.minemobs.luckyblock.object;

import com.google.gson.annotations.SerializedName;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public record Explosion(int radius, @SerializedName("break_blocks") boolean breakBlocks, @SerializedName("burn_blocks") boolean burnBlocks) implements LBEvent {

    @Override
    public void execute(Player player, Location location) {
        location.getWorld().createExplosion(location, radius, breakBlocks, burnBlocks);
    }
}
