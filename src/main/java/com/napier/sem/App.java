package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;   //Implemented by teammate, presumably used for lists
/*
Three steps here:

1. Connect to the world.sql database.
2. Ask Country,City, CapitalCity, PopCityNoCity classes to run their reports, print results.
3. Disconnect from the database.
 */
public class App
{
    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();


        //establishes live connection
        Connection con = a.getConnection();


        /*
        Naming conventions for reports:
         limit 0 = all rows, limit N = Top N rows.
         count() is used for all the long "all" reports (prints row count and first row)
         show() is used for short "top N" reports and prints every row.
         */

        // Country reports
        count("All countries, world",                Country.getCountries(con,0));
        count("All countries, continent",            Country.getByContinent(con,"Europe", 0));
        count("All countries, region",               Country.getByRegion(con,"Caribbean", 0));

        show("Top N countries (10), world",          Country.getCountries(con,10));
        show("Top N countries (8), continent",            Country.getByContinent(con,"Asia", 8));
        show("Top N countries (6), region",   Country.getByRegion(con,"South America", 6));

        // City reports
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

        // Capital cities reports
        count("All capital cities, world",               CapitalCity.getCountries(con,0));
        count("All capital cities, continent",           CapitalCity.getContinent(con,"Europe", 0));
        count("All capital cities, region",              CapitalCity.getRegion(con,"North America", 0));

        show("Top N capital cities (5), world",         CapitalCity.getCountries(con,5));
        show("Top N capital cities (4), continent",      CapitalCity.getContinent(con,"Asia",4));
        show("Top N capital cities (3), region",         CapitalCity.getRegion(con,"Southern Europe", 3));

        //Number of people, people living in cities, people not living in cities
        show("Population by continent", PopCityNoCity.getContinent(con));
        count("Population by region", PopCityNoCity.getRegion(con));
        count("Population by country", PopCityNoCity.getCountry(con));

        // Disconnect from database
        a.disconnect();
    }
    /**
    * Prints a heading followed by every item in the list.
    * Used for short reports (top N, continents, regions, etc).
    *
    * @param title
    * @param items the report rows, "?" means any type
     */
    private static void show(String title, ArrayList<?> items) {
        System.out.println();
        System.out.println("===" + title + " ===");
        for (Object item : items) {
            System.out.println(item); //println calls each object's toString() to decide how it looks
        }
    }
    /**
     * Print a heading, how many of the rows came back, and only the first row as we go by largest
     * Used for long "all" reports as printing in full would be excessive. What happens instead is we count
     * all the rows, but return only the first row.
     *
     * @param title
     * @param items (the report rows)
     */
    private static void count(String title, ArrayList<?> items) {
        System.out.println();
        System.out.println("=== " + title + ": " + items.size() + " rows ===");
        if (!items.isEmpty()) {
            System.out.println("First: " + items.get(0)); //if row isn't empty, print first row which would be largest
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


