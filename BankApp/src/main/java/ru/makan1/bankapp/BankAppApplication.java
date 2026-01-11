package ru.makan1.bankapp;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.makan1.bankapp.service.menu.ConsoleMenu;

public class BankAppApplication {

    public static void main(String[] args) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("ru.makan1.bankapp");

        ConsoleMenu consoleMenu = context.getBean(ConsoleMenu.class);
        consoleMenu.run();
        context.close();
    }

}
