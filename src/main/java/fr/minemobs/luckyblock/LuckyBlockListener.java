package fr.minemobs.luckyblock;

import com.jeff_media.customblockdata.CustomBlockData;
import fr.minemobs.luckyblock.object.LBEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.Random;

public class LuckyBlockListener implements Listener {

    public static final ItemStack LUCKY_BLOCK;
    public static final Random random = new Random();
    private final LuckyBlock lb;
    private final NamespacedKey key;

    public LuckyBlockListener(LuckyBlock lb) {
        this.lb = lb;
        this.key = new NamespacedKey(lb, "lucky_block");
    }

    static {
        LUCKY_BLOCK = new ItemStack(Material.SPONGE);
        ItemMeta itemMeta = Objects.requireNonNull(LUCKY_BLOCK.getItemMeta());
        itemMeta.setDisplayName(ChatColor.WHITE + "Lucky Block");
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        pdc.set(new NamespacedKey(JavaPlugin.getPlugin(LuckyBlock.class), "lucky_block"),  PersistentDataType.BYTE, (byte) 1);
        LUCKY_BLOCK.setItemMeta(itemMeta);
    }

    @EventHandler
    public void onLuckyBlockPlaced(BlockPlaceEvent event) {
        if(event.getItemInHand().getType() != Material.SPONGE || event.getItemInHand().getItemMeta() == null ||
                !event.getItemInHand().getItemMeta().getPersistentDataContainer().has(key,  PersistentDataType.BYTE)) return;
        PersistentDataContainer pdc = new CustomBlockData(event.getBlock(), lb);
        //noinspection ConstantConditions
        pdc.set(key, PersistentDataType.BYTE, event.getItemInHand().getItemMeta().getPersistentDataContainer().get(key,  PersistentDataType.BYTE));
    }

    @EventHandler
    public void onLuckyBlockBreak(BlockBreakEvent event) {
        if(event.getBlock().getType() != Material.SPONGE) return;
        PersistentDataContainer pdc = new CustomBlockData(event.getBlock(), lb);
        if(!pdc.has(key,  PersistentDataType.BYTE)) return;
        event.setDropItems(false);
        LBEvent lbe = lb.getEvents()[random.nextInt(lb.getEvents().length)].toEvent();
        lbe.execute(event.getPlayer(), event.getBlock().getLocation());
        pdc.remove(key);
    }

    public static void removeRandomItems(PlayerInventory inv) {
        for (ItemStack content : inv.getContents()) {
            if(content == null) continue;
            if(random.nextInt(100) > 10) {
                content.setAmount(content.getAmount() - random.nextInt(content.getAmount() + 1));
            }
        }
    }

    public static void removeItems(PlayerInventory inventory, int minCount, int maxCount) {
        for (ItemStack content : inventory.getContents()) {
            if(content == null) continue;
            if(random.nextInt(100) > 10) {
                content.setAmount(content.getAmount() - random.nextInt(maxCount - minCount + 1) + minCount);
            }
        }
    }
}