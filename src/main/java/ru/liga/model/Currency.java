package ru.liga.model;

public enum Currency {
    USD("USD", "src/main/resources/RC_F01_06_2002_T17_06_2022_USD.csv"),
    TRY("TRY", "src/main/resources/RC_F01_06_2002_T17_06_2022_TRY.csv"),
    EUR("EUR", "src/main/resources/RC_F01_06_2002_T17_06_2022_EUR.csv");
    private String name;
    private String path;

    Currency(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

}