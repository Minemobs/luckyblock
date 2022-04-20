package fr.minemobs.luckyblock;

import com.jeff_media.customblockdata.CustomBlockData;
import fr.minemobs.luckyblock.object.LBEvent;
import fr.minemobs.luckyblock.object.LBEvents;
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

import java.util.Random;

public class LuckyBlockListener implements Listener {

    public static final ItemStack LUCKY_BLOCK;
    public static final Random random = new Random();

    static {
        LUCKY_BLOCK = new ItemStack(Material.SPONGE);
        ItemMeta itemMeta = LUCKY_BLOCK.getItemMeta();
        itemMeta.setDisplayName(ChatColor.WHITE + "Lucky Block");
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        pdc.set(new NamespacedKey(LuckyBlock.getPlugin(LuckyBlock.class), "lucky_block"),  PersistentDataType.BYTE, (byte) 1);
        LUCKY_BLOCK.setItemMeta(itemMeta);
    }

    @EventHandler
    public void onLuckyBlockPlaced(BlockPlaceEvent event) {
        if(event.getItemInHand().getType() != Material.SPONGE || event.getItemInHand().getItemMeta() == null ||
                !event.getItemInHand().getItemMeta().getPersistentDataContainer().has(new NamespacedKey(JavaPlugin.getPlugin(LuckyBlock.class),
                        "lucky_block"),  PersistentDataType.BYTE)) return;
        PersistentDataContainer pdc = new CustomBlockData(event.getBlock(), JavaPlugin.getPlugin(LuckyBlock.class));
        pdc.set(new NamespacedKey(JavaPlugin.getPlugin(LuckyBlock.class), "lucky_block"), PersistentDataType.BYTE,
                event.getItemInHand().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(JavaPlugin.getPlugin(LuckyBlock.class),
                        "lucky_block"),  PersistentDataType.BYTE));
    }

    @EventHandler
    public void onLuckyBlockBreak(BlockBreakEvent event) {
        if(event.getBlock().getType() != Material.SPONGE) return;
        PersistentDataContainer pdc = new CustomBlockData(event.getBlock(), JavaPlugin.getPlugin(LuckyBlock.class));
        if(!pdc.has(new NamespacedKey(JavaPlugin.getPlugin(LuckyBlock.class), "lucky_block"),  PersistentDataType.BYTE)) return;
        event.setDropItems(false);
        LBEvents[] events = LuckyBlock.getEvents();
        LBEvent lbe = events[random.nextInt(events.length)].toEvent();
        lbe.execute(event.getPlayer(), event.getBlock().getLocation());
    }

    public static void removeRandomItems(PlayerInventory inv) {
        for (ItemStack content : inv.getContents()) {
            if(content == null) continue;
            if(random.nextBoolean()) {
                content.setAmount(content.getAmount() - random.nextInt(content.getAmount() + 1));
            }
        }
    }

    public static void removeItems(PlayerInventory inventory, int minCount, int maxCount) {
        for (ItemStack content : inventory.getContents()) {
            if(content == null) continue;
            if(random.nextBoolean()) {
                content.setAmount(content.getAmount() - random.nextInt(maxCount - minCount + 1) + minCount);
            }
        }
    }
}