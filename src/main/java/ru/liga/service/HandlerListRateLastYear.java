package ru.liga.service;

import ru.liga.model.Command;
import ru.liga.model.Rate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HandlerListRateLastYear extends HandlerListRate {
    @Override
    public List<Rate> toDateRate(List<Rate> listRate, Command command) {
        List<Rate> resultRate = new ArrayList<>();
        resultRate.add(oneDayRate(listRate, command.getToDate()));
        return resultRate;
    }

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
