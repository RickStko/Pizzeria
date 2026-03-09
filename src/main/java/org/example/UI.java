package org.example;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ConsoleNavigation {
    public static void clean() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}

public class UI {

    public static final String START_SCREEN = """
            ▄▖▄▖▄▖▄▖▄▖▄▖▄▖▄▖
            ▙▌▐ ▗▘▗▘▙▖▙▘▐ ▌▌
            ▌ ▟▖▙▖▙▖▙▖▌▌▟▖▛▌
           
           1 [Make an order]
           2 [Switch to Admin]
           3 [Exit]""";

    public static final String READY_SCREEN = """
                   ______
              .---<__. \\ \\
              `---._  \\ \\ \\
               ,----`- `.))
              / ,--.   )  |
             /_/    >     |
             |,\\__-'      |
              \\_           \\
                ~~-___      )
                      \\      \\
            """;

    public static void showMenu(List<Pizza> list) {
        int counter = 0;
        for (Pizza pizza : list) {
            counter++;
            System.out.println("[" + counter + "] " + pizza.getName());
            System.out.print("Ingredients: ");
            for (Map.Entry<String, Integer> entry : pizza.getIngredients().entrySet()) {
                System.out.print(entry.getKey() + " - " + entry.getValue() + "kcal, ");
            }
            System.out.print("\b\b  \b\b\n");
            System.out.println("Price: " + pizza.getPrice() + "hrn");
            System.out.println("Time to cook: " + pizza.getTimeToCook() + "m\n");
        }
        System.out.println("Make an Order (Choose - [1 - " + counter + "], Finish - f, Delete last - d)");
    }

    public static void showConfirmation(Order order) {
        ConsoleNavigation.clean();
        System.out.println("""
                ▄▖    ▐▘▘                      ▌    ▄▖
                ▌ ▛▌▛▌▜▘▌▛▘▛▛▌  ▌▌▛▌▌▌▛▘  ▛▌▛▘▛▌█▌▛▘▗▘
                ▙▖▙▌▌▌▐ ▌▌ ▌▌▌  ▙▌▙▌▙▌▌   ▙▌▌ ▙▌▙▖▌ ▗\s
                                ▄▌                   \s
                """);

        Map<Pizza, Integer> counts = new HashMap<>();
        for (Pizza p : order.getPizzas()) {
            counts.put(p, counts.getOrDefault(p, 0) + 1);
        }

        int totalTime = order.getPizzas().stream().mapToInt(Pizza::getTimeToCook).sum();
        int cost = order.calculateTotalBill();

        List<Pizza> distinctPizzas = order.getPizzas().stream().distinct().toList();
        for (Pizza pizza : distinctPizzas) {
            System.out.println(pizza.getName() + " - " + counts.get(pizza) + "x");
        }

        System.out.println("\nOverall time to cook: " + totalTime + "m");
        System.out.println("Total Bill: " + cost + "hrn\n");
        System.out.println("1 [Pay & Confirm]                  2 [Cancel]");
    }

    public static void showMakingProcess(Order order) {
        ConsoleNavigation.clean();
        System.out.println("""
                ░░░░░░░░░░░░░░░░░░░░░░░░░░░░▄▄▄░░░░░░░░░
                ░░░░░░░░░░░░░░▄▄▄▄▄▄▄▄░░░░░░█████▄░░░░░░
                ░░░░░░░░░▄▄█████████████▀░░▄░░▀████▄░░░░
                ░░░░░░▄▄███▀▀░░▄▄▄▄▄▄░░▀░░████▄░▀████░░░
                ░░░░▄███▀░▄▄██████▀▀███░░▄█▀▀░▀▀▄░▀███▄░
                ░░░▄██▀░▄███▀▀▀███▄▄██░░▄██░░░░░█▄░▀███░
                ░░███░▄██████████████▀░░███▄░░░▄██▄░████
                ░███░▄████░█████░▀██▀░░█████████▀▀▀░░░░░
                ▄██░░██████▀▀███████░░▀▀▀▀▀░░░░░░░░░░░░░
                ███░██████░░░░█████░░░░░░░░░▄▄▄▄░░██░░░░
                ██░░██████▄░░▄█████▄▄▄█████▀▀███░░██░░░░
                ███░███▄░▀█████▀▀███████████████░░██░░░░
                ███░▀██████▀████▄▄█████▄▄███▀███░▄██░░░░
                ░██▄░██████▄▄██████▀▀▀█████▄▄██░░██▀░░░░
                ░▀██▄░▀█▀▀████████░░░░░███████░░██▀░░░░░
                ░░▀██▄░▀███████████▄░▄▄█████▀░▄██▀░░░░░░
                ░░░░▀██▄░░▀███▄▄▄████████▀▀░▄███▀░░░░░░░
                ░░░░░░▀███▄░░▀▀▀▀▀▀▀▀▀▀░░▄▄███▀░░░░░░░░░
                ░░░░░░░░▀▀████▄▄▄▄▄▄▄▄█████▀░░░░░░░░░░░░
                ░░░░░░░░░░░░░▀▀▀▀▀▀▀▀▀▀░░░░░░░░░░░░░░░░░
                """);

        System.out.println("Receipt Number: #" + order.getReceiptNumber() + ". Please wait...");

        int totalTime = order.getPizzas().stream().mapToInt(Pizza::getTimeToCook).sum();
        long sleepTime = (totalTime > 0) ? totalTime * 10L : 100L;

        for (int i = 1; i <= 10; i++) {
            System.out.print("███ ");
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException ignored) {}
        }

        ConsoleNavigation.clean();
        System.out.println(READY_SCREEN);
        System.out.println("Your order #" + order.getReceiptNumber() + " is ready!");
        System.out.println("\nPress Enter to return to main menu...");
    }

    public static void printArchive(List<Order> orders) {
        ConsoleNavigation.clean();
        System.out.println("=== ORDER ARCHIVE ===");
        if (orders.isEmpty()) {
            System.out.println("The archive is empty.");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for (Order o : orders) {
            System.out.println("Receipt: #" + o.getReceiptNumber()
                    + " | Date: " + o.getDate().format(formatter)
                    + " | Total: " + o.calculateTotalBill() + "hrn"
                    + " | Status: " + (o.isStatusReady() ? "Ready" : "In Progress"));
        }
        System.out.println("=====================");
    }

    public static void clearScreen() {
        ConsoleNavigation.clean();
    }
}