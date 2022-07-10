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
    FileReader fileReader = new FileReader();

    @Test
    public void testParseRateFromFile() {
        List<Rate> listRate = Arrays.asList(
                new Rate(LocalDate.of(2022, 6, 17), new BigDecimal("58.9568")),
                new Rate(LocalDate.of(2022, 6, 16), new BigDecimal("59.1204")),
                new Rate(LocalDate.of(2022, 6, 15), new BigDecimal("59.2481")),
                new Rate(LocalDate.of(2022, 6, 11), new BigDecimal("60.9656")),
                new Rate(LocalDate.of(2022, 6, 10), new BigDecimal("62.0934")),
                new Rate(LocalDate.of(2022, 6, 9), new BigDecimal("63.9380")),
                new Rate(LocalDate.of(2022, 6, 8), new BigDecimal("64.5699"))
        );
        List<Rate> listRate2 = rateParser.parseRateFromFile(Currency.EUR.getPath(), Algorithm.AVG.getCountReadRates());
        assertThat(listRate2.toString()).isEqualTo(listRate.toString());

    }

    @Test
    public void testGetLastDateFromFile() {
        LocalDate localDate = LocalDate.of(2022, 6, 17);
        assertThat(rateParser.getLastDateFromFile(Currency.EUR.getPath())).isEqualTo(localDate);
    }

    public void testParseRateFromFileToDay() {
        List<Rate> listRate = Arrays.asList(
                new Rate(LocalDate.of(2022, 6, 17), new BigDecimal("58.9568")),
                new Rate(LocalDate.of(2022, 6, 16), new BigDecimal("59.1204")),
                new Rate(LocalDate.of(2022, 6, 15), new BigDecimal("59.2481")),
                new Rate(LocalDate.of(2022, 6, 11), new BigDecimal("60.9656")),
                new Rate(LocalDate.of(2022, 6, 10), new BigDecimal("62.0934")),
                new Rate(LocalDate.of(2022, 6, 9), new BigDecimal("63.9380"))
        );
        List<Rate> listRate2 = rateParser.parseRateFromFile(Currency.EUR.getPath(), LocalDate.of(2022, 6, 9));
        assertThat(listRate2.toString()).isEqualTo(listRate.toString());
    }
}