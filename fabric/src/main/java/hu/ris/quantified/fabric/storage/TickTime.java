package hu.ris.quantified.fabric.storage;

import java.time.LocalTime;

import lombok.Getter;

/**
 * Represents the time in ticks in a Minecraft world.
 * <p>
 * This class provides methods to convert the total number of ticks into
 * seconds, minutes, hours, days, weeks, months, and years. * In Minecraft, one
 * tick is 1/20th of a second, so there are 20 ticks in a second, 1200 ticks in
 * a minute, 72000 ticks in an hour, and so on. * The time is calculated based
 * on the total number of ticks that have passed in the world.
 * <p>
 * Example usage:
 * 
 * <pre>
 * {@code
 * TickTime tickTime = new TickTime(24000);
 * System.out.println("Total seconds: " + tickTime.getSeconds());
 * System.out.println("Total minutes: " + tickTime.getMinutes());
 * System.out.println("Total hours: " + tickTime.getHours());
 * System.out.println("Total days: " + tickTime.getDays());
 * System.out.println("Total weeks: " + tickTime.getWeeks());
 * System.out.println("Total months: " + tickTime.getMonths());
 * System.out.println("Total years: " + tickTime.getYears());
 * }
 * </pre>
 * 
 */
public class TickTime {

    @Getter
    private final long ticks;

    public TickTime(long ticks) {
        this.ticks = ticks;
    }

    public TickTime(double ticks) {
        this.ticks = (long) ticks;
    }

    /**
     * Returns the total number of ticks that have passed in the world.
     * <p>
     * This is the raw value representing the time in ticks.
     */
    public double getSeconds() {
        return ticks / 20.0;
    }

    /**
     * Returns the total number of seconds that have passed in the world.
     * <p>
     * This is calculated by dividing the total ticks by 20, as there are 20 ticks
     * per second in Minecraft.
     * 
     * @return the total number of seconds in the world
     */
    public double getMinutes() {
        return getSeconds() / 60.0;
    }

    /**
     * Returns the total number of minutes that have passed in the world.
     * <p>
     * This is calculated by dividing the total seconds by 60.
     * 
     * @return the total number of minutes in the world
     */
    public double getHours() {
        return getMinutes() / 60.0;
    }

    /**
     * Returns the total number of hours that have passed in the world.
     * <p>
     * This is calculated by dividing the total minutes by 60.
     * 
     * @return the total number of hours in the world
     */
    public double getDays() {
        return getHours() / 24.0;
    }

    /**
     * Returns the total number of days that have passed in the world.
     * <p>
     * This is calculated by dividing the total hours by 24.
     * 
     * @return the total number of days in the world
     */
    public double getWeeks() {
        return getDays() / 7.0;
    }

    /**
     * Returns the total number of months that have passed in the world.
     * <p>
     * This is calculated by dividing the total days by 30.44, which is the average
     * number of days in a month.
     * 
     * @return the total number of months in the world
     */
    public double getMonths() {
        return getDays() / 30.44;
    }

    /**
     * Returns the total number of years that have passed in the world.
     * <p>
     * This is calculated by dividing the total days by 365.25, which accounts for
     * leap years.
     * 
     * @return the total number of years in the world
     */
    public double getYears() {
        return getDays() / 365.25;
    }

    /**
     * Returns the date representation of the time in ticks.
     * <p>
     * This is calculated by converting the total ticks to milliseconds and creating
     * a Date object. * @return the date representation of the time in ticks
     */

    public LocalTime getLocalTime() {
        long seconds = (long) getSeconds();
        int hours = (int) (seconds / 3600) % 24;
        int minutes = (int) (seconds % 3600) / 60;
        int secs = (int) (seconds % 60);
        return LocalTime.of(hours, minutes, secs);
    }

}
