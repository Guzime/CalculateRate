package ru.liga.service;

import ru.liga.model.Rate;

import java.util.List;

/**
 * Интерфейс для работы с курсами
 */
public interface CalcRate {

    Rate oneDayRate(List<Rate> listRate);

    List<Rate> weekRate(List<Rate> listRate);
}
