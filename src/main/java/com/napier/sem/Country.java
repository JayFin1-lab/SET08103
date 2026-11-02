package com.napier.sem;

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
}