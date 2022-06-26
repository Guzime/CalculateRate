package ru.liga.model;

import ru.liga.util.ConstantsRate;

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

    /**
     * Вывводиться курс в формате EE dd.MM.yyyy - number(курс на этот день)
     *
     * @return строка в нужном формате
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ConstantsRate.OUT_PATTERN_DT);
        return formatter.format(date).toUpperCase() + " - " + String.format("%.4f", rate);
    }
}
