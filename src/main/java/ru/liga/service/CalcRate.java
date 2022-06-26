package ru.liga.service;

import ru.liga.model.Rate;

import java.util.List;

public interface CalcRate {
    List<Rate> getRateFromFile(String path);

    void printRatesToConsole(List<Rate> listRate);

    Rate oneDayRate(List<Rate> listRate);

    List<Rate> weekRate(List<Rate> listRate);
}
