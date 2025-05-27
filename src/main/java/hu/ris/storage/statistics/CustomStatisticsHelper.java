package hu.ris.storage.statistics;

import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;

/**
 * CustomStatisticsHelper is an abstract class that extends StatisticsHelper and
 * provides additional methods to retrieve various statistics that are not
 * directly saved by the game.
 * <p>
 * 
 */
public abstract class CustomStatisticsHelper extends StatisticsHelper {

    public CustomStatisticsHelper(ServerPlayerEntity player, ServerWorld world, MinecraftServer server) {
        super(player, world, server);
    }

    /**
     * Returns the amount of times a totem of undying has saved the player from
     * dying.
     * 
     * @return
     */
    protected int getSavedByTotem() {
        return this.player.getStatHandler().getStat(Stats.USED.getOrCreateStat(Items.TOTEM_OF_UNDYING));
    }

    /**
     * Returns the server's default GameMode.
     * 
     * @return the default GameMode of the server
     */
    protected String getDefaultGameMode() {
        return this.server.getDefaultGameMode().name();
    }

    /**
     * Returns the server's view distance.
     * 
     * @return the view distance of the server
     */
    protected int getViewDistance() {
        return this.server.getPlayerManager().getViewDistance();
    }

    /**
     * Returns the server's simulation distance.
     * 
     * @return the simulation distance of the server
     */
    protected int getSimulationDistance() {
        return this.server.getPlayerManager().getSimulationDistance();
    }

    /**
     * Returns the total number of player lost a raid.
     * 
     * @return
     */
    protected int getRaidLostCount() {
        return this.getRaidTrigger() - this.getRaidWin();
    }

    /**
     * Returns the total number of days that have passed in the world.
     * <p>
     * This is calculated by dividing the total world time by 24000, which is the
     * number of ticks in a day in Minecraft.
     * 
     * @return the total number of days in the world
     */
    protected double getDays() {
        return this.getTotalWorldTime() / 24000.0;
    }

    /**
     * Returns the total world time in ticks.
     * <p>
     * This is the total time that has passed in the world since it was created not
     * including game pauses etc.
     * 
     * @return the total world time in ticks
     **/
    protected long getTotalWorldTime() {
        return world.getTimeOfDay();
    }

}
