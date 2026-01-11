package ru.makan1.bankapp.service.menu;

import java.io.BufferedReader;
import java.io.IOException;

public final class ConsoleInput {

    public ConsoleInput() {
    }

    public static String ask(BufferedReader reader, String command) throws IOException {
        while (true) {
            System.out.print(command);
            String s = reader.readLine();
            if (s != null && !s.trim().isEmpty()) return s.trim();
            System.out.println("Пустое значение. Повторите ввод.");
        }
    }

    public static long askLong(BufferedReader reader, String command) throws IOException {
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

    public static double askDouble(BufferedReader reader, String command) throws IOException {
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

    public static long askPositiveLong(BufferedReader reader, String prompt) throws IOException {
        while (true) {
            long value = askLong(reader, prompt);
            if (value > 0) {
                return value;
            }
            System.out.println("Значение должно быть больше 0.");
        }
    }

    public static double askPositiveDouble(BufferedReader reader, String prompt) throws IOException {
        while (true) {
            double value = askDouble(reader, prompt);
            if (value > 0) {
                return value;
            }
            System.out.println("Сумма должна быть больше 0.");
        }
    }
}
