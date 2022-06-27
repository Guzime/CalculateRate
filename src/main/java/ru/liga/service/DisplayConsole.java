package ru.liga.service;

import ru.liga.model.Rate;

import java.util.List;

public class DisplayConsole {
    /**
     * Вывод всех курсов в списке
     *
     * @param listRate Список курсов
     */
    public void printRatesToConsole(List<Rate> listRate) {
        for (Rate rate : listRate) {
            System.out.println(rate);
        }
    }
}
