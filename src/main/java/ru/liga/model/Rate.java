package ru.liga.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Rate {
    private final LocalDate date;
    private final Double rate;

    public Rate(LocalDate date, Double rate) {
        this.date = date;
        this.rate = rate;
    }

    public Double getRate() {
        return rate;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EE dd.MM.yyyy");
        return formatter.format(date).toUpperCase() + " - " + String.format("%.4f", rate);
    }
}
