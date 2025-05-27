package hu.ris.storage.statistics;

import java.util.HashMap;
import java.util.Map;

import hu.ris.utils.SortMap;
import hu.ris.utils.SumMap;
import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;

public abstract class StatisticsHelper {

    @Getter
    protected final ServerPlayerEntity player;

    @Getter
    protected final ServerWorld world;

    @Getter
    protected final MinecraftServer server;

    public StatisticsHelper(ServerPlayerEntity player, ServerWorld world, MinecraftServer server) {
        this.player = player;
        this.world = world;
        this.server = server;
    }

    /**
     * Returns a map of mined items by the player.
     * 
     * @return
     */
    protected Map<String, Integer> getMinedItems() {
        Map<String, Integer> minedItems = new HashMap<String, Integer>();

        for (Block block : Registries.BLOCK) {
            int count = this.player.getStatHandler().getStat(Stats.MINED.getOrCreateStat(block));
            String itemName = Registries.BLOCK.getId(block).toString();
            minedItems.put(itemName, count);
        }

        return SortMap.sortByValue(minedItems);
    }

    /**
     * Returns a map of crafted items by the player.
     * 
     * @returns a map of crafted items with their names as keys and the number of
     */
    protected Map<String, Integer> getCraftedItems() {
        Map<String, Integer> craftedItems = new HashMap<String, Integer>();

        for (Item item : Registries.ITEM) {
            int count = this.player.getStatHandler().getStat(Stats.CRAFTED.getOrCreateStat(item));
            String itemName = Registries.ITEM.getId(item).toString();
            craftedItems.put(itemName, count);
        }

        return SortMap.sortByValue(craftedItems);
    }

    /**
     * Returns a map of used items by the player.
     * 
     * @return
     */
    protected Map<String, Integer> getUsedItems() {
        Map<String, Integer> usedItems = new HashMap<String, Integer>();

        for (Item item : Registries.ITEM) {
            int count = this.player.getStatHandler().getStat(Stats.USED.getOrCreateStat(item));
            String itemName = Registries.ITEM.getId(item).toString();
            usedItems.put(itemName, count);
        }

        return SortMap.sortByValue(usedItems);
    }

    /**
     * Returns a map of broken items by the player.
     * 
     * @return
     * 
     */
    protected Map<String, Integer> getBrokenItems() {
        Map<String, Integer> brokenItems = new HashMap<String, Integer>();

        for (Item item : Registries.ITEM) {
            int count = this.player.getStatHandler().getStat(Stats.BROKEN.getOrCreateStat(item));
            String itemName = Registries.ITEM.getId(item).toString();
            brokenItems.put(itemName, count);
        }

        return SortMap.sortByValue(brokenItems);
    }

    /**
     * Returns a map of items picked up by the player.
     * 
     * @return
     */
    protected Map<String, Integer> getPickedUpItems() {
        Map<String, Integer> pickedUpItems = new HashMap<String, Integer>();

        for (Item item : Registries.ITEM) {
            int count = this.player.getStatHandler().getStat(Stats.PICKED_UP.getOrCreateStat(item));
            String itemName = Registries.ITEM.getId(item).toString();
            pickedUpItems.put(itemName, count);
        }

        return SortMap.sortByValue(pickedUpItems);
    }

    /**
     * Returns a map of items dropped by the player.
     * 
     * @return
     */
    protected Map<String, Integer> getDroppedItems() {
        Map<String, Integer> droppedItems = new HashMap<String, Integer>();

        for (Item item : Registries.ITEM) {
            int count = this.player.getStatHandler().getStat(Stats.DROPPED.getOrCreateStat(item));
            String itemName = Registries.ITEM.getId(item).toString();
            droppedItems.put(itemName, count);
        }

        return SortMap.sortByValue(droppedItems);
    }

    /**
     * Returns a map of entities killed by the player.
     * 
     * @return
     */
    protected Map<String, Integer> getKilledByEntities() {
        Map<String, Integer> killedByEntities = new HashMap<String, Integer>();

        for (EntityType<?> entityType : Registries.ENTITY_TYPE) {
            int count = this.player.getStatHandler().getStat(Stats.KILLED_BY.getOrCreateStat(entityType));
            String entityName = Registries.ENTITY_TYPE.getId(entityType).toString();
            killedByEntities.put(entityName, count);
        }

        return SortMap.sortByValue(killedByEntities);
    }

