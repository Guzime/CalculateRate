package ru.liga.service;

import ru.liga.model.Command;
import ru.liga.model.Rate;

import java.util.List;

/**
 * Интерфейс для работы с курсами
 */
public interface CalcRate {

    List<Rate> toDateRate(List<Rate> listRate, Command command);

    List<Rate> periodRate(List<Rate> listRate, Command command);
}
