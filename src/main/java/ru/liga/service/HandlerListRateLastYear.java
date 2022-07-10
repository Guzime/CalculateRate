package ru.liga.service;

import ru.liga.model.Command;
import ru.liga.model.Rate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HandlerListRateLastYear implements CalcRate {
    @Override
    public List<Rate> toDateRate(List<Rate> listRate, Command command) {
        List<Rate> resultRate = new ArrayList<>();
        LocalDate localDate = command.getToDate().minusYears(1);

        for (Rate rate : listRate) {
            if (rate.getDate().equals(localDate)) {
                resultRate.add(new Rate(command.getToDate(), rate.getRate()));
                return resultRate;
            }
            if (rate.getDate().toEpochDay() - localDate.toEpochDay() < 0) {
                resultRate.add(new Rate(command.getToDate(), rate.getRate()));
                return resultRate;
            }
        }
        return resultRate;
    }

    @Override
    public List<Rate> periodRate(List<Rate> listRate, Command command) {
        return listRate;
    }
}
