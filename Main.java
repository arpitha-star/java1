// ============================================
// Main Calculator Application
// ============================================
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        InputHandler inputHandler = new InputHandler();

        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║   ENHANCED CONSOLE CALCULATOR v1.0    ║");
        System.out.println("╚═══════════════════════════════════════╝\n");

        while (true) {
            try {
                displayMenu();
                int choice = inputHandler.getMenuChoice();

                if (choice == 0) {
                    System.out.println("\n✓ Thank you for using Calculator! Goodbye!");
                    break;
                }

                processChoice(choice, calculator, inputHandler);

            } catch (CalculatorException e) {
                System.out.println("\n✗ Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("\n✗ Unexpected error: " + e.getMessage());
            }

            System.out.println("\n" + "─".repeat(45));
        }

        inputHandler.close();
    }

    private static void displayMenu() {
        System.out.println("┌─ OPERATIONS ─────────────────────────────┐");
        System.out.println("│  1. Addition           6. Square Root    │");
        System.out.println("│  2. Subtraction        7. Power          │");
        System.out.println("│  3. Multiplication     8. Absolute Value │");
        System.out.println("│  4. Division           9. Factorial      │");
        System.out.println("│  5. Modulus           10. Memory Recall  │");
        System.out.println("│  0. Exit                                 │");
        System.out.println("└──────────────────────────────────────────┘");
        System.out.print("Enter your choice: ");
    }

    private static void processChoice(int choice, Calculator calc, InputHandler input)
            throws CalculatorException {
        double num1, num2, result;

        switch (choice) {
            case 1: // Addition
                num1 = input.getNumber("Enter first number: ");
                num2 = input.getNumber("Enter second number: ");
                result = calc.add(num1, num2);
                displayResult(num1 + " + " + num2, result);
                calc.storeInMemory(result);
                break;

            case 2: // Subtraction
                num1 = input.getNumber("Enter first number: ");
                num2 = input.getNumber("Enter second number: ");
                result = calc.subtract(num1, num2);
                displayResult(num1 + " - " + num2, result);
                calc.storeInMemory(result);
                break;

            case 3: // Multiplication
                num1 = input.getNumber("Enter first number: ");
                num2 = input.getNumber("Enter second number: ");
                result = calc.multiply(num1, num2);
                displayResult(num1 + " × " + num2, result);
                calc.storeInMemory(result);
                break;

            case 4: // Division
                num1 = input.getNumber("Enter numerator: ");
                num2 = input.getNumber("Enter denominator: ");
                result = calc.divide(num1, num2);
                displayResult(num1 + " ÷ " + num2, result);
                calc.storeInMemory(result);
                break;

            case 5: // Modulus
                num1 = input.getNumber("Enter first number: ");
                num2 = input.getNumber("Enter second number: ");
                result = calc.modulus(num1, num2);
                displayResult(num1 + " % " + num2, result);
                calc.storeInMemory(result);
                break;

            case 6: // Square Root
                num1 = input.getNumber("Enter number: ");
                result = calc.squareRoot(num1);
                displayResult("√" + num1, result);
                calc.storeInMemory(result);
                break;

            case 7: // Power
                num1 = input.getNumber("Enter base: ");
                num2 = input.getNumber("Enter exponent: ");
                result = calc.power(num1, num2);
                displayResult(num1 + "^" + num2, result);
                calc.storeInMemory(result);
                break;

            case 8: // Absolute Value
                num1 = input.getNumber("Enter number: ");
                result = calc.absoluteValue(num1);
                displayResult("|" + num1 + "|", result);
                calc.storeInMemory(result);
                break;

            case 9: // Factorial
                int n = input.getInteger("Enter non-negative integer: ");
                result = calc.factorial(n);
                displayResult(n + "!", result);
                calc.storeInMemory(result);
                break;

            case 10: // Memory Recall
                if (calc.hasMemory()) {
                    System.out.println("Memory: " + calc.recallMemory());
                } else {
                    System.out.println("Memory is empty!");
                }
                break;

            default:
                System.out.println("Invalid choice! Please select 0-10.");
        }
    }

    private static void displayResult(String operation, double result) {
        System.out.println("\n┌─ RESULT ─────────────────────────────────┐");
        System.out.printf("│  %s = %.4f%n", operation, result);
        System.out.println("│  (Stored in memory)                      │");
        System.out.println("└──────────────────────────────────────────┘");
    }
}


// ============================================
// Calculator Core Class
// ============================================
class Calculator {
    private double memory;
    private boolean hasMemoryValue;

    // Basic Operations
    public double add(double a, double b) {
        return a + b;
    }

    public double subtract(double a, double b) {
        return a - b;
    }

    public double multiply(double a, double b) {
        return a * b;
    }

    public double divide(double a, double b) throws CalculatorException {
        if (b == 0) {
            throw new CalculatorException("Division by zero is not allowed!");
        }
        return a / b;
    }

    public double modulus(double a, double b) throws CalculatorException {
        if (b == 0) {
            throw new CalculatorException("Modulus by zero is not allowed!");
        }
        return a % b;
    }

    // Advanced Operations
    public double squareRoot(double a) throws CalculatorException {
        if (a < 0) {
            throw new CalculatorException("Cannot calculate square root of negative number!");
        }
        return Math.sqrt(a);
    }

    public double power(double base, double exponent) {
        return Math.pow(base, exponent);
    }

    public double absoluteValue(double a) {
        return Math.abs(a);
    }

    public double factorial(int n) throws CalculatorException {
        if (n < 0) {
            throw new CalculatorException("Factorial is not defined for negative numbers!");
        }
        if (n > 20) {
            throw new CalculatorException("Factorial too large! Maximum value is 20.");
        }

        double result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    // Memory Operations
    public void storeInMemory(double value) {
        this.memory = value;
        this.hasMemoryValue = true;
    }

    public double recallMemory() throws CalculatorException {
        if (!hasMemoryValue) {
            throw new CalculatorException("Memory is empty!");
        }
        return memory;
    }

    public boolean hasMemory() {
        return hasMemoryValue;
    }

    public void clearMemory() {
        this.memory = 0;
        this.hasMemoryValue = false;
    }
}


// ============================================
// Input Handler Class
// ============================================
class InputHandler {
    private Scanner scanner;

    public InputHandler() {
        this.scanner = new Scanner(System.in);
    }

    public int getMenuChoice() throws CalculatorException {
        try {
            String input = scanner.nextLine().trim();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new CalculatorException("Invalid input! Please enter a number.");
        }
    }

    public double getNumber(String prompt) throws CalculatorException {
        System.out.print(prompt);
        try {
            String input = scanner.nextLine().trim();
            return Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new CalculatorException("Invalid number format! Please enter a valid number.");
        }
    }

    public int getInteger(String prompt) throws CalculatorException {
        System.out.print(prompt);
        try {
            String input = scanner.nextLine().trim();
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new CalculatorException("Invalid integer! Please enter a whole number.");
        }
    }

    public void close() {
        scanner.close();
    }
}



// Custom Exception Class
// ============================================
class CalculatorException extends Exception {
    public CalculatorException(String message) {
        super(message);
}
}