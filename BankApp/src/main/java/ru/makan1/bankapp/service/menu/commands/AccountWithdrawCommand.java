package ru.makan1.bankapp.service.menu.commands;

import org.springframework.stereotype.Component;
import ru.makan1.bankapp.enums.MenuCommand;
import ru.makan1.bankapp.service.menu.Command;
import ru.makan1.bankapp.service.menu.CommandContext;
import ru.makan1.bankapp.service.menu.ConsoleInput;

@Component
public class AccountWithdrawCommand implements Command {

    @Override
    public MenuCommand command() {
        return MenuCommand.ACCOUNT_WITHDRAW;
    }

    @Override
    public String description() {
        return "Снятие средств со счета";
    }

    @Override
    public void execute(CommandContext context) throws Exception {
        long accountId = ConsoleInput.askPositiveLong(context.getBufferedReader(), "Введите accountId с которого надо снять средства: ");
        double amount = ConsoleInput.askPositiveDouble(context.getBufferedReader(), "Введите сумму: ");
        context.getAccountService().accountWithdraw(accountId, amount);
        System.out.println("Средства со счета " + accountId + " сняты");
    }
}
