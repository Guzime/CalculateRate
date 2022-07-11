package ru.liga.service;

import ru.liga.model.Rate;

import java.util.List;

public class ShowRate {

    /**
     * Курс ввиде строки
     *
     * @param listRate Входной курс
     * @return Весь курс одной строкой
     */
    public String convertRatesToString(List<Rate> listRate) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Rate rate : listRate) {
            stringBuilder.append(rate).append('\n');
        }
        return stringBuilder.toString();
    }

    /**
     * Выввод курса в консоль
     *
     * @param listRate Входной курс
     */
    public void printRatesToConsole(List<Rate> listRate) {
        for (Rate rate : listRate) {
            System.out.println(rate);
        }
    }

    /**
     * Все курсы ввиде строки
     *
     * @param listRate Входные курсы
     * @return Все курсы одной строкой
     */
    public String convertListRatesToString(List<List<Rate>> listRate) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < listRate.size(); i++) {
            if (i == listRate.size() - 1) {
                stringBuilder.append(convertRatesToString(listRate.get(i)));
            } else {
                stringBuilder.append(convertRatesToString(listRate.get(i))).append('\n');
            }
        }
        return stringBuilder.toString();
    }


}
