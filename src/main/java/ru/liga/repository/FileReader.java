package ru.liga.repository;

import ru.liga.util.ConstantsRate;

import java.io.BufferedReader;
import java.io.IOException;

public class FileReader {
    public String[] getRateFromFile(String path) {
        String[] resultString = new String[ConstantsRate.DAYS_OF_RATE];
        try {
            BufferedReader br = new BufferedReader(new java.io.FileReader(path));
            String line;
            int indexRate = 0;
            int indexDate = 0;
            int countLine = 0;
            while ((line = br.readLine()) != null && countLine < ConstantsRate.DAYS_OF_RATE + 1) {
                if (countLine == 0) {
                    String[] rateInfo = line.split(";");
                    for (int i = 0; i < rateInfo.length; i++) {
                        switch (rateInfo[i]) {
                            case "data":
                                indexDate = i;
                            case "curs":
                                indexRate = i;
                        }
                    }
                }
                if (countLine > 0) {
                    String[] rateInfo = line.split(";");
                    resultString[countLine - 1] = rateInfo[indexDate] + ";" + rateInfo[indexRate].replace(',', '.').replace('\"', ' ').trim();
                }
                countLine++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resultString;
    }
}
