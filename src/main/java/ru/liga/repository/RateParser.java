package ru.liga.repository;

import ru.liga.model.Rate;
import ru.liga.util.ConstantsRate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RateParser {
    /**
     * Добавление элементов списка курсов из заданного файла
     *
     * @param path Путь до файла
     * @return Список курсов
     */
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
                Rate rate = new Rate(LocalDate.parse(temp[indexDate], formatter),
                        new BigDecimal(temp[indexRate]
                                .replace(',', '.')
                                .replace('\"', ' ')
                                .trim()));
                listRate.add(rate);
            }

        }
        return listRate;
    }
}
