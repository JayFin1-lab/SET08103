package com.napier.sem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/*
 * This class has two objectives:
 *
 * 1. The data and calculations (fields, getters, setters, percentages), similar to the given city.java class
 * which need no database and can be unit tested.
 * 2. The static query methods that fill the rows from the database.
 */
public class PopCityNoCity {

    // naming conventions matched with world.sql, otherwise would have used more specific names
    private final String name;
    private final long total;
    private final long inCities;


    /** Constructor, this is building our Country obj (1to1 with the provided City class)
    *
    * @param name  (continent, region or country name)
    * @param total (total population)
    * @param inCities (population of people living in cities, which we can also use to gauge people not in cities)
     */
    public PopCityNoCity(String name, long total, long inCities) {
        this.name = name;
        this.total = total;
        this.inCities = inCities;
    }

    // getters, since we don't need to change the output, no setters needed
    public String getName() { return name;}
    public long getTotal() { return total; }
    public long getInCities() { return inCities;}
    public long getNotInCities() { return total - inCities;} //pretty self-explanatory, population number - in cities population

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
     *0 if the total is also 0.
     * @return
     */
    public double getNotinCityPercentage() {
        return total == 0 ? 0.0 : getNotInCities() * 100.0 / total;
    }

    /**
     * Controls how a row prints. %d prints a whole number and %.2f prints a decimal with two places;
     * %% prints a literal percent sign.
     */
    @Override
    public String toString() {
        return String.format("%s | total=%d | in cities=%d (%.2f%%) | not in cities=%d (%.2f%%)",
                name, total, inCities, getCityPercentage(), getNotInCities(), getNotinCityPercentage());
    }

    /**
     * For the first three queries below (continent,region,country)
     * One row per (continent/region/country)
     * @param con to open the database.
     * @return population rows, largest first.
     */
    public static ArrayList<PopCityNoCity> getContinent (Connection con) {
        return query(con, "c.Continent", "c.Continent");
    }


    public static ArrayList<PopCityNoCity> getRegion (Connection con) {
        return query(con, "c.Region", "c.Region");
    }

    public static ArrayList<PopCityNoCity> getCountry (Connection con) {
        return query(con, "c.Name", "c.Code"); //group by primary key (code), allowed name as there's only one per code.
    }

    /**
     *
     * @param con open database
     * @param nameColumn column shows rows name
     * @param groupBy columns that define one row of the report
     * @return report rows, largest total population first
     */
    private static ArrayList<PopCityNoCity> query(Connection con, String nameColumn, String groupBy) {
        ArrayList<PopCityNoCity> rows = new ArrayList<>();

        /*
         * The inner SELECT adds up the city populations for each country
         * first, giving ONE row per country.
         *
         * The outer query joins that to the country table and groups the
         * countries into continents/regions (or leaves them as countries).
         *
         * Adding the cities first matters: joining straight to the city table
         * would repeat each country's population once per city, making the
         * totals far too large.
         *
         * LEFT JOIN keeps countries that have no cities in the data, and
         * COALESCE turns their missing city total (NULL) into 0.
         */
        String sql = "SELECT " + nameColumn +" AS Name, " + "SUM(c.Population) AS Total, "
                + "COALESCE(SUM(ci.CityPop), 0) AS CityPopulation " + "FROM country c "
                + "LEFT JOIN (SELECT CountryCode, SUM(Population) AS CityPop "
                + " FROM city GROUP BY CountryCode) ci " + "ON ci.CountryCode = c.Code "
                + "GROUP BY " + groupBy + " " + "ORDER BY Total DESC";

        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                rows.add(new PopCityNoCity(
                        rs.getString("Name"),
                        rs.getLong("Total"),
                        rs.getLong("CityPopulation")));
            }
        } catch (SQLException e) { System.out.println("Error getting population report: " + e.getMessage()); }
        return rows;
    }



}