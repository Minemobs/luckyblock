package fr.minemobs.luckyblock.object;

import fr.minemobs.luckyblock.LuckyBlockListener;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SpawnItem implements LBEvent {

    private final String item;
    private final int minCount;
    private final int maxCount;

    public SpawnItem(String item, int minCount, int maxCount) {
        this.item = item;
        this.minCount = minCount;
        this.maxCount = maxCount;
    }

    @Override
    public void execute(Player player, Location loc) {
        loc.getWorld().dropItem(loc, new ItemStack(Material.valueOf(item.toUpperCase()), LuckyBlockListener.random.nextInt(minCount, maxCount + 1)));
    }
}
