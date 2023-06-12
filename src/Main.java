import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Введите арифметическую операцию (для завершение программы введите Q или q): ");
            String input = scanner.nextLine();

            if (input.equals("Q") || input.equals("q")) {
                System.out.println("Завершение программы...");
                break;
            }

            try {
                String result = calc(input);
                System.out.println(result);
            } catch (Exception e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
    }

    public static String calc(String input) throws Exception {
        String[] parts = input.trim().split(" ");

        if (parts.length == 1) {
            throw new Exception("Строка не является математической операцией!");
        }
        if (parts.length == 5) {
            throw new Exception("Формат математической операции не удовлетворяет заданию - два операнда и один оператор!");
        }
        if (parts.length != 3) {
            throw new Exception("Некорректное выражение!");
        }

        String operand1 = parts[0];
        String operator = parts[1];
        String operand2 = parts[2];

        int num1, num2;
        boolean isRoman = false;

        try {
            num1 = Integer.parseInt(operand1);
            num2 = Integer.parseInt(operand2);
        } catch (NumberFormatException e) {
            num1 = RomanToArabic(operand1);
            num2 = RomanToArabic(operand2);
            isRoman = true;
        }

        if (num1 < 1 || num1 > 10 || num2 < 1 || num2 > 10) {
            throw new Exception("Числа должны быть от 1 до 10");
        }

        int result;
        switch (operator) {
            case "+":
                result = num1 + num2;
                break;
            case "-":
                result = num1 - num2;
                break;
            case "*":
                result = num1 * num2;
                break;
            case "/":
                result = num1 / num2;
                break;
            default:
                throw new Exception("Некорректная операция");
        }

        if (isRoman) {
            if (result < 1) {
                throw new Exception("В римской системе нет отрицательных чисел!");
            }
            return ArabicToRoman(result);
        } else {
            return Integer.toString(result);
        }
    }

    private static int RomanToArabic(String roman) throws Exception {
        Map<Character, Integer> romanToArabic = new HashMap<>();
        romanToArabic.put('I', 1);
        romanToArabic.put('V', 5);
        romanToArabic.put('X', 10);

        int result = 0;
        int prevValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            char current = roman.charAt(i);
            int currentValue = romanToArabic.getOrDefault(current, 0);

            if (currentValue == 0) {
                throw new Exception("Используются одновременно разные системы счисления!");
            }

            if (currentValue < prevValue) {
                result -= currentValue;
            } else {
                result += currentValue;
                prevValue = currentValue;
            }
        }

        return result;
    }

    private static String ArabicToRoman(int number) {
        if (number < 1) {
            return "";
        }

        String[] romanNumerals = {"X", "IX", "V", "IV", "I"};
        int[] arabicValues = {10, 9, 5, 4, 1};

        StringBuilder result = new StringBuilder();
        int i = 0;

        while (number > 0) {
            if (number >= arabicValues[i]) {
                result.append(romanNumerals[i]);
                number -= arabicValues[i];
            } else {
                i++;
            }
        }
        return result.toString();
    }

}
