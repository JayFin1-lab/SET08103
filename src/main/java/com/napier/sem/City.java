package com.napier.sem;

import java.util.ArrayList;
import java.sql.*;
/**
 * Represents a city from the world database
 */
public class City {

    private long id;
    private String name;
    private String countryCode;
    private String district;
    private long population;

    public City(long id, String name, String countryCode, String district, long population) {
        this.id = id;
        this.name = name;
        this.countryCode = countryCode;
        this.district = district;
        this.population = population;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getDistrict() {
        return district;
    }

    public long getPopulation() {
        return population;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", district='" + district + '\'' +
                ", population=" + population +
                '}';
    }

    // All countries in the world (limit 0), or the top N.
    public static ArrayList<City> getCities(Connection con, int limit) {
        return query(con, "", null, limit);
    }
    /** Countries in a continent, e.g. "Europe". */
    public static ArrayList<City> getByContinent(Connection con, String continent, int limit) {
        return query(con, "WHERE country.Continent = ? ", continent, limit);
    }
    /** Countries in a region, e.g. "Caribbean". */
    public static ArrayList<City> getByRegion(Connection con, String region, int limit) {
        return query(con, "WHERE country.Region = ? ", region, limit);
    }
    public static ArrayList<City> getByCountry(Connection con, String countryName, int limit) {
        return query(con, "WHERE country.Name = ? ", countryName, limit);
    }
    public static ArrayList<City> getByDistrict(Connection con, String district, int limit) {
        return query(con, "WHERE country.Region = ? ", district, limit);
    }

    //Runs the country query. "Where" is empty for the whole world
    private static ArrayList<City> query(Connection con, String where, String value, int limit) {
        ArrayList<City> cities = new ArrayList<>();

        String sql = "SELECT city.ID, city.Name, city.CountryCode, city.District, city.Population "
                + "FROM city JOIN country on city.CountryCode = country.Code " + where
                + "ORDER BY city.Population DESC "
                + (limit > 0 ? "LIMIT ?" : "");

        try (PreparedStatement query = con.prepareStatement(sql)) {
            int i = 1;
            if (value != null) query.setString(i++, value);
            if (limit > 0) query.setInt(i, limit);

            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) {
                    cities.add(new City(
                            rs.getLong("ID"), rs.getString("Name"), rs.getString("CountryCode"),
                            rs.getString("District"), rs.getLong("Population")));
                }
            }
        } catch (SQLException e) {
            System.out.println("Cant access cities" + e.getMessage());
        }
        return cities;
    }
}