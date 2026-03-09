package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PizzeriaManager {
    private final List<Order> archive = new ArrayList<>();

    public void addOrderToArchive(Order order) {
        if (order == null) throw new IllegalArgumentException("Order cannot be null");
        archive.add(order);
    }

    public List<Order> getArchive() {
        return new ArrayList<>(archive);
    }

    public List<Order> getOrdersSortedBy(Comparator<Order> comparator) {
        List<Order> sortedList = new ArrayList<>(archive);
        sortedList.sort(comparator);
        return sortedList;
    }
}