package ru.liga.service;

import ru.liga.model.Rate;
import ru.liga.repository.FileReader;
import ru.liga.util.ConstantsRate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HandlerListRateAVG implements CalcRate, GetRate, ShowRate {

    public HandlerListRateAVG() {

    }

    /**
     * Добавление элементов списка курсов из заданного файла
     *
     * @param path Путь до файла
     * @return Список курсов
     */
    @Override
    public List<Rate> parseRateFromFile(String path) {
        FileReader fileReader = new FileReader();
        List<Rate> listRate = new ArrayList<>();
        String[] rows = fileReader.readFile(path);
        String[] temp;
        int indexRate = 0;
        int indexDate = 0;
        for (int i = 0; i < ConstantsRate.DAYS_OF_RATE + 1; i++) {
            if (i == 0) {
                temp = rows[i].split(";");
                for (int j = 0; j < temp.length; j++) {
                    switch (temp[j]) {
                        case "data":
                            indexDate = j;
                        case "curs":
                            indexRate = j;
                    }
                }
            } else {
                temp = rows[i].split(";");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ConstantsRate.IN_PATTERN_DT);
                Rate rate = new Rate(LocalDate.parse(temp[indexDate], formatter), Double.parseDouble(temp[indexRate].replace(',', '.').replace('\"', ' ').trim()));
                listRate.add(rate);
            }

        }
        return listRate;
    }

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

    /**
     * Курс на один следующий день
     *
     * @param listRate Список курсов за неделю
     * @return Курс посчитанный по среднеарифметическому всех остальных курсов в классе
     */
    public Rate oneDayRate(List<Rate> listRate) {
        Double doubleRate = 0d;

        for (int i = 0; i < ConstantsRate.DAYS_OF_RATE; i++) {
            doubleRate += listRate.get(i).getRate();
        }
        return new Rate(listRate.get(0).getDate().plusDays(1), doubleRate / ConstantsRate.DAYS_OF_RATE);
    }

    /**
     * Курс на неделю по методу среднего арифметического
     *
     * @param listRate Список курсов за неделю
     * @return Список курсов на следующую неделю
     */
    public List<Rate> weekRate(List<Rate> listRate) {
        List<Rate> resultRate = new ArrayList<>();
        for (int i = 0; i < ConstantsRate.DAYS_OF_RATE; i++) {
            resultRate = plusOneRate(listRate, oneDayRate(listRate));
            listRate = resultRate;
        }
        return resultRate;
    }

    /**
     * Добавление к текущему листу курсу валют курса, состоящего из 1 строки
     *
     * @param listRate Входной список курсов
     * @param rate     Прибавляемая одна строка
     * @return Текущий курс, с добавлением входного курса rate и удалением самого старого
     */
    private List<Rate> plusOneRate(List<Rate> listRate, Rate rate) {
        List<Rate> resultRate = new ArrayList<>();
        resultRate.add(rate);
        for (int i = 0; i < ConstantsRate.DAYS_OF_RATE - 1; i++) {
            resultRate.add(listRate.get(i));
        }
        return resultRate;
    }
}
