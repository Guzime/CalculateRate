package ru.liga.service;

import ru.liga.model.Algorithm;
import ru.liga.model.Rate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class HandlerListRateAVG extends HandlerListRate {
    /**
     * Курс на один следующий день
     *
     * @param listRate Список курсов за неделю
     * @return Курс посчитанный по среднеарифметическому всех остальных курсов в классе
     */
    @Override
    public Rate oneDayRate(List<Rate> listRate, LocalDate toDate) {
        BigDecimal numRate = BigDecimal.ZERO;

        for (int i = 0; i < listRate.size(); i++) {
            numRate = numRate.add(listRate.get(i).getRate());
        }
        return new Rate(toDate,
                numRate.divide(BigDecimal.valueOf(Algorithm.AVG.getCountReadRates()), 4, RoundingMode.HALF_UP));
    }
}
