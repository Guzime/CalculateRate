package ru.liga.service;

import ru.liga.model.Command;
import ru.liga.model.Rate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HandlerListRateLastYear extends HandlerListRate {

    /**
     * Курс на конкретную дату по алгоритму прошлого года
     *
     * @param listRate Список курсов за неделю
     * @param command  спарсеная комманда
     * @return курс посчитанный для конкретной даты
     */
    @Override
    public List<Rate> toDateRate(List<Rate> listRate, Command command) {
        List<Rate> resultRate = new ArrayList<>();
        resultRate.add(oneDayRate(listRate, command.getToDate()));
        return resultRate;
    }

    /**
     * Курс на один следующий день по алгоритму прошлого года
     *
     * @param listRate Список курсов за неделю
     * @return Курс посчитанный по алгоритму
     */
    @Override
    public Rate oneDayRate(List<Rate> listRate, LocalDate toDate) {
        Rate result = new Rate(LocalDate.now(), BigDecimal.valueOf(0));
        LocalDate localDate = toDate.minusYears(1);

        for (Rate rate : listRate) {
            if (rate.getDate().equals(localDate)) {
                result = new Rate(toDate, rate.getRate());
                return result;
            }
            if (rate.getDate().toEpochDay() - localDate.toEpochDay() < 0) {
                result = new Rate(toDate, rate.getRate());
                return result;
            }
        }
        return result;
    }
}
