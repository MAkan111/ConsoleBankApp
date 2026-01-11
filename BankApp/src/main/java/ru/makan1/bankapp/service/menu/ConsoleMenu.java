package ru.makan1.bankapp.service.menu;

import org.springframework.stereotype.Component;
import ru.makan1.bankapp.enums.MenuCommand;

import java.util.EnumMap;
import java.util.List;

@Component
public final class ConsoleMenu {
    private final EnumMap<MenuCommand, Command> commands = new EnumMap<>(MenuCommand.class);
    private final CommandContext context;

    public ConsoleMenu(CommandContext context, List<Command> commandList) {
        this.context = context;
        for (Command c : commandList) {
            commands.put(c.command(), c);
        }
    }

    public void run() throws Exception {
        System.out.println("Введите команду (или 'exit')");
        commands.get(MenuCommand.HELP).execute(context);

        while (true) {
            System.out.print("\n> ");
            String input = context.getBufferedReader().readLine();

            if (input == null || "exit".equalsIgnoreCase(input)) {
                break;
            }

            MenuCommand cmd = MenuCommand.fromInput(input)
                    .orElse(null);

            if (cmd == null) {
                System.out.println("Неизвестная команда.");
                continue;
            }

            Command command = commands.get(cmd);
            if (command == null) {
                System.out.println("Команда не реализована.");
                continue;
            }

            try {
                command.execute(context);
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }

        System.out.println("Выход.");
    }
}
