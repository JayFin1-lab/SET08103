package com.napier.sem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import static javax.lang.model.type.TypeKind.UNION;


class CountryLanguage {

    // naming conventions matched with world.sql, otherwise would have used more specific names
    private final String CountryCode;
    private final String Language;
    private final String TrueFalse;
    private final Long Percentage;

    // Constructor, this is building our CountryLanguages obj
    public CountryLanguage(String CountryCode, String Language,String TrueFalse,Long Percentage){
        this.CountryCode = CountryCode;
        this.Language = Language;
        this.TrueFalse = TrueFalse;
        this.Percentage = Percentage;

    }

    public String getCountryCode() {return CountryCode; }
    public String getLanguage() {return Language; }
    public String getTrueFalse() {return TrueFalse; }
    public long getPercentage() {return Percentage; }

    @Override
    public String toString() {
        return "Country{" +
                "Code = " + CountryCode +
                ", Language = " + Language +
                ", True or False = " + TrueFalse +
                ", Percentage = " + Percentage +
                "}";
    }

    public static ArrayList<CountryLanguage> getLanguage(Connection con, String language, int limit) {
        return query(con, "WHAT countrylanguage.language = ? ", language, limit);
    }

    private static ArrayList<CountryLanguage> query(Connection con, String what, String value, int limit) {
        ArrayList<CountryLanguage> countryLanguages = new ArrayList<>();

        String Sql = "SELECT country.Name, country.Population, countrylanguages.Percentage, countrylanguages.Language, "
                + "SUM(countrylanguage.Percentage / 100) * country_population as TotalSpeakers, "
                + "FROM countrylanguage, "
                + "JOIN country on countrylanguage.Code = country.Code, " + what
                + "GROUP BY countrylanguage.Language, "
                + "ORDER BY TotalSpeakres DESC"
                + (limit > 0 ? "LIMIT ?" : "");

        try (PreparedStatement query = con.prepareStatement(Sql)) {
            int i = 1;
            if (value != null) query.setString(i++, value);
            if (limit > 0) query.setInt(i, limit);

            try (ResultSet rs = query.executeQuery()) {
                while (rs.next()) {
                    countryLanguages.add(new CountryLanguage(
                            rs.getString("Code"), rs.getString("Language"), rs.getString("TrueFalse"),
                            rs.getLong("Percentage")));
                }
            }
        } catch (SQLException e) {
            System.out.println("Cant access cities" + e.getMessage());
        }
        return countryLanguages;
    }
}






