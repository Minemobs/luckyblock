package fr.minemobs.luckyblock.object;

import com.google.gson.annotations.SerializedName;

public class LBEvents {

    public enum Events {
        EXPLOSION,
        SPAWN_ITEM,
        REMOVE_ITEM,
        SPAWN_ENTITY
    }

    private final String type, item, entity;
    private final int radius, distance;
    @SerializedName("min_count")
    private final int minCount;
    @SerializedName("max_count")
    private final int maxCount;
    @SerializedName("break_blocks")
    private final boolean breakBlocks;
    @SerializedName("burn_blocks")
    private final boolean burnBlocks;
    private final boolean random;

    public LBEvents(String type, String item, String entity, int radius, int distance, int minCount, int maxCount, boolean breakBlocks, boolean burnBlocks, boolean random) {
        this.type = type;
        this.item = item;
        this.entity = entity;
        this.radius = radius;
        this.distance = distance;
        this.minCount = minCount;
        this.maxCount = maxCount;
        this.breakBlocks = breakBlocks;
        this.burnBlocks = burnBlocks;
        this.random = random;
    }

    public LBEvent toEvent() {
        try {
            return switch (toType()) {
                case EXPLOSION -> new Explosion(this.radius, this.breakBlocks, this.burnBlocks);
                case SPAWN_ITEM -> new SpawnItem(this.item, this.minCount, this.maxCount);
                case REMOVE_ITEM -> new RemoveItem(this.random, this.minCount, this.maxCount);
                case SPAWN_ENTITY -> new SpawnEntity(this.entity, this.minCount, this.maxCount, this.distance);
            };
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    public Events toType() {
        return Events.valueOf(type.toUpperCase());
    }
}