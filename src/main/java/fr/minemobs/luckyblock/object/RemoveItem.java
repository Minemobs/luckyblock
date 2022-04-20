package fr.minemobs.luckyblock.object;

import fr.minemobs.luckyblock.JsonRegexConvertor;
import fr.minemobs.luckyblock.LuckyBlockListener;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RemoveItem implements LBEvent {

    private final List<Material> items;
    private final int minCount;
    private final int maxCount;
    private final boolean allItems;

    public RemoveItem(String[] itemTypes, int minCount, int maxCount, boolean allItems) {
        this.items = new ArrayList<>();
        for (int i = 0; i < itemTypes.length; i++) {
            int finalI = i;
            var mat = Arrays.stream(Material.values()).filter(m -> JsonRegexConvertor.toMatcher(itemTypes[finalI], m.name()).matches()).toList();
            this.items.addAll(mat);
        }
        this.minCount = minCount;
        this.maxCount = maxCount;
        this.allItems = allItems;
    }

    @Override
    public void execute(Player player, Location loc) {
        PlayerInventory inv = player.getInventory();
        if(allItems) {
            for(Material item : items) {
                removeItem(inv, item);
            }
        } else {
            removeItem(inv, items.get(LuckyBlockListener.random.nextInt(items.size())));
        }
    }

    private void removeItem(PlayerInventory inv, Material mat) {
        int slot = inv.first(mat);
        if(slot == -1) return;
        inv.getItem(slot).setAmount(inv.getItem(slot).getAmount() - LuckyBlockListener.random.nextInt(minCount, maxCount));
    }
}
