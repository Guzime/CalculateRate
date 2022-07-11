package ru.liga.service;

import ru.liga.model.Rate;
import ru.liga.util.LinearRegression;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class HandlerListRateLinearRegression extends HandlerListRate {
    @Override
    public Rate oneDayRate(List<Rate> listRate, LocalDate toDate) {
        double[] x = new double[listRate.size()];
        double[] y = new double[listRate.size()];

        for (int i = 0; i < listRate.size(); i++) {
            x[i] = i;
        }

        for (int i = listRate.size() - 1, j = 0; i >= 0; i--, j++) {
            y[j] = Double.parseDouble(listRate.get(i).getRate().toString());
        }

        LinearRegression linearRegression = new LinearRegression(x, y);

        BigDecimal resRate = BigDecimal.valueOf(linearRegression.predict(31));
        Rate resultRate = new Rate(toDate, resRate);
        return resultRate;
    }
}
