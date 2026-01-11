package ru.makan1.bankapp.service.menu.commands;

import org.springframework.stereotype.Component;
import ru.makan1.bankapp.enums.MenuCommand;
import ru.makan1.bankapp.service.menu.Command;
import ru.makan1.bankapp.service.menu.CommandContext;
import ru.makan1.bankapp.service.menu.ConsoleInput;

@Component
public class AccountDepositCommand implements Command {

    @Override
    public MenuCommand command() {
        return MenuCommand.ACCOUNT_DEPOSIT;
    }

    @Override
    public String description() {
        return "Пополнение счета";
    }

    @Override
    public void execute(CommandContext context) throws Exception {
        long accountId = ConsoleInput.askPositiveLong(context.getBufferedReader(), "Введите accountId который надо пополнить: ");
        double amount = ConsoleInput.askPositiveDouble(context.getBufferedReader(), "Введите сумму пополнения: ");
        context.getAccountService().accountDeposit(accountId, amount);
        System.out.println("Счет пополнен.");
    }
}
