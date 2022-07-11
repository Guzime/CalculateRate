package ru.liga.repository;

import junit.framework.TestCase;
import org.junit.Test;
import ru.liga.model.Algorithm;
import ru.liga.model.Currency;
import ru.liga.model.Rate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RateParserTest extends TestCase {

    RateParser rateParser = new RateParser();

    @Test
    public void testParseRateFromFile() {
        List<Rate> listRate = Arrays.asList(
                new Rate(LocalDate.of(2022, 3, 5), new BigDecimal("116.5312")),
                new Rate(LocalDate.of(2022, 3, 4), new BigDecimal("124.0161")),
                new Rate(LocalDate.of(2022, 3, 3), new BigDecimal("114.5544")),
                new Rate(LocalDate.of(2022, 3, 2), new BigDecimal("102.9112")),
                new Rate(LocalDate.of(2022, 3, 1), new BigDecimal("104.4772")),
                new Rate(LocalDate.of(2022, 2, 26), new BigDecimal("93.5994")),
                new Rate(LocalDate.of(2022, 2, 25), new BigDecimal("97.7688"))
        );
        List<Rate> listRate2 = rateParser.parseRateFromFile(Currency.EUR.getPath(), Algorithm.AVG.getCountReadRates());
        assertThat(listRate2.toString()).isEqualTo(listRate.toString());

    }

    @Test
    public void testGetLastDateFromFile() {
        LocalDate localDate = LocalDate.of(2022, 3, 5);
        assertThat(rateParser.getLastDateFromFile(Currency.EUR.getPath())).isEqualTo(localDate);
    }

    public void testParseRateFromFileToDay() {
        List<Rate> listRate = Arrays.asList(
                new Rate(LocalDate.of(2022, 3, 5), new BigDecimal("116.5312")),
                new Rate(LocalDate.of(2022, 3, 4), new BigDecimal("124.0161")),
                new Rate(LocalDate.of(2022, 3, 3), new BigDecimal("114.5544")),
                new Rate(LocalDate.of(2022, 3, 2), new BigDecimal("102.9112")),
                new Rate(LocalDate.of(2022, 3, 1), new BigDecimal("104.4772"))
        );
        List<Rate> listRate2 = rateParser.parseRateFromFile(Currency.EUR.getPath(), LocalDate.of(2022, 3, 1));
        assertThat(listRate2.toString()).isEqualTo(listRate.toString());
    }
}