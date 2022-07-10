package ru.liga.model;

import junit.framework.TestCase;
import org.junit.Test;
import ru.liga.service.ValidateCommands;

import java.time.LocalDate;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandTest extends TestCase {

    @Test
    public void testValidCommandWithoutOutput() {
        String inputCommand = "rate TRY,USD -date 22.02.2023 -alg avg";
        Command commandTest = new Command(Arrays.asList(Currency.TRY, Currency.USD),
                WordCommand.DATE,
                LocalDate.of(2022, 6, 17),
                LocalDate.of(2023, 2, 22),
                Algorithm.AVG,
                WordCommand.LIST);
        ValidateCommands validateCommands = new ValidateCommands(inputCommand);
        validateCommands.validate();
        Command command = new Command(inputCommand.split("-"));
        assertThat(commandTest.toString()).isEqualTo(command.toString());
    }

    @Test
    public void testValidCommandWithOutput() {
        String inputCommand = "rate TRY,USD -period week -alg mist -output graph";
        Command commandTest = new Command(Arrays.asList(Currency.TRY, Currency.USD),
                WordCommand.PERIOD,
                LocalDate.of(2022, 6, 17),
                LocalDate.of(2022, 6, 24),
                Algorithm.MIST,
                WordCommand.GRAPH);
        ValidateCommands validateCommands = new ValidateCommands(inputCommand);
        validateCommands.validate();
        Command command = new Command(inputCommand.split("-"));
        assertThat(commandTest.toString()).isEqualTo(command.toString());
    }

    @Test
    public void testValidCommand() {
        String inputCommand = "rate TRY -period month -alg avg -output list";
        Command commandTest = new Command(Arrays.asList(Currency.TRY),
                WordCommand.PERIOD,
                LocalDate.of(2022, 6, 17),
                LocalDate.of(2022, 7, 17),
                Algorithm.AVG,
                WordCommand.LIST);
        ValidateCommands validateCommands = new ValidateCommands(inputCommand);
        validateCommands.validate();
        Command command = new Command(inputCommand.split("-"));
        assertThat(commandTest.toString()).isEqualTo(command.toString());
    }

}