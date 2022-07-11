package ru.liga.model;

public enum Currency {
    USD("src/main/resources/csv/USD_F01_02_2005_T05_03_2022.csv"),
    TRY("src/main/resources/csv/TRY_F01_02_2005_T05_03_2022.csv"),
    EUR("src/main/resources/csv/EUR_F01_02_2005_T05_03_2022.csv"),
    BGN("src/main/resources/csv/BGN_F01_02_2005_T05_03_2022.csv"),
    AMD("src/main/resources/csv/AMD_F01_02_2005_T05_03_2022.csv");
    private final String path;

    Currency(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
