package org.example;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Database implements FileStorage {
    @Override
    public void exportData(List<Order> orders, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("ReceiptNumber,Date,TotalBill,StatusReady\n");
            for (Order order : orders) {
                writer.write(String.format("%d,%s,%d,%b\n",
                        order.getReceiptNumber(),
                        order.getDate().toString(),
                        order.calculateTotalBill(),
                        order.isStatusReady()));
            }
        }
    }

    @Override
    public List<Order> importData(String filePath) throws IOException {
        List<Order> importedOrders = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    Order order = new Order(Integer.parseInt(parts[0]));
                    order.setDate(LocalDateTime.parse(parts[1]));
                    order.setStatusReady(Boolean.parseBoolean(parts[3]));
                    importedOrders.add(order);
                }
            }
        }
        return importedOrders;
    }
}
