package ru.liga.service;

import ru.liga.model.Rate;
import ru.liga.util.ConstantsRate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class HandlerListRateAVG implements CalcRate {
    /**
     * Курс на один следующий день
     *
     * @param listRate Список курсов за неделю
     * @return Курс посчитанный по среднеарифметическому всех остальных курсов в классе
     */
    public Rate oneDayRate(List<Rate> listRate) {
        BigDecimal numRate = BigDecimal.ZERO;

        for (int i = 0; i < ConstantsRate.DAYS_OF_RATE; i++) {
            numRate = numRate.add(listRate.get(i).getRate());
        }
        return new Rate(listRate.get(0).getDate().plusDays(1), numRate.divide(BigDecimal.valueOf(ConstantsRate.DAYS_OF_RATE), 4, RoundingMode.HALF_UP));
    }

    /**
     * Курс на неделю по методу среднего арифметического
     *
     * @param listRate Список курсов за неделю
     * @return Список курсов на следующую неделю
     */
    public List<Rate> weekRate(List<Rate> listRate) {
        List<Rate> resultRate = new ArrayList<>();
        for (int i = 0; i < ConstantsRate.DAYS_OF_RATE; i++) {
            resultRate = plusOneRate(listRate, oneDayRate(listRate));
            listRate = resultRate;
        }
        return resultRate;
    }

    /**
     * Добавление к текущему листу курсу валют курса, состоящего из 1 строки
     *
     * @param listRate Входной список курсов
     * @param rate     Прибавляемая одна строка
     * @return Текущий курс, с добавлением входного курса rate и удалением самого старого
     */
    private List<Rate> plusOneRate(List<Rate> listRate, Rate rate) {
        List<Rate> resultRate = new ArrayList<>();
        resultRate.add(rate);
        for (int i = 0; i < ConstantsRate.DAYS_OF_RATE - 1; i++) {
            resultRate.add(listRate.get(i));
        }
        return resultRate;
    }
}
