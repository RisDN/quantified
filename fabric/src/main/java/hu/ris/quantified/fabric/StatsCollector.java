package hu.ris.quantified.fabric;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import hu.ris.quantified.common.maputils.SortMap;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;

public class StatsCollector {

    public static CompletableFuture<Map<String, Integer>> getStats(ServerPlayerEntity player, MinecraftServer server) {
        Map<String, Integer> stats = new HashMap<>();

        return CompletableFuture.supplyAsync(() -> {
            Quantified.log("Collecting stats for player: " + player.getName().getString());

            stats.putAll(getCustoms(player));
            stats.putAll(getMinedItems(player));
            stats.putAll(getCraftedItems(player));
            stats.putAll(getUsedItems(player));
            stats.putAll(getBrokenItems(player));
            stats.putAll(getPickedUpItems(player));
            stats.putAll(getDroppedItems(player));
            stats.putAll(getKilledByEntities(player));
            stats.putAll(getKilledEntities(player));

            return stats;
        });
    }

    /**
     * Returns a map of custom statistics for the player.
     * 
     * @return a map of custom statistics with their names as keys and the number of
     */
    private static Map<String, Integer> getCustoms(ServerPlayerEntity player) {
        Map<String, Integer> customs = new HashMap<>();

        for (Identifier statId : Registries.CUSTOM_STAT) {
            Stat<?> stat = Stats.CUSTOM.getOrCreateStat(statId);
            int value = player.getStatHandler().getStat(stat);
            String name = "custom;" + statId.toString();
            customs.put(name, value);
        }

        return SortMap.sortByValue(customs);
    }

    /**
     * /** Returns a map of mined items by the player.
     * 
     * @return
     */
    private static Map<String, Integer> getMinedItems(ServerPlayerEntity player) {
        Map<String, Integer> minedItems = new HashMap<String, Integer>();

        for (Block block : Registries.BLOCK) {
            int count = player.getStatHandler().getStat(Stats.MINED.getOrCreateStat(block));
            String itemName = Registries.BLOCK.getId(block).toString();
            minedItems.put("mined;" + itemName, count);
        }

        return SortMap.sortByValue(minedItems);
    }

    /**
     * Returns a map of crafted items by the player.
     * 
     * @returns a map of crafted items with their names as keys and the number of
     */
    private static Map<String, Integer> getCraftedItems(ServerPlayerEntity player) {
        Map<String, Integer> craftedItems = new HashMap<String, Integer>();

        for (Item item : Registries.ITEM) {
            int count = player.getStatHandler().getStat(Stats.CRAFTED.getOrCreateStat(item));
            String itemName = Registries.ITEM.getId(item).toString();
            craftedItems.put("crafted;" + itemName, count);
        }

        return SortMap.sortByValue(craftedItems);
    }

    /**
     * Returns a map of used items by the player.
     * 
     * @return
     */
    private static Map<String, Integer> getUsedItems(ServerPlayerEntity player) {
        Map<String, Integer> usedItems = new HashMap<String, Integer>();

        for (Item item : Registries.ITEM) {
            int count = player.getStatHandler().getStat(Stats.USED.getOrCreateStat(item));
            String itemName = Registries.ITEM.getId(item).toString();
            usedItems.put("used;" + itemName, count);
        }

        return SortMap.sortByValue(usedItems);
    }

    /**
     * Returns a map of broken items by the player.
     * 
     * @return
     * 
     */
    private static Map<String, Integer> getBrokenItems(ServerPlayerEntity player) {
        Map<String, Integer> brokenItems = new HashMap<String, Integer>();

        for (Item item : Registries.ITEM) {
            int count = player.getStatHandler().getStat(Stats.BROKEN.getOrCreateStat(item));
            String itemName = Registries.ITEM.getId(item).toString();
            brokenItems.put("broken;" + itemName, count);
        }

        return SortMap.sortByValue(brokenItems);
    }

    /**
     * Returns a map of items picked up by the player.
     * 
     * @return
     */
    private static Map<String, Integer> getPickedUpItems(ServerPlayerEntity player) {
        Map<String, Integer> pickedUpItems = new HashMap<String, Integer>();

        for (Item item : Registries.ITEM) {
            int count = player.getStatHandler().getStat(Stats.PICKED_UP.getOrCreateStat(item));
            String itemName = Registries.ITEM.getId(item).toString();
            pickedUpItems.put("picked;" + itemName, count);
        }

        return SortMap.sortByValue(pickedUpItems);
    }

    /**
     * Returns a map of items dropped by the player.
     * 
     * @return
     */
    private static Map<String, Integer> getDroppedItems(ServerPlayerEntity player) {
        Map<String, Integer> droppedItems = new HashMap<String, Integer>();

        for (Item item : Registries.ITEM) {
            int count = player.getStatHandler().getStat(Stats.DROPPED.getOrCreateStat(item));
            String itemName = Registries.ITEM.getId(item).toString();
            droppedItems.put("dropped;" + itemName, count);
        }

        return SortMap.sortByValue(droppedItems);
    }

    /**
     * Returns a map of entities killed by the player.
     * 
     * @return
     */
    private static Map<String, Integer> getKilledByEntities(ServerPlayerEntity player) {
        Map<String, Integer> killedByEntities = new HashMap<String, Integer>();

        for (EntityType<?> entityType : Registries.ENTITY_TYPE) {
            int count = player.getStatHandler().getStat(Stats.KILLED_BY.getOrCreateStat(entityType));
            String entityName = Registries.ENTITY_TYPE.getId(entityType).toString();
            killedByEntities.put("killedby;" + entityName, count);
        }

        return SortMap.sortByValue(killedByEntities);
    }

    /**
     * Returns a map of entities killed by the player.
     * 
     * @return
     */
    private static Map<String, Integer> getKilledEntities(ServerPlayerEntity player) {
        Map<String, Integer> killedEntities = new HashMap<String, Integer>();

        for (EntityType<?> entityType : Registries.ENTITY_TYPE) {
            int count = player.getStatHandler().getStat(Stats.KILLED.getOrCreateStat(entityType));
            String entityName = Registries.ENTITY_TYPE.getId(entityType).toString();
            killedEntities.put("killed;" + entityName, count);
        }

        return SortMap.sortByValue(killedEntities);
    }

}
