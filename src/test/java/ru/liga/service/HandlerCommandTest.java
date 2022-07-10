package ru.liga.service;

import junit.framework.TestCase;
import org.junit.Test;
import ru.liga.model.Command;
import ru.liga.repository.RateParser;

import static org.assertj.core.api.Assertions.assertThat;

public class HandlerCommandTest extends TestCase {

    private static ShowRate showRate = new ShowRate();


    @Test
    public void testHandlerTomorrow() {
        String commandInput = "rate TRY -date tomorrow -alg avg";
        ValidateCommands validateCommands = new ValidateCommands(commandInput);
        validateCommands.validate();

        HandlerCommand handlerCommand = new HandlerCommand(new HandlerListRateAVG(), new RateParser(), new Command(commandInput.split("-")));

        assertThat(showRate.convertListRatesToString(handlerCommand.call()))
                .isEqualTo("СБ 18.06.2022 - 34,1940\n");
    }

    @Test
    public void testHandlerWeek() {
        String commandInput = "rate TRY -period week -alg avg";
        ValidateCommands validateCommands = new ValidateCommands(commandInput);
        validateCommands.validate();

        HandlerCommand handlerCommand = new HandlerCommand(new HandlerListRateAVG(), new RateParser(), new Command(commandInput.split("-")));

        assertThat(showRate.convertListRatesToString(handlerCommand.call()))
                .isEqualTo("ПТ 24.06.2022 - 33,5328\n" +
                        "ЧТ 23.06.2022 - 33,4472\n" +
                        "СР 22.06.2022 - 33,4035\n" +
                        "ВТ 21.06.2022 - 33,4236\n" +
                        "ПН 20.06.2022 - 33,5119\n" +
                        "ВС 19.06.2022 - 33,8247\n" +
                        "СБ 18.06.2022 - 34,1940\n");
    }

    @Test
    public void testHandlerAfterTomorrow() {
        String commandInput = "rate TRY -date 20.06.2022 -alg avg";
        ValidateCommands validateCommands = new ValidateCommands(commandInput);
        validateCommands.validate();

        HandlerCommand handlerCommand = new HandlerCommand(new HandlerListRateAVG(), new RateParser(), new Command(commandInput.split("-")));
        assertThat(showRate.convertListRatesToString(handlerCommand.call()))
                .isEqualTo("ПН 20.06.2022 - 33,5119\n");
    }

    @Test
    public void testHandlerAfterVeryLate() {
        String commandInput = "rate TRY -date 20.06.2023 -alg avg";
        ValidateCommands validateCommands = new ValidateCommands(commandInput);
        validateCommands.validate();

        HandlerCommand handlerCommand = new HandlerCommand(new HandlerListRateAVG(), new RateParser(), new Command(commandInput.split("-")));
        assertThat(showRate.convertListRatesToString(handlerCommand.call()))
                .isEqualTo("ВТ 20.06.2023 - 33,5180\n");
    }

    @Test
    public void testHandlerMonth() {
        String commandInput = "rate TRY -period month -alg avg";
        ValidateCommands validateCommands = new ValidateCommands(commandInput);
        validateCommands.validate();

        HandlerCommand handlerCommand = new HandlerCommand(new HandlerListRateAVG(), new RateParser(), new Command(commandInput.split("-")));

        assertThat(showRate.convertListRatesToString(handlerCommand.call()))
                .isEqualTo("ВС 17.07.2022 - 33,5178\n" +
                        "СБ 16.07.2022 - 33,5179\n" +
                        "ПТ 15.07.2022 - 33,5181\n" +
                        "ЧТ 14.07.2022 - 33,5183\n" +
                        "СР 13.07.2022 - 33,5183\n" +
                        "ВТ 12.07.2022 - 33,5178\n" +
                        "ПН 11.07.2022 - 33,5172\n" +
                        "ВС 10.07.2022 - 33,5170\n" +
                        "СБ 09.07.2022 - 33,5185\n" +
                        "ПТ 08.07.2022 - 33,5198\n" +
                        "ЧТ 07.07.2022 - 33,5197\n" +
                        "СР 06.07.2022 - 33,5178\n" +
                        "ВТ 05.07.2022 - 33,5149\n" +
                        "ПН 04.07.2022 - 33,5126\n" +
                        "ВС 03.07.2022 - 33,5157\n" +
                        "СБ 02.07.2022 - 33,5287\n" +
                        "ПТ 01.07.2022 - 33,5292\n" +
                        "ЧТ 30.06.2022 - 33,5190\n" +
                        "СР 29.06.2022 - 33,5045\n" +
                        "ВТ 28.06.2022 - 33,4944\n" +
                        "ПН 27.06.2022 - 33,4966\n" +
                        "ВС 26.06.2022 - 33,5376\n" +
                        "СБ 25.06.2022 - 33,6197\n" +
                        "ПТ 24.06.2022 - 33,5328\n" +
                        "ЧТ 23.06.2022 - 33,4472\n" +
                        "СР 22.06.2022 - 33,4035\n" +
                        "ВТ 21.06.2022 - 33,4236\n" +
                        "ПН 20.06.2022 - 33,5119\n" +
                        "ВС 19.06.2022 - 33,8247\n" +
                        "СБ 18.06.2022 - 34,1940\n");
    }

    @Test
    public void testSomeCurrencyHandlerWeek() {
        String commandInput = "rate TRY,USD -period week -alg avg";
        ValidateCommands validateCommands = new ValidateCommands(commandInput);
        validateCommands.validate();

        HandlerCommand handlerCommand = new HandlerCommand(new HandlerListRateAVG(), new RateParser(), new Command(commandInput.split("-")));

        assertThat(showRate.convertListRatesToString(handlerCommand.call()))
                .isEqualTo("ПТ 24.06.2022 - 33,5328\n" +
                        "ЧТ 23.06.2022 - 33,4472\n" +
                        "СР 22.06.2022 - 33,4035\n" +
                        "ВТ 21.06.2022 - 33,4236\n" +
                        "ПН 20.06.2022 - 33,5119\n" +
                        "ВС 19.06.2022 - 33,8247\n" +
                        "СБ 18.06.2022 - 34,1940\n" +
                        "\n" +
                        "ПТ 24.06.2022 - 57,5602\n" +
                        "ЧТ 23.06.2022 - 57,4479\n" +
                        "СР 22.06.2022 - 57,4035\n" +
                        "ВТ 21.06.2022 - 57,4503\n" +
                        "ПН 20.06.2022 - 57,5677\n" +
                        "ВС 19.06.2022 - 57,9003\n" +
                        "СБ 18.06.2022 - 58,2823\n");
    }

    @Test
    public void testSomeCurrencyHandlerToDay() {
        String commandInput = "rate TRY,USD -date 22.06.2022 -alg avg";
        ValidateCommands validateCommands = new ValidateCommands(commandInput);
        validateCommands.validate();

        HandlerCommand handlerCommand = new HandlerCommand(new HandlerListRateAVG(), new RateParser(), new Command(commandInput.split("-")));

        assertThat(showRate.convertListRatesToString(handlerCommand.call()))
                .isEqualTo(
                        "СР 22.06.2022 - 33,4035\n" +
                                "\n" +
                                "СР 22.06.2022 - 57,4035\n");
    }

}