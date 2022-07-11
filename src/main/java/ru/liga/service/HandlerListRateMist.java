package ru.liga.service;

import com.google.common.collect.Lists;
import ru.liga.model.Algorithm;
import ru.liga.model.Command;
import ru.liga.model.Rate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HandlerListRateMist implements CalcRate {
    @Override
    public List<Rate> toDateRate(List<Rate> listRate, Command command) {

        List<Rate> tempRate = new ArrayList<>();
        List<Rate> resultList = new ArrayList<>();
        LocalDate startDate = command.getStartDate();
        LocalDate toDate = command.getToDate();
        while (!startDate.equals(toDate)) {
            tempRate = plusOneRate(listRate, oneDayRate(listRate, command.getToDate()));
            listRate = tempRate;
            startDate = startDate.plusDays(1);
        }
        resultList.add(tempRate.get(0));
        return resultList;
    }

    @Override
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

    public Rate oneDayRate(List<Rate> listRate, LocalDate toDate) {
        Random ran = new Random();
        Rate result = new Rate(toDate, listRate.get(ran.nextInt(Algorithm.MIST.getCountReadRates())).getRate());
        return result;
    }

    private List<Rate> plusOneRate(List<Rate> listRate, Rate rate) {
        List<Rate> resultRate = new ArrayList<>();
        resultRate.add(rate);
        for (int i = 0; i < listRate.size() - 1; i++) {
            resultRate.add(listRate.get(i));
        }
        return resultRate;
    }

}
