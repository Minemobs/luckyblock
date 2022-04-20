package fr.minemobs.luckyblock.object;

import com.google.gson.annotations.SerializedName;
import fr.minemobs.luckyblock.LuckyBlock;
import org.bukkit.plugin.java.JavaPlugin;

public class LBEvents {

    public enum Events {
        EXPLOSION,
        SPAWN_ITEM,
        REMOVE_ITEM,
        RANDOM_REMOVE_ITEM,
        SPAWN_ENTITY
    }

    private final String type, item;
    @SerializedName("item_types")
    private final String[] itemTypes;
    private final String[] entities;
    private final int radius, distance;
    @SerializedName("min_count")
    private final int minCount;
    @SerializedName("max_count")
    private final int maxCount;
    @SerializedName("break_blocks")
    private final boolean breakBlocks;
    @SerializedName("burn_blocks")
    private final boolean burnBlocks;
    @SerializedName("all_items")
    private final boolean allItems;

    public LBEvents(String type, String item, String[] entities, String[] itemTypes, int radius, int distance, int minCount, int maxCount, boolean breakBlocks,
                    boolean burnBlocks, boolean allItems) {
        this.type = type;
        this.item = item;
        this.entities = entities;
        this.itemTypes = itemTypes;
        this.radius = radius;
        this.distance = distance;
        this.minCount = minCount;
        this.maxCount = maxCount;
        this.breakBlocks = breakBlocks;
        this.burnBlocks = burnBlocks;
        this.allItems = allItems;
    }

    public LBEvent toEvent() {
        try {
            JavaPlugin.getPlugin(LuckyBlock.class).getLogger().info(() -> "Event : " + type);
            return switch (toType()) {
                case EXPLOSION -> new Explosion(this.radius, this.breakBlocks, this.burnBlocks);
                case SPAWN_ITEM -> new SpawnItem(this.item, this.minCount, this.maxCount);
                case RANDOM_REMOVE_ITEM -> new RandomRemoveItem();
                case REMOVE_ITEM -> new RemoveItem(this.itemTypes, this.minCount, this.maxCount, this.allItems);
                case SPAWN_ENTITY -> new SpawnEntity(this.entities, this.minCount, this.maxCount, this.distance);
            };
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public Events toType() {
        return Events.valueOf(type.toUpperCase());
    }
}