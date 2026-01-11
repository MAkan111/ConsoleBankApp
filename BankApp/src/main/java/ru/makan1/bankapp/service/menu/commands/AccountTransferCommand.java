package ru.makan1.bankapp.service.menu.commands;

import org.springframework.stereotype.Component;
import ru.makan1.bankapp.enums.MenuCommand;
import ru.makan1.bankapp.service.menu.Command;
import ru.makan1.bankapp.service.menu.CommandContext;
import ru.makan1.bankapp.service.menu.ConsoleInput;

@Component
public class AccountTransferCommand implements Command {

    @Override
    public MenuCommand command() {
        return MenuCommand.ACCOUNT_TRANSFER;
    }

    @Override
    public String description() {
        return "Перевод средств между счетами";
    }

    @Override
    public void execute(CommandContext context) throws Exception {
        long fromAccountId = ConsoleInput.askPositiveLong(context.getBufferedReader(), "С какого accountId перевести: ");
        long toAccountId = ConsoleInput.askPositiveLong(context.getBufferedReader(), "На какой accountId перевести: ");
        double amount = ConsoleInput.askPositiveDouble(context.getBufferedReader(), "Введите сумму: ");
        context.getAccountService().accountTransfer(fromAccountId, toAccountId, amount);
        System.out.println("Перевод выполнен");
    }
}
