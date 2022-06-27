package ru.liga.repository;

import ru.liga.util.ConstantsRate;

import java.io.BufferedReader;
import java.io.IOException;

public class FileReader {
    /**
     * Считывание файла csv из директории resources
     *
     * @param path путь до файла
     * @return массив считанных строк в количестве ConstantsRate.DAYS_OF_RATE + 1
     * @throws RuntimeException кидаю
     */
    public String[] readFile(String path) {
        String[] resultString = new String[ConstantsRate.DAYS_OF_RATE + 1];
        try {
            BufferedReader br = new BufferedReader(new java.io.FileReader(path));
            String line;
            int countLine = 0;
            while ((line = br.readLine()) != null && countLine < ConstantsRate.DAYS_OF_RATE + 1) {
                resultString[countLine] = line;
                countLine++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resultString;
    }
}
