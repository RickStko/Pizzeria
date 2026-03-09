package org.example;

import data.Stock;

import java.io.IOException;
import java.util.*;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static final PizzeriaManager manager = new PizzeriaManager();
    private static final Database storage = new Database();
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Pizza> menu = Stock.getMenuList();

    public static void main(String[] args) {
        while (true) {
            UI.clearScreen();
            System.out.println(UI.START_SCREEN);
            System.out.print("> ");
            String respond = scanner.nextLine();

            switch (respond) {
                case "1":
                    makeAnOrder();
                    break;
                case "2":
                    adminPanel();
                    break;
                case "3":
                case "exit":
                    UI.clearScreen();
                    System.out.println("Good bye! Have a nice day.");
                    return;
                default:
                    System.out.println("Please make a correct prompt. Press Enter to continue...");
                    scanner.nextLine();
            }
        }
    }

    private static void makeAnOrder() {
        UI.clearScreen();
        UI.showMenu(menu);

        int receiptNumber = new Random().nextInt(90000) + 10000;
        Order currentOrder = new Order(receiptNumber);

        System.out.print("> ");
        String respond = scanner.nextLine();

        while (!respond.equals("f")) {
            if (respond.equals("d")) {
                if (!currentOrder.getPizzas().isEmpty()) {
                    currentOrder.getPizzas().removeLast();
                    System.out.println("Last item removed.");
                } else {
                    System.out.println("Order is already empty!");
                }
            } else {
                try {
                    int num = Integer.parseInt(respond);
                    if (num >= 1 && num <= menu.size()) {
                        Pizza selectedPizza = menu.get(num - 1);
                        currentOrder.addPizza(selectedPizza);
                        System.out.println(selectedPizza.getName() + " added.");
                    } else {
                        System.out.println("Invalid pizza number. Please select between 1 and " + menu.size());
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Sorry, invalid input. Enter a number, 'd', or 'f'.");
                }
            }
            System.out.print("> ");
            respond = scanner.nextLine();
        }

        if (currentOrder.getPizzas().isEmpty()) {
            System.out.println("Order cancelled (empty). Press Enter to return.");
            scanner.nextLine();
            return;
        }

        UI.showConfirmation(currentOrder);
        System.out.print("> ");
        String confirmRespond = scanner.nextLine();

        if (confirmRespond.equals("1")) {
            UI.showMakingProcess(currentOrder);
            currentOrder.setStatusReady(true);
            manager.addOrderToArchive(currentOrder);
            scanner.nextLine();
        } else {
            System.out.println("Order cancelled. Press Enter to return.");
            scanner.nextLine();
        }
    }

    private static void adminPanel() {
        while (true) {
            UI.clearScreen();
            System.out.println("=== ADMIN PANEL ===");
            System.out.println("1 [View Archive]");
            System.out.println("2 [Sort Archive by Price]");
            System.out.println("3 [Sort Archive by Date]");
            System.out.println("4 [Export Archive to CSV]");
            System.out.println("5 [Import Archive from CSV]");
            System.out.println("6 [Exit to Main Menu]");
            System.out.print("> ");

            String respond = scanner.nextLine();

            switch (respond) {
                case "1":
                    UI.printArchive(manager.getArchive());
                    break;
                case "2":
                    UI.printArchive(manager.getOrdersSortedBy(
                            Comparator.comparingInt(Order::calculateTotalBill).reversed()
                    ));
                    break;
                case "3":
                    UI.printArchive(manager.getOrdersSortedBy(
                            Comparator.comparing(Order::getDate)
                    ));
                    break;
                case "4":
                    try {
                        List<Order> toExport = manager.getOrdersSortedBy(Comparator.comparing(Order::getDate));
                        storage.exportData(toExport, "archive.csv");
                        System.out.println("Successfully exported to archive.csv!");
                    } catch (IOException e) {
                        System.out.println("Error saving file: " + e.getMessage());
                    }
                    break;
                case "5":
                    try {
                        List<Order> importedOrders = storage.importData("archive.csv");
                        for (Order o : importedOrders) {
                            if (!manager.getArchive().contains(o)) {
                                manager.addOrderToArchive(o);
                            }
                        }
                        System.out.println("Successfully imported " + importedOrders.size() + " orders!");
                    } catch (IOException e) {
                        System.out.println("Error reading file: " + e.getMessage());
                    }
                    break;
                case "6":
                    return;
                default:
                    System.out.println("Wrong prompt.");
            }

            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }
}
