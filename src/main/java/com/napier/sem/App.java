package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;   //Implemented by teammate, presumably used for lists

public class App
{
    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

        // All countries, listed in order from population largest to smallest
        ArrayList<Country> countries = a.getCountries();
        for (Country current_country : countries) //this is cycling through the current row, hence name "current"
        {
            System.out.println(current_country);
        }

        // All cities, order population largest to smallest
        ArrayList<City> cities = a.getCities();
        for (City current_city : cities)
        {
            System.out.println(current_city);
        }
        // Disconnect from database
        a.disconnect();
    }
    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect()
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database, updated
                con = DriverManager.getConnection("jdbc:mysql://localhost:33060/world?useSSL=false&allowPublicKeyRetrieval=true", "root", "example");
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }
    // set up method, takes no args, return country obj from Country.java
    public ArrayList<Country> getCountries()
    {
        ArrayList<Country> countries = new ArrayList<>(); //just creates an empty list

        //using SQL language, constructing a query
        String sql = "SELECT country.Code, country.Name, country.Continent, country.Region, "
                + "country.Population, city.Name AS Capital "
                + "FROM country LEFT JOIN city ON country.Capital = city.ID "
                + "ORDER BY country.Population DESC";

        try (PreparedStatement query = con.prepareStatement(sql); //con = connect to db, query gets ready to send
             ResultSet rs = query.executeQuery()) // returns rows (rs)
        {
            while (rs.next()) //this is just going row by row
            {
                countries.add(new Country(
                        rs.getString("Code"),
                        rs.getString("Name"),
                        rs.getString("Continent"),
                        rs.getString("Region"),
                        rs.getLong("Population"),
                        rs.getString("Capital")));
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error getting countries: " + e.getMessage());
        }

        return countries;
    }

    // same as our country method, create list, build query, connect to database, run query
    public ArrayList<City> getCities()
    {
        ArrayList<City> cities = new ArrayList<>();

        String sql = "SELECT ID, Name, CountryCode, District, Population "
                + "FROM city ORDER BY Population DESC";

        try (PreparedStatement query = con.prepareStatement(sql);
             ResultSet rs = query.executeQuery())
        {
            while (rs.next())
            {
                cities.add(new City(
                        rs.getLong("ID"),
                        rs.getString("Name"),
                        rs.getString("CountryCode"),
                        rs.getString("District"),
                        rs.getLong("Population")));
            }
        }
        catch (SQLException e)
        {
            System.out.println("Error getting cities: " + e.getMessage());
        }

        return cities;
    }
}


