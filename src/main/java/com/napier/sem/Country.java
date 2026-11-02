package com.napier.sem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.*;

public class Country {
// naming conventions matched with world.sql, otherwise would have used more specific names
    private final String countryCode;
    private final String name;
    private final String continent;
    private final String region;
    private final long population;
    private final String capital;

    // Constructor, this is building our Country obj (1to1 with the provided City class)
    public Country(String countryCode, String name, String continent, String region, long population, String capital ) {
        this.countryCode = countryCode;
        this.name = name;
        this.continent = continent;
        this.region = region;
        this.population = population;
        this.capital = capital;
    }
    // getters, since we don't need to change the output, no setters needed
    public String getCountryCode() {return countryCode; }
    public String getName() {return name; }
    public String getContinent() {return continent; }
    public String getRegion() {return region; }
    public long getPopulation() {return population; }
    public String getCapital() {return capital; }

    @Override //the output,
    public String toString() {
        return "Country{" +
                "Code=" + countryCode +
                ", name='" + name + '\'' +
                ", continent='" + continent + '\'' +
                ", region='" + region + '\'' +
                ", population=" + population +
                ", capital=" + capital +
                '}';
    }

    // All countries in the world (limit 0), or the top N.
    public static ArrayList<Country> getCountries(Connection con, int limit) {
        return query(con, "", null, limit);
    }
    /** Countries in a continent, e.g. "Europe". */
    public static ArrayList<Country> getByContinent(Connection con, String continent, int limit) {
        return query(con, "WHERE country.Continent = ? ", continent, limit);
    }

    /** Countries in a region, e.g. "Caribbean". */
    public static ArrayList<Country> getByRegion(Connection con, String region, int limit) {
        return query(con, "WHERE country.Region = ? ", region, limit);
    }

    //Runs the country query. "Where" is empty for the whole world
    private static ArrayList<Country> query(Connection con, String where, String value, int limit) {
        ArrayList<Country> countries = new ArrayList<>();

        String sql = "SELECT country.Code, country.Name, country.Continent, country.Region, "
                + "country.Population, city.Name AS Capital "
                + "FROM country LEFT JOIN city ON country.Capital = city.ID "
                + where
                + "ORDER BY country.Population DESC "
                + (limit > 0 ? "LIMIT ?" : "");
        try (PreparedStatement query = con.prepareStatement(sql)) {
            int i = 1;
            if (value != null) query.setString(i++, value);
            if (limit > 0) query.setInt(i, limit);

            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) {
                    countries.add(new Country(
                            rs.getString("Code"), rs.getString("Name"),
                            rs.getString("Continent"), rs.getString("Region"),
                            rs.getLong("Population"), rs.getString("Capital")));
                }
              }
            } catch (SQLException e) {
                System.out.println("Cant access countries" + e.getMessage());
            }
            return countries;
        }

    }