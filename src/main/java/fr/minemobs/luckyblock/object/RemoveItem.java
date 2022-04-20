package fr.minemobs.luckyblock.object;

import com.google.gson.annotations.SerializedName;
import fr.minemobs.luckyblock.LuckyBlockListener;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class RemoveItem implements LBEvent {

    private final boolean random;
    @SerializedName("min_count")
    private final int minCount;
    @SerializedName("max_count")
    private final int maxCount;

    public RemoveItem(boolean random, int minCount, int maxCount) {
        this.random = random;
        this.minCount = minCount;
        this.maxCount = maxCount;
    }

    @Override
    public void execute(Player player, Location loc) {
        if(random) {
            LuckyBlockListener.removeRandomItems(player.getInventory());
        } else {
            LuckyBlockListener.removeItems(player.getInventory(), minCount, maxCount);
        }
    }
}
