package com.napier.sem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PopCityNoCity {

    // naming conventions matched with world.sql, otherwise would have used more specific names
    private final String name;
    private final long total;
    private final long inCities;


    // Constructor, this is building our Country obj (1to1 with the provided City class)
    public PopCityNoCity(String name, long total, long inCities) {
        this.name = name;
        this.total = total;
        this.inCities = inCities;
    }

    // getters, since we don't need to change the output, no setters needed
    public String getName() { return name;}
    public long getTotal() { return total; }
    public long getInCities() { return inCities;}
    public long getNotInCities() { return total - inCities;}

    /**
     * Percentage of the population in cities
     * 0 if the total is also 0.
     * @return
     */
    public double getCityPercentage() {
        return total == 0 ? 0.0 : inCities * 100.0 / total;
    }

    /**
     * Percentage of population not in cities
     *
     * @return
     */
    public double getNotinCityPercentage() {
        return total == 0 ? 0.0 : getNotInCities() * 100.0 / total;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return String.format("%s | total=%d | in cities=%d (%.2f%%) | not in cities=%d (%.2f%%)",
                name, total, inCities, getCityPercentage(), getNotInCities(), getNotinCityPercentage());
    }


    public static

}