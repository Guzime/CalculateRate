package ru.liga.service;

import com.google.common.collect.Lists;
import ru.liga.model.Command;
import ru.liga.model.Rate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HandlerListRateLastYear implements CalcRate {
    @Override
    public List<Rate> toDateRate(List<Rate> listRate, Command command) {
        List<Rate> resultRate = new ArrayList<>();
        resultRate.add(oneDayRate(listRate, command.getToDate()));
        return resultRate;
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

    private List<Rate> plusOneRate(List<Rate> listRate, Rate rate) {
        List<Rate> resultRate = new ArrayList<>();
        resultRate.add(rate);
        for (int i = 0; i < listRate.size() - 1; i++) {
            resultRate.add(listRate.get(i));
        }
        return resultRate;
    }
}
