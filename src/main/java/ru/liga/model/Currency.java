package ru.liga.model;

public enum Currency {
    USD("src/main/resources/RC_F01_06_2002_T17_06_2022_USD.csv"),
    TRY("src/main/resources/RC_F01_06_2002_T17_06_2022_TRY.csv"),
    EUR("src/main/resources/RC_F01_06_2002_T17_06_2022_EUR.csv");
    private String path;

    Currency(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
