package io.elkaim.canvas.challenge.command;

import io.elkaim.canvas.challenge.command.exceptions.NoExecutorFoundException;
import io.elkaim.canvas.challenge.command.exceptions.QuitApplicationSignalException;
import io.elkaim.canvas.challenge.command.executors.QuitApplicationExecutor;
import io.elkaim.canvas.challenge.command.model.Command;
import io.elkaim.canvas.challenge.command.model.CommandType;
import io.elkaim.canvas.challenge.io.out.printers.MessagePrinterImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

class CommandServiceImplTest {

    private CommandServiceImpl commandService;

    @BeforeEach
    void init() {
        this.commandService = new CommandServiceImpl(List.of(
                new QuitApplicationExecutor(
                        new MessagePrinterImpl(
                                new PrintStream(new ByteArrayOutputStream())
                        ))));
    }

    @Test
    void should_execute_throw_when_no_impl_for_cmd_type() {
        Command cmd = new Command(CommandType.HELP, "");
        Assertions.assertThrows(NoExecutorFoundException.class, () -> this.commandService.dispatch(cmd));
    }

    @Test
    void should_execute_dispatch_to_relevant_executor_when_available() {
        Command cmd = new Command(CommandType.QUIT, "");
        Assertions.assertThrows(QuitApplicationSignalException.class, () -> this.commandService.dispatch(cmd));
    }
}
