package ru.liga.model;

import ru.liga.util.ConstantsRate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Rate {
    private final LocalDate date;
    private final BigDecimal rate;

    public Rate(LocalDate date, BigDecimal rate) {
        this.date = date;
        this.rate = rate;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public LocalDate getDate() {
        return date;
    }

    /**
     * Выводиться курс в формате EE dd.MM.yyyy - number(курс на этот день)
     *
     * @return строка в нужном формате
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ConstantsRate.OUT_PATTERN_DT);
        return formatter.format(date).toUpperCase() + " - " + String.format("%.4f", rate);
    }
}
