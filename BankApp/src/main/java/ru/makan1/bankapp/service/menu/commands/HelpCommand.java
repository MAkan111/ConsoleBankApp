package ru.makan1.bankapp.service.menu.commands;

import org.springframework.stereotype.Component;
import ru.makan1.bankapp.enums.MenuCommand;
import ru.makan1.bankapp.service.menu.Command;
import ru.makan1.bankapp.service.menu.CommandContext;

import java.util.Comparator;
import java.util.List;

@Component
public class HelpCommand implements Command {

    private final List<Command> commands;
    public HelpCommand(List<Command> commands) { this.commands = commands; }

    @Override
    public MenuCommand command() {
        return MenuCommand.HELP;
    }

    @Override
    public String description() {
        return "Показать список команд";
    }

    @Override
    public void execute(CommandContext ctx) {
        commands.stream()
                .sorted(Comparator.comparing(c -> c.command().name()))
                .forEach(c ->
                        System.out.printf("%-18s - %s%n",
                                c.command().name(),
                                c.description()));
    }
}
