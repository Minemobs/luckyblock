package fr.minemobs.luckyblock.object;

import fr.minemobs.luckyblock.LuckyBlockListener;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class SpawnEntity implements LBEvent {

    private final EntityType[] types;
    private final int minCount;
    private final int maxCount;
    private final int distance;

    public SpawnEntity(String[] types, int minCount, int maxCount, int distance) {
        this.types = new EntityType[types.length];
        for (int i = 0; i < types.length; i++) {
            this.types[i] = EntityType.valueOf(types[i].toUpperCase());
        }
        this.minCount = minCount;
        this.maxCount = maxCount;
        this.distance = distance;
    }

    @Override
    public void execute(Player player, Location blockLoc) {
        for (int i = 0; i < LuckyBlockListener.random.nextInt(minCount, maxCount + 1); i++) {
            Location loc = blockLoc.clone().add(player.getEyeLocation().getDirection().multiply(distance));
            if(loc.getWorld().getBlockAt(loc).getType() == Material.AIR) loc = blockLoc;
            blockLoc.getWorld().spawnEntity(loc, types[LuckyBlockListener.random.nextInt(types.length)]);
        }
    }
}
