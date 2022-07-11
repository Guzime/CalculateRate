package ru.liga.service;

import ru.liga.model.Algorithm;
import ru.liga.model.Rate;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class HandlerListRateMist extends HandlerListRate {
    /**
     * Курс на один следующий день по мистическому алгоритму
     *
     * @param listRate Список курсов за неделю
     * @return Курс посчитанный по алгоритму
     */
    @Override
    public Rate oneDayRate(List<Rate> listRate, LocalDate toDate) {
        Random ran = new Random();
        return new Rate(toDate, listRate.get(ran.nextInt(Algorithm.MIST.getCountReadRates())).getRate());
    }
}
