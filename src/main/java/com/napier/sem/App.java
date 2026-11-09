package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;   //Implemented by teammate, presumably used for lists

public class App
{
    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();


        //establishes live connection
        Connection con = a.getConnection();

        // All countries, listed in order from population largest to smallest
        count("All countries, world",                Country.getCountries(con,0));
        count("All countries, continent",            Country.getByContinent(con,"Europe", 0));
        count("All countries, region",               Country.getByRegion(con,"Caribbean", 0));

        show("Top N countries (10), world",          Country.getCountries(con,10));
        show("Top N countries (8), continent",            Country.getByContinent(con,"Asia", 8));
        show("Top N countries (6), region",   Country.getByRegion(con,"South America", 6));

        // All cities, listed in order from population largest to smallest
        count("All cities, world",               City.getCities(con,0));
        count("All cities, continent",           City.getByContinent(con,"Europe", 0));
        count("All cities, region",              City.getByRegion(con,"North America", 0));
        count("All cities, country",             City.getByCountry(con,"Brazil", 0));
        count("All cities, district",            City.getByDistrict(con,"Nevada", 0));

        show("Top N cities (11), world",         City.getCities(con,11));
        show("Top N cities (9), continent",      City.getByContinent(con,"Asia",9));
        show("Top N cities (7), region",         City.getByRegion(con,"Southern Europe", 7));
        show("Top N cities (5), country",        City.getByCountry(con,"Germany", 5));
        show("Top N cities (4), district",       City.getByDistrict(con,"Inner Mongolia", 4));

        count("All cities, world",               CapitalCity.getCountries(con,0));
        count("All cities, continent",           CapitalCity.getContinent(con,"Europe", 0));
        count("All cities, region",              CapitalCity.getRegion(con,"North America", 0));

        show("Top N cities (5), world",         CapitalCity.getCountries(con,5));
        show("Top N cities (4), continent",      CapitalCity.getContinent(con,"Asia",4));
        show("Top N cities (3), region",         CapitalCity.getRegion(con,"Southern Europe", 3));

        // Disconnect from database
        a.disconnect();
    }
//
    private static void show(String title, ArrayList<?> items) {
        System.out.println();
        System.out.println("===" + title + " ===");
        for (Object item : items) {
            System.out.println(item);
        }
    }

    private static void count(String title, ArrayList<?> items) {
        System.out.println();
        System.out.println("=== " + title + ": " + items.size() + " rows ===");
        if (!items.isEmpty()) {
            System.out.println("First: " + items.get(0));
        }
    }
    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    public Connection getConnection() {
        return connect();
    }

    /**
     * Connect to the MySQL database.
     */
    public Connection connect()
    {
        Connection con = null;
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
                // Connect to database, updated the connection (unreliably connected previously??)
                con = DriverManager.getConnection(System.getenv().getOrDefault("DB_URL", "jdbc:mysql://localhost:33060/world?useSSL=false&allowPublicKeyRetrieval=true"),"root", "example");
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
        return con;
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
}


