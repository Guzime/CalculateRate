package ru.liga.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OldRate {
    private static final int daysOfRate = 7;
    private final List<Date> dateList = new ArrayList<>();
    private final List<Double> doubleRateList = new ArrayList<>();

    public OldRate() {

    }

    /**
     * Конструктор
     *
     * @param path Путь до CSV файла с курсами
     */

    public OldRate(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            int countLine = 0;
            while ((line = br.readLine()) != null && countLine < daysOfRate + 1) {
                if (countLine > 0) {
                    String[] rateInfo = line.split(";");
                    SimpleDateFormat ft = new SimpleDateFormat("dd.MM.yyyy");
                    dateList.add(ft.parse(rateInfo[1]));
                    doubleRateList.add(Double.parseDouble(rateInfo[2].substring(1, 9).replace(',', '.')));
                }
                countLine++;
            }
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Вывводяться курсы в формате EE dd.MM.yyyy - number(курс на этот день)
     *
     * @return Строка с курсом на все дни
     */
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < dateList.size(); i++) {
            SimpleDateFormat formattedDate = new SimpleDateFormat("EE dd.MM.yyyy");
            String formattedDouble = String.format("%.4f", doubleRateList.get(i));
            result.append(formattedDate.format(dateList.get(i)).toUpperCase()).append(" - ").append(formattedDouble).append('\n');
        }
        return result.toString();
    }

    /**
     * Курс на неделю
     *
     * @return Курс расчитанный по среднеарифметическому всех остальных
     */
    public OldRate weekRate() {
        OldRate tempRate = plusOneRate(oneDayRate());
        for (int i = 0; i < daysOfRate - 1; i++) {
            tempRate = tempRate.plusOneRate(tempRate.oneDayRate());
        }
        return tempRate;
    }

    /**
     * Курс на один день
     *
     * @return Курс расчитанный по среднеарифметическому всех остальных курсов в классе
     */
    public OldRate oneDayRate() {
        OldRate resultRate = new OldRate();
        Double doubleRate = 0d;

        resultRate.dateList.add(getNextDay(this.dateList.get(0)));
        for (int i = 0; i < daysOfRate; i++) {
            doubleRate += this.doubleRateList.get(i);
        }
        resultRate.doubleRateList.add(doubleRate / this.dateList.size());
        return resultRate;
    }

    /**
     * Получение следущего дня
     *
     * @param date Входная дата
     * @return Входная дата + 1 день
     */
    private Date getNextDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    /**
     * Добавление к текущему листу курсу валют курса, состоящего из 1 строки
     *
     * @param rate Входной курс
     * @return Текущий курс, с добавлением входного курса и удалением самого старого
     */
    private OldRate plusOneRate(OldRate rate) {
        OldRate resultRate = new OldRate();
        resultRate.doubleRateList.add(rate.doubleRateList.get(0));
        resultRate.dateList.add(rate.dateList.get(0));
        for (int i = 0; i < daysOfRate - 1; i++) {
            resultRate.doubleRateList.add(this.doubleRateList.get(i));
            resultRate.dateList.add(this.dateList.get(i));
        }
        return resultRate;
    }

}