    /**
     * Returns a map of entities killed by the player.
     * 
     * @return
     */
    protected Map<String, Integer> getKilledEntities() {
        Map<String, Integer> killedEntities = new HashMap<String, Integer>();

        for (EntityType<?> entityType : Registries.ENTITY_TYPE) {
            int count = this.player.getStatHandler().getStat(Stats.KILLED.getOrCreateStat(entityType));
            String entityName = Registries.ENTITY_TYPE.getId(entityType).toString();
            killedEntities.put(entityName, count);
        }

        return SortMap.sortByValue(killedEntities);
    }

    /**
     * Returns the total number of items mined by the player.
     * 
     * @return the total number of items mined
     */
    protected int getMinedItemsCount() {
        return SumMap.sumValues(this.getMinedItems());
    }

    /**
     * Returns the total number of crafted items by the player.
     * 
     * @return the total number of crafted items
     */
    protected int getCraftedItemsCount() {
        return SumMap.sumValues(this.getCraftedItems());
    }

    /**
     * Returns the total number of used items by the player.
     * 
     * @return the total number of used items
     */
    protected int getUsedItemsCount() {
        return SumMap.sumValues(this.getUsedItems());
    }

    /**
     * Returns the total number of broken items by the player.
     * 
     * @return the total number of broken items
     */
    protected int getBrokenItemsCount() {
        return SumMap.sumValues(this.getBrokenItems());
    }

    /**
     * Returns the total number of items picked up by the player.
     * 
     * @return the total number of items picked up
     */
    protected int getPickedUpItemsCount() {
        return SumMap.sumValues(this.getPickedUpItems());
    }

    /**
     * Returns the total number of items dropped by the player.
     * 
     * @return the total number of items dropped
     */
    protected int getDroppedItemsCount() {
        return SumMap.sumValues(this.getDroppedItems());
    }

