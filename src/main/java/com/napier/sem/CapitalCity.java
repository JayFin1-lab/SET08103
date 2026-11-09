package com.napier.sem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CapitalCity {

    // naming conventions matched with world.sql, otherwise would have used more specific names
    private final String name;
    private final String country;
    private final long population;


    // Constructor, this is building our Country obj (1to1 with the provided City class)
    public CapitalCity(String name, String country, long population) {
        this.name = name;
        this.country = country;
        this.population = population;
    }

    // getters, since we don't need to change the output, no setters needed
    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public long getPopulation() {
        return population;
    }

    @Override // the output,
    public String toString() {
        return "CapitalCity{" +
                ", name='" + name + '\'' +
                ", country='" + country + '\'' +
                ", population=" + population +
                '}';
    }

    /** All capital cities in the world, or the top N
     *
     * @param con
     * @param limit
     * @return
     */
    public static ArrayList<CapitalCity> getCountries(Connection con, int limit) {
        return query(con, "", null, limit);
    }

    /** All capital cities in a continent, or the top N
     *
     * @param con
     * @param limit
     * @return
     */
    public static ArrayList<CapitalCity> getContinent(Connection con, String continent, int limit) {
        return query(con, "", null, limit);
    }

    /** All capital cities in a region, or the top N
     *
     * @param con
     * @param limit
     * @return
     */
    public static ArrayList<CapitalCity> getRegion(Connection con, String region, int limit) {
        return query(con, "", null, limit);
    }

    /**
     *
     * @param con
     * @param where
     * @param value
     * @param limit
     * @return
     */
    private static ArrayList<CapitalCity> query(Connection con, String where, String value, int limit) {
        ArrayList<CapitalCity> capitalCities = new ArrayList<>();

        String sql = "SELECT city.Name AS CityName, country.Name AS CountryName, city.Population "
                + "FROM country JOIN city on country.Capital = city.ID "
                + where
                + "ORDER BY city.Population DESC "
                + (limit > 0 ? "LIMIT ?" : "");
        /**
         *
         * @param con
         * @param where
         * @param value
         * @param limit
         * @return
         */
        try (PreparedStatement query = con.prepareStatement(sql)) {
            int i = 1;
            if (value != null) query.setString(i++, value);
            if (limit > 0) query.setInt(i, limit);
            /**
             *
             * @param con
             * @param where
             * @param value
             * @param limit
             * @return
             */
            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) {
                    capitalCities.add(new CapitalCity(
                            rs.getString("CityName"),
                            rs.getString("CountryName"),
                            rs.getLong("Population")));
                }
            }
        } catch (SQLException e) {
            System.out.println("Cant access countries" + e.getMessage());
        }
        return capitalCities;
    }
}

