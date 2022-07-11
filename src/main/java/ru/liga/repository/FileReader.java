package ru.liga.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReader {
    /**
     * Считывание файла csv из директории resources
     *
     * @param path путь до файла
     * @return массив считанных строк
     * @throws RuntimeException кидаю
     */
    public String[] readFile(String path, int countDate) {
        String[] resultString = new String[countDate + 1];
        try {
            BufferedReader br = new BufferedReader(new java.io.FileReader(path));
            String line;
            int countLine = 0;
            while ((line = br.readLine()) != null && countLine < countDate + 1) {
                resultString[countLine] = line;
                countLine++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resultString;
    }

    /**
     * Считывание ВСЕГО файла csv из директории resources
     *
     * @param path путь до файла
     * @return массив считанных строк
     * @throws RuntimeException кидаю
     */
    public List<String> readFileAll(String path) {
        List<String> resultString = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new java.io.FileReader(path));
            String line;
            int countLine = 0;
            while ((line = br.readLine()) != null) {
                resultString.add(line);
                countLine++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return resultString;
    }
}