    /**
     * Returns the number of times the player has left the game.
     * 
     * @return the number of times the player has left the game
     */
    protected int getLeaveGame() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.LEAVE_GAME));
    }

    /**
     * Returns the total play time of the player in ticks.
     * 
     * @return the total play time in ticks
     */
    protected long getPlayTime() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.PLAY_TIME));
    }

    /**
     * Returns the total world time in ticks.
     * 
     * @return the total world time in ticks
     */
    protected long getTotalWorldTimeStat() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.TOTAL_WORLD_TIME));
    }

    /**
     * Returns the time since the player's last death in ticks.
     * 
     * @return the time since last death in ticks
     */
    protected long getTimeSinceDeath() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_DEATH));
    }

    /**
     * Returns the time since the player last rested in ticks.
     * 
     * @return the time since last rest in ticks
     */
    protected long getTimeSinceRest() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST));
    }

    /**
     * Returns the total time the player has spent sneaking in ticks.
     * 
     * @return the total sneak time in ticks
     */
    protected long getSneakTime() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.SNEAK_TIME));
    }

    /**
     * Returns the total distance the player has walked in centimeters.
     * 
     * @return the distance walked in centimeters
     */
    protected long getWalkOneCm() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.WALK_ONE_CM));
    }

    /**
     * Returns the total distance the player has crouched in centimeters.
     * 
     * @return the distance crouched in centimeters
     */
    protected long getCrouchOneCm() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.CROUCH_ONE_CM));
    }

    /**
     * Returns the total distance the player has sprinted in centimeters.
     * 
     * @return the distance sprinted in centimeters
     */
    protected long getSprintOneCm() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.SPRINT_ONE_CM));
    }

    /**
     * Returns the total distance the player has walked on water in centimeters.
     * 
     * @return the distance walked on water in centimeters
     */
    protected long getWalkOnWaterOneCm() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.WALK_ON_WATER_ONE_CM));
    }

    /**
     * Returns the total distance the player has fallen in centimeters.
     * 
     * @return the distance fallen in centimeters
     */
    protected long getFallOneCm() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.FALL_ONE_CM));
    }

    /**
     * Returns the total distance the player has climbed in centimeters.
     * 
     * @return the distance climbed in centimeters
     */
    protected long getClimbOneCm() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.CLIMB_ONE_CM));
    }

    /**
     * Returns the total distance the player has flown in centimeters.
     * 
     * @return the distance flown in centimeters
     */
    protected long getFlyOneCm() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.FLY_ONE_CM));
    }

    /**
     * Returns the total distance the player has walked underwater in centimeters.
     * 
     * @return the distance walked underwater in centimeters
     */
    protected long getWalkUnderWaterOneCm() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.WALK_UNDER_WATER_ONE_CM));
    }

    /**
     * Returns the total distance the player has traveled by minecart in
     * centimeters.
     * 
     * @return the distance traveled by minecart in centimeters
     */
    protected long getMinecartOneCm() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.MINECART_ONE_CM));
    }

    /**
     * Returns the total distance the player has traveled by boat in centimeters.
     * 
     * @return the distance traveled by boat in centimeters
     */
    protected long getBoatOneCm() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.BOAT_ONE_CM));
    }

    /**
     * Returns the total distance the player has traveled by pig in centimeters.
     * 
     * @return the distance traveled by pig in centimeters
     */
    protected long getPigOneCm() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.PIG_ONE_CM));
    }

    /**
     * Returns the total distance the player has traveled by horse in centimeters.
     * 
     * @return the distance traveled by horse in centimeters
     */
    protected long getHorseOneCm() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.HORSE_ONE_CM));
    }

    /**
     * Returns the total distance the player has glided with elytra in centimeters.
     * 
     * @return the distance glided with elytra in centimeters
     */
    protected long getAviateOneCm() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.AVIATE_ONE_CM));
    }

    /**
     * Returns the total distance the player has swum in centimeters.
     * 
     * @return the distance swum in centimeters
     */
    protected long getSwimOneCm() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.SWIM_ONE_CM));
    }

    /**
     * Returns the total distance the player has traveled by strider in centimeters.
     * 
     * @return the distance traveled by strider in centimeters
     */
    protected long getStriderOneCm() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.STRIDER_ONE_CM));
    }

    /**
     * Returns the total number of jumps by the player.
     * 
     * @return the number of jumps
     */
    protected int getJump() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.JUMP));
    }

    /**
     * Returns the total number of items dropped by the player.
     * 
     * @return the number of items dropped
     */
    protected int getDrop() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.DROP));
    }

    /**
     * Returns the total damage dealt by the player.
     * 
     * @return the total damage dealt
     */
    protected int getDamageDealt() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.DAMAGE_DEALT));
    }

    /**
     * Returns the total damage dealt and absorbed by the player.
     * 
     * @return the total damage dealt and absorbed
     */
    protected int getDamageDealtAbsorbed() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.DAMAGE_DEALT_ABSORBED));
    }

    /**
     * Returns the total damage dealt and resisted by the player.
     * 
     * @return the total damage dealt and resisted
     */
    protected int getDamageDealtResisted() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.DAMAGE_DEALT_RESISTED));
    }

    /**
     * Returns the total damage taken by the player.
     * 
     * @return the total damage taken
     */
    protected int getDamageTaken() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.DAMAGE_TAKEN));
    }

    /**
     * Returns the total damage blocked by shield by the player.
     * 
     * @return the total damage blocked by shield
     */
    protected int getDamageBlockedByShield() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.DAMAGE_BLOCKED_BY_SHIELD));
    }

    /**
     * Returns the total damage absorbed by the player.
     * 
     * @return the total damage absorbed
     */
    protected int getDamageAbsorbed() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.DAMAGE_ABSORBED));
    }

    /**
     * Returns the total damage resisted by the player.
     * 
     * @return the total damage resisted
     */
    protected int getDamageResisted() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.DAMAGE_RESISTED));
    }

    /**
     * Returns the total number of deaths by the player.
     * 
     * @return the total number of deaths
     */
    protected int getDeaths() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.DEATHS));
    }

    /**
     * Returns the total number of mob kills by the player.
     * 
     * @return the total number of mob kills
     */
    protected int getMobKills() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.MOB_KILLS));
    }

    /**
     * Returns the total number of animals bred by the player.
     * 
     * @return the total number of animals bred
     */
    protected int getAnimalsBred() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.ANIMALS_BRED));
    }

    /**
     * Returns the total number of player kills by the player.
     * 
     * @return the total number of player kills
     */
    protected int getPlayerKills() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.PLAYER_KILLS));
    }

    /**
     * Returns the total number of fish caught by the player.
     * 
     * @return the total number of fish caught
     */
    protected int getFishCaught() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.FISH_CAUGHT));
    }

    /**
     * Returns the total number of times the player has talked to a villager.
     * 
     * @return the number of times talked to a villager
     */
    protected int getTalkedToVillager() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.TALKED_TO_VILLAGER));
    }

    /**
     * Returns the total number of times the player has traded with a villager.
     * 
     * @return the number of times traded with a villager
     */
    protected int getTradedWithVillager() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.TRADED_WITH_VILLAGER));
    }

    /**
     * Returns the total number of cake slices eaten by the player.
     * 
     * @return the number of cake slices eaten
     */
    protected int getEatCakeSlice() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.EAT_CAKE_SLICE));
    }

    /**
     * Returns the total number of times the player has filled a cauldron.
     * 
     * @return the number of times filled a cauldron
     */
    protected int getFillCauldron() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.FILL_CAULDRON));
    }

    /**
     * Returns the total number of times the player has used a cauldron.
     * 
     * @return the number of times used a cauldron
     */
    protected int getUseCauldron() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.USE_CAULDRON));
    }

    /**
     * Returns the total number of times the player has cleaned armor.
     * 
     * @return the number of times cleaned armor
     */
    protected int getCleanArmor() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.CLEAN_ARMOR));
    }

    /**
     * Returns the total number of times the player has cleaned a banner.
     * 
     * @return the number of times cleaned a banner
     */
    protected int getCleanBanner() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.CLEAN_BANNER));
    }

    /**
     * Returns the total number of times the player has cleaned a shulker box.
     * 
     * @return the number of times cleaned a shulker box
     */
    protected int getCleanShulkerBox() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.CLEAN_SHULKER_BOX));
    }

    /**
     * Returns the total number of times the player has interacted with a brewing
     * stand.
     * 
     * @return the number of times interacted with a brewing stand
     */
    protected int getInteractWithBrewingstand() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.INTERACT_WITH_BREWINGSTAND));
    }

    /**
     * Returns the total number of times the player has interacted with a beacon.
     * 
     * @return the number of times interacted with a beacon
     */
    protected int getInteractWithBeacon() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.INTERACT_WITH_BEACON));
    }

    /**
     * Returns the total number of times the player has inspected a dropper.
     * 
     * @return the number of times inspected a dropper
     */
    protected int getInspectDropper() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.INSPECT_DROPPER));
    }

    /**
     * Returns the total number of times the player has inspected a hopper.
     * 
     * @return the number of times inspected a hopper
     */
    protected int getInspectHopper() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.INSPECT_HOPPER));
    }

    /**
     * Returns the total number of times the player has inspected a dispenser.
     * 
     * @return the number of times inspected a dispenser
     */
    protected int getInspectDispenser() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.INSPECT_DISPENSER));
    }

    /**
     * Returns the total number of times the player has played a noteblock.
     * 
     * @return the number of times played a noteblock
     */
    protected int getPlayNoteblock() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.PLAY_NOTEBLOCK));
    }

    /**
     * Returns the total number of times the player has tuned a noteblock.
     * 
     * @return the number of times tuned a noteblock
     */
    protected int getTuneNoteblock() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.TUNE_NOTEBLOCK));
    }

    /**
     * Returns the total number of times the player has placed a flower pot.
     * 
     * @return the number of times placed a flower pot
     */
    protected int getPotFlower() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.POT_FLOWER));
    }

    /**
     * Returns the total number of times the player has triggered a trapped chest.
     * 
     * @return the number of times triggered a trapped chest
     */
    protected int getTriggerTrappedChest() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.TRIGGER_TRAPPED_CHEST));
    }

    /**
     * Returns the total number of times the player has opened an ender chest.
     * 
     * @return the number of times opened an ender chest
     */
    protected int getOpenEnderchest() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.OPEN_ENDERCHEST));
    }

    /**
     * Returns the total number of times the player has enchanted an item.
     * 
     * @return the number of times enchanted an item
     */
    protected int getEnchantItem() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.ENCHANT_ITEM));
    }

    /**
     * Returns the total number of times the player has played a record.
     * 
     * @return the number of times played a record
     */
    protected int getPlayRecord() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.PLAY_RECORD));
    }

    /**
     * Returns the total number of times the player has interacted with a furnace.
     * 
     * @return the number of times interacted with a furnace
     */
    protected int getInteractWithFurnace() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.INTERACT_WITH_FURNACE));
    }

    /**
     * Returns the total number of times the player has interacted with a crafting
     * table.
     * 
     * @return the number of times interacted with a crafting table
     */
    protected int getInteractWithCraftingTable() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.INTERACT_WITH_CRAFTING_TABLE));
    }

    /**
     * Returns the total number of times the player has opened a chest.
     * 
     * @return the number of times opened a chest
     */
    protected int getOpenChest() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.OPEN_CHEST));
    }

    /**
     * Returns the total number of times the player has slept in a bed.
     * 
     * @return the number of times slept in a bed
     */
    protected int getSleepInBed() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.SLEEP_IN_BED));
    }

    /**
     * Returns the total number of times the player has opened a shulker box.
     * 
     * @return the number of times opened a shulker box
     */
    protected int getOpenShulkerBox() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.OPEN_SHULKER_BOX));
    }

    /**
     * Returns the total number of times the player has opened a barrel.
     * 
     * @return the number of times opened a barrel
     */
    protected int getOpenBarrel() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.OPEN_BARREL));
    }

    /**
     * Returns the total number of times the player has interacted with a blast
     * furnace.
     * 
     * @return the number of times interacted with a blast furnace
     */
    protected int getInteractWithBlastFurnace() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.INTERACT_WITH_BLAST_FURNACE));
    }

    /**
     * Returns the total number of times the player has interacted with a smoker.
     * 
     * @return the number of times interacted with a smoker
     */
    protected int getInteractWithSmoker() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.INTERACT_WITH_SMOKER));
    }

    /**
     * Returns the total number of times the player has interacted with a lectern.
     * 
     * @return the number of times interacted with a lectern
     */
    protected int getInteractWithLectern() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.INTERACT_WITH_LECTERN));
    }

    /**
     * Returns the total number of times the player has interacted with a campfire.
     * 
     * @return the number of times interacted with a campfire
     */
    protected int getInteractWithCampfire() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.INTERACT_WITH_CAMPFIRE));
    }

    /**
     * Returns the total number of times the player has interacted with a
     * cartography table.
     * 
     * @return the number of times interacted with a cartography table
     */
    protected int getInteractWithCartographyTable() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.INTERACT_WITH_CARTOGRAPHY_TABLE));
    }

    /**
     * Returns the total number of times the player has interacted with a loom.
     * 
     * @return the number of times interacted with a loom
     */
    protected int getInteractWithLoom() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.INTERACT_WITH_LOOM));
    }

    /**
     * Returns the total number of times the player has interacted with a
     * stonecutter.
     * 
     * @return the number of times interacted with a stonecutter
     */
    protected int getInteractWithStonecutter() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.INTERACT_WITH_STONECUTTER));
    }

    /**
     * Returns the total number of times the player has rung a bell.
     * 
     * @return the number of times rung a bell
     */
    protected int getBellRing() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.BELL_RING));
    }

    /**
     * Returns the total number of times the player has triggered a raid.
     * 
     * @return the number of times triggered a raid
     */
    protected int getRaidTrigger() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.RAID_TRIGGER));
    }

    /**
     * Returns the total number of times the player has won a raid.
     * 
     * @return the number of times won a raid
     */
    protected int getRaidWin() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.RAID_WIN));
    }

    /**
     * Returns the total number of times the player has interacted with an anvil.
     * 
     * @return the number of times interacted with an anvil
     */
    protected int getInteractWithAnvil() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.INTERACT_WITH_ANVIL));
    }

    /**
     * Returns the total number of times the player has interacted with a
     * grindstone.
     * 
     * @return the number of times interacted with a grindstone
     */
    protected int getInteractWithGrindstone() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.INTERACT_WITH_GRINDSTONE));
    }

    /**
     * Returns the total number of times the player has hit a target.
     * 
     * @return the number of times hit a target
     */
    protected int getTargetHit() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.TARGET_HIT));
    }

    /**
     * Returns the total number of times the player has interacted with a smithing
     * table.
     * 
     * @return the number of times interacted with a smithing table
     */
    protected int getInteractWithSmithingTable() {
        return this.player.getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.INTERACT_WITH_SMITHING_TABLE));
    }

}
