package com.napier.sem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * One row of the language report: how many people speak a language, and
 * what percentage of the world population that is.
 *
 * Covers the brief's languages: Chinese, English, Hindi, Spanish, Arabic,
 * ordered from the most speakers to the fewest.
 */
public class CountryLanguage {

    private final String language;
    private final long speakers;
    private final double worldPercent;

    /**
     * @param language     language name, e.g. "Chinese"
     * @param speakers     estimated number of speakers worldwide
     * @param worldPercent speakers as a percentage of the world population
     */
    public CountryLanguage(String language, long speakers, double worldPercent) {
        this.language = language;
        this.speakers = speakers;
        this.worldPercent = worldPercent;
    }

    public String getLanguage() { return language; }
    public long getSpeakers() { return speakers; }
    public double getWorldPercent() { return worldPercent; }

    @Override
    public String toString() {
        return String.format("%s | speakers=%d | %.2f%% of world population",
                language, speakers, worldPercent);
    }

    /**
     * Speakers of each language in the brief, most speakers first.
     *
     * How the SQL works:
     *  - Each row in countrylanguage says what percentage of a country speaks
     *    a language, so speakers in that country = country population * percentage / 100.
     *  - Joining to country (on CountryCode = Code) gives us the population.
     *  - GROUP BY language adds those figures up across all countries.
     *  - The world population is the sum of every country's population,
     *    found with a small subquery.
     *
     * The language names are fixed text, never user input.
     *
     * @param con the open database connection
     * @return one row per language, largest first
     */
    public static ArrayList<CountryLanguage> getLanguages(Connection con) {
        ArrayList<CountryLanguage> rows = new ArrayList<>();

        String sql = "SELECT cl.Language, "
                + "SUM(c.Population * cl.Percentage / 100) AS Speakers, "
                + "SUM(c.Population * cl.Percentage / 100) * 100 "
                + "    / (SELECT SUM(Population) FROM country) AS WorldPercent "
                + "FROM countrylanguage cl "
                + "JOIN country c ON cl.CountryCode = c.Code "
                + "WHERE cl.Language IN ('Chinese', 'English', 'Hindi', 'Spanish', 'Arabic') "
                + "GROUP BY cl.Language "
                + "ORDER BY Speakers DESC";

        try (PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                rows.add(new CountryLanguage(
                        rs.getString("Language"),
                        rs.getLong("Speakers"),
                        rs.getDouble("WorldPercent")));
            }
        } catch (SQLException e) {
            System.out.println("Error getting language report: " + e.getMessage());
        }
        return rows;
    }
}
