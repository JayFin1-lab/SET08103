package com.napier.sem;

import java.sql.*;

public class App
{
    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

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
                // Connect to database
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

    /**
     * Story 15: Returns the top N most populated cities
     * in a specified region.
     */
    public java.util.ArrayList<City> getTopNCitiesInRegion(String region, int n) {
        java.util.ArrayList<City> cities = new java.util.ArrayList<>();

        if (region == null || region.isBlank() || n <= 0 || con == null) {
            return cities;
        }
        String sql = "SELECT city.ID, city.Name, city.CountryCode, "
                + "city.District, city.Population "
                + "FROM city JOIN country ON city.CountryCode = country.Code "
                + "WHERE country.Region = ? "
                + "ORDER BY city.Population DESC, city.ID ASC LIMIT ?";

        try (PreparedStatement statement = con.prepareStatement(sql)) {
            statement.setString(1, region);
            statement.setInt(2, n);

            try (ResultSet results = statement.executeQuery()) {
                while (results.next()) {
                    City city = new City(
                            results.getLong("ID"),
                            results.getString("Name"),
                            results.getString("CountryCode"),
                            results.getString("District"),
                            results.getLong("Population")
                    );
                    cities.add(city);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving cities in region: " + e.getMessage());
        }

        return cities;
    }


}