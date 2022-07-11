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
        assertThat(showRate.convertRatesToString(resultRate)).isEqualTo("СБ 18.06.2022 - 73,8883\n");
    }

    @Test
    public void testOneDayRateLate() {
        List<Rate> listRate = rateParser.parseRateFromFile(Currency.USD.getPath(), Algorithm.LASTYEAR.getCountReadRates());
        String commandInput = "rate USD -date 18.07.2022 -alg linreg";
        ValidateCommands validateCommands = new ValidateCommands(commandInput);
        validateCommands.validate();
        List<Rate> resultRate = handlerListRateLinearRegression.toDateRate(listRate, new Command(commandInput.split("-")));
        assertThat(showRate.convertRatesToString(resultRate)).isEqualTo("ПН 18.07.2022 - 73,9994\n");
    }

    @Test
    public void testPeriodWeek() {
        List<Rate> listRate = rateParser.parseRateFromFile(Currency.USD.getPath(), Algorithm.LASTYEAR.getCountReadRates());
        String commandInput = "rate USD -period week -alg linreg";
        ValidateCommands validateCommands = new ValidateCommands(commandInput);
        validateCommands.validate();
        List<Rate> resultRate = handlerListRateLinearRegression.periodRate(listRate, new Command(commandInput.split("-")));
        assertThat(showRate.convertRatesToString(resultRate)).isEqualTo(
                "СБ 12.03.2022 - 74,8793\n" +
                        "ПТ 11.03.2022 - 74,8806\n" +
                        "ЧТ 10.03.2022 - 74,8835\n" +
                        "СР 09.03.2022 - 74,8838\n" +
                        "ВТ 08.03.2022 - 74,8866\n" +
                        "ПН 07.03.2022 - 74,8849\n" +
                        "ВС 06.03.2022 - 74,8849\n");
    }

}