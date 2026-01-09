package ru.makan1.bankapp.service;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Component
public class OperationsConsoleListener {

    private final AccountService accountService;
    private final UserService userService;

    public OperationsConsoleListener(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    public void showMenu() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            printHelp();

            while (true) {
                System.out.print("\nВведите команду (или 'exit'): ");
                String cmd = reader.readLine();
                if (cmd == null || "exit".equalsIgnoreCase(cmd.trim())) {
                    System.out.println("Выход.");
                    break;
                }

                switch (cmd.trim().toUpperCase()) {
                    case "USER_CREATE" -> handleUserCreate(reader);
                    case "SHOW_ALL_USERS" -> userService.showAllUsers();
                    case "ACCOUNT_CREATE" -> handleAccountCreate(reader);
                    case "ACCOUNT_CLOSE" -> handleAccountClose(reader);
                    case "ACCOUNT_DEPOSIT" -> handleAccountDeposit(reader);
                    case "ACCOUNT_TRANSFER" -> handleAccountTransfer(reader);
                    case "ACCOUNT_WITHDRAW" -> handleAccountWithdraw(reader);
                    case "HELP" -> printHelp();
                    default -> System.out.println("Неизвестная команда. Введите HELP.");
                }
                System.out.println();
                printHelp();
            }

        } catch (IOException e) {
            throw new RuntimeException("Ошибка ввода из консоли", e);
        }
    }

    private void printHelp() {
        System.out.println("USER_CREATE - Создание нового пользователя.");
        System.out.println("SHOW_ALL_USERS - Отображение списка всех пользователей.");
        System.out.println("ACCOUNT_CREATE - Создание нового счета для пользователя.");
        System.out.println("ACCOUNT_CLOSE - Закрытие счета.");
        System.out.println("ACCOUNT_DEPOSIT - Пополнение счета.");
        System.out.println("ACCOUNT_TRANSFER - Перевод средств между счетами.");
        System.out.println("ACCOUNT_WITHDRAW - Снятие средств со счета.");
        System.out.println("HELP - Показать меню.");
    }

    private void handleUserCreate(BufferedReader reader) throws IOException {
        String login = ask(reader, "Введите логин: ");
        userService.createUser(login);
    }

    private void handleAccountCreate(BufferedReader reader) throws IOException {
        long userId = askPositiveLong(reader, "Введите userId: ");
        accountService.createAccount(userId);
    }

    private void handleAccountClose(BufferedReader reader) throws IOException {
        long accountId = askPositiveLong(reader, "Введите accountId: ");
        accountService.closeAccount(accountId);
    }

    private void handleAccountDeposit(BufferedReader reader) throws IOException {
        long accountId = askPositiveLong(reader, "Введите accountId: ");
        double amount = askPositiveDouble(reader, "Введите сумму: ");
        accountService.accountDeposit(accountId, amount);
    }

    private void handleAccountTransfer(BufferedReader reader) throws IOException {
        long fromAccountId = askPositiveLong(reader, "С какого accountId перевести: ");
        long toAccountId = askPositiveLong(reader, "На какой accountId перевести: ");
        double amount = askPositiveDouble(reader, "Введите сумму: ");
        accountService.accountTransfer(fromAccountId, toAccountId, amount);
    }

    private void handleAccountWithdraw(BufferedReader reader) throws IOException {
        long accountId = askPositiveLong(reader, "Введите accountId: ");
        double amount = askPositiveDouble(reader, "Введите сумму: ");
        accountService.accountWithdraw(accountId, amount);
    }

    private String ask(BufferedReader reader, String command) throws IOException {
        while (true) {
            System.out.print(command);
            String s = reader.readLine();
            if (s != null && !s.trim().isEmpty()) return s.trim();
            System.out.println("Пустое значение. Повторите ввод.");
        }
    }

    private long askLong(BufferedReader reader, String command) throws IOException {
        while (true) {
            String s = ask(reader, command);
            try {
                if (Long.parseLong(s) <= 0) {
                    throw new NumberFormatException("Число не может быть отрицательным");
                } else {
                    return Long.parseLong(s);
                }
            } catch (NumberFormatException e) {
                System.out.println("Нужно целое число. Повторите ввод.");
            }
        }
    }

    private double askDouble(BufferedReader reader, String command) throws IOException {
        while (true) {
            String s = ask(reader, command).replace(',', '.');
            try {
                if (Double.parseDouble(s) <= 0) {
                    throw new NumberFormatException("Число не может быть отрицательным");
                } else {
                    return Double.parseDouble(s);
                }
            } catch (NumberFormatException e) {
                System.out.println("Нужно число. Повторите ввод.");
            }
        }
    }

    private long askPositiveLong(BufferedReader reader, String prompt) throws IOException {
        while (true) {
            long value = askLong(reader, prompt);
            if (value > 0) {
                return value;
            }
            System.out.println("Значение должно быть больше 0.");
        }
    }

    private double askPositiveDouble(BufferedReader reader, String prompt) throws IOException {
        while (true) {
            double value = askDouble(reader, prompt);
            if (value > 0) {
                return value;
            }
            System.out.println("Сумма должна быть больше 0.");
        }
    }
}
