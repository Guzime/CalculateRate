package ru.liga.service;

import junit.framework.TestCase;
import org.junit.Test;
import ru.liga.model.Algorithm;
import ru.liga.model.Command;
import ru.liga.model.Currency;
import ru.liga.model.Rate;
import ru.liga.repository.RateParser;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HandlerListRateLinRegTest extends TestCase {
    private final RateParser rateParser = new RateParser();
    private final HandlerListRateLinearRegression handlerListRateLinearRegression = new HandlerListRateLinearRegression();
    private final ShowRate showRate = new ShowRate();

    @Test
    public void testOneDayRate() {
        List<Rate> listRate = rateParser.parseRateFromFile(Currency.USD.getPath(), Algorithm.LASTYEAR.getCountReadRates());
        String commandInput = "rate USD -date 18.06.2022 -alg linreg";
        ValidateCommands validateCommands = new ValidateCommands(commandInput);
        validateCommands.validate();
        List<Rate> resultRate = handlerListRateLinearRegression.toDateRate(listRate, new Command(commandInput.split("-")));
        assertThat(showRate.convertRatesToString(resultRate)).isEqualTo("СБ 18.06.2022 - 73,9801\n");
    }

    @Test
    public void testOneDayRateLate() {
        List<Rate> listRate = rateParser.parseRateFromFile(Currency.USD.getPath(), Algorithm.LASTYEAR.getCountReadRates());
        String commandInput = "rate USD -date 18.07.2022 -alg linreg";
        ValidateCommands validateCommands = new ValidateCommands(commandInput);
        validateCommands.validate();
        List<Rate> resultRate = handlerListRateLinearRegression.toDateRate(listRate, new Command(commandInput.split("-")));
        assertThat(showRate.convertRatesToString(resultRate)).isEqualTo("ПН 18.07.2022 - 74,2607\n");
    }

    @Test
    public void testPeriodWeek() {
        List<Rate> listRate = rateParser.parseRateFromFile(Currency.USD.getPath(), Algorithm.LASTYEAR.getCountReadRates());
        String commandInput = "rate USD -period week -alg linreg";
        ValidateCommands validateCommands = new ValidateCommands(commandInput);
        validateCommands.validate();
        List<Rate> resultRate = handlerListRateLinearRegression.periodRate(listRate, new Command(commandInput.split("-")));
        showRate.printRatesToConsole(resultRate);
        assertThat(showRate.convertRatesToString(resultRate)).isEqualTo(
                "ПТ 24.06.2022 - 74,0439\n" +
                        "ЧТ 23.06.2022 - 74,0372\n" +
                        "СР 22.06.2022 - 74,0365\n" +
                        "ВТ 21.06.2022 - 74,0346\n" +
                        "ПН 20.06.2022 - 74,0262\n" +
                        "ВС 19.06.2022 - 74,0048\n" +
                        "СБ 18.06.2022 - 73,9801\n");
    }

}