package ru.liga.service;

import com.google.common.collect.Lists;
import ru.liga.model.Command;
import ru.liga.model.Rate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class HandlerListRate {
    /**
     * Курс на конкретную дату
     *
     * @param listRate Список курсов за неделю
     * @param command  спарсенная комманда
     * @return курс посчитанный для конкретной даты
     */
    public List<Rate> toDateRate(List<Rate> listRate, Command command) {
        List<Rate> tempRate = new ArrayList<>();
        List<Rate> resultList = new ArrayList<>();
        LocalDate startDate = command.getStartDate();
        LocalDate toDate = command.getToDate();
        while (!startDate.equals(toDate)) {
            tempRate = plusOneRate(listRate, oneDayRate(listRate, startDate.plusDays(1)));
            listRate = tempRate;
            startDate = startDate.plusDays(1);
        }
        resultList.add(tempRate.get(0));
        return resultList;
    }

    /**
     * Курс на один следующий день
     *
     * @param listRate Список курсов за неделю
     * @return Курс посчитанный по алгоритму
     */
    public Rate oneDayRate(List<Rate> listRate, LocalDate toDate) {
        return new Rate(toDate, BigDecimal.ZERO);
    }

    /**
     * Курс на период
     *
     * @param listRate Список курсов за неделю
     * @return Список курсов на следующую неделю
     */
    public List<Rate> periodRate(List<Rate> listRate, Command command) {
        List<Rate> resultRate = new ArrayList<>();
        List<Rate> tempRate;
        LocalDate startDate = command.getStartDate();
        LocalDate toDate = command.getToDate();

        while (startDate.toEpochDay() < toDate.toEpochDay()) {
            tempRate = plusOneRate(listRate, oneDayRate(listRate, startDate.plusDays(1)));
            resultRate.add(oneDayRate(listRate, startDate.plusDays(1)));
            listRate = tempRate;
            startDate = startDate.plusDays(1);
        }
        return Lists.reverse(resultRate);
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
        for (int i = 0; i < listRate.size() - 1; i++) {
            resultRate.add(listRate.get(i));
        }
        return resultRate;
    }
}