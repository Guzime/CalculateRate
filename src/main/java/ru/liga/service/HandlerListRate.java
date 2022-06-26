package ru.liga.service;

import ru.liga.model.Rate;
import ru.liga.repository.FileReader;
import ru.liga.util.ConstantsRate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class HandlerListRate {
    public final List<Rate> listRate = new ArrayList<>();

    public HandlerListRate() {

    }

    public HandlerListRate(String path) {
        FileReader fileReader = new FileReader();
        String[] rows = fileReader.getRateFromFile(path);
        String[] temp;
        LocalDate lt;
        for (int i = 0; i < ConstantsRate.DAYS_OF_RATE; i++) {
            temp = rows[i].split(";");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            Rate rate = new Rate(LocalDate.parse(temp[0], formatter), Double.parseDouble(temp[1]));
            listRate.add(rate);
        }
    }

    public void printRatesToConsole() {
        for (Rate rate : listRate) {
            System.out.println(rate.toString());
        }
    }

    public Rate oneDayRate() {
        Double doubleRate = 0d;

        for (int i = 0; i < ConstantsRate.DAYS_OF_RATE; i++) {
            doubleRate += listRate.get(i).getRate();
        }
        Rate resultRate = new Rate(listRate.get(0).getDate().plusDays(1), doubleRate / ConstantsRate.DAYS_OF_RATE);
        return resultRate;
    }
}
