package ru.liga.model;

public enum Algorithm {
    MIST(30), AVG(7), LASTYEAR(365);
    private int countReadRates;

    Algorithm(int countReadRates) {
        this.countReadRates = countReadRates;
    }

    public int getCountReadRates() {
        return countReadRates;
    }
}
