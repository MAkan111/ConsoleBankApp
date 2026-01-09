package ru.makan1.bankapp;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.makan1.bankapp.service.AccountService;
import ru.makan1.bankapp.service.OperationsConsoleListener;
import ru.makan1.bankapp.service.UserService;

@SpringBootApplication
public class BankAppApplication {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("ru.makan1.bankapp");

        UserService userService = context.getBean(UserService.class);
        AccountService accountService = context.getBean(AccountService.class);

        OperationsConsoleListener operationsConsoleListener = new OperationsConsoleListener(accountService, userService);
        operationsConsoleListener.showMenu();
        context.close();
    }

}
