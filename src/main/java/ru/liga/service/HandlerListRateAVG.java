package ru.liga.service;

import ru.liga.model.Rate;
import ru.liga.repository.FileReader;
import ru.liga.util.ConstantsRate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HandlerListRateAVG implements CalcRate {

    public HandlerListRateAVG() {

    }

    public List<Rate> getRateFromFile(String path) {
        FileReader fileReader = new FileReader();
        List<Rate> listRate = new ArrayList<>();
        String[] rows = fileReader.getRateFromFile(path);
        String[] temp;
        LocalDate lt;
        for (int i = 0; i < ConstantsRate.DAYS_OF_RATE; i++) {
            temp = rows[i].split(";");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(ConstantsRate.IN_PATTERN_DT);
            Rate rate = new Rate(LocalDate.parse(temp[0], formatter), Double.parseDouble(temp[1]));
            listRate.add(rate);
        }
        return listRate;
    }

    public void printRatesToConsole(List<Rate> listRate) {
        for (Rate rate : listRate) {
            System.out.println(rate);
        }
    }

    public Rate oneDayRate(List<Rate> listRate) {
        Double doubleRate = 0d;

        for (int i = 0; i < ConstantsRate.DAYS_OF_RATE; i++) {
            doubleRate += listRate.get(i).getRate();
        }
        return new Rate(listRate.get(0).getDate().plusDays(1), doubleRate / ConstantsRate.DAYS_OF_RATE);
    }

    private List<Rate> plusOneRate(List<Rate> listRate, Rate rate) {
        List<Rate> resultRate = new ArrayList<>();
        resultRate.add(rate);
        for (int i = 0; i < ConstantsRate.DAYS_OF_RATE - 1; i++) {
            resultRate.add(listRate.get(i));
        }
        return resultRate;
    }

    public List<Rate> weekRate(List<Rate> listRate) {
        List<Rate> resultRate = new ArrayList<>();
        for (int i = 0; i < ConstantsRate.DAYS_OF_RATE; i++) {
            resultRate = plusOneRate(listRate, oneDayRate(listRate));
            listRate = resultRate;
        }
        return resultRate;
    }
}
