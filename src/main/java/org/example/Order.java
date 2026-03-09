package org.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order {
    private int receiptNumber;
    private LocalDateTime date;
    private boolean statusReady;
    private List<Pizza> pizzas;

    public Order(int receiptNumber) {
        this.receiptNumber = receiptNumber;
        this.date = LocalDateTime.now();
        this.statusReady = false;
        this.pizzas = new ArrayList<>();
    }

    public void addPizza(Pizza pizza) {
        if (pizza == null) {
            throw new IllegalArgumentException("Pizza cannot be null");
        }
        pizzas.add(pizza);
    }

    public int calculateTotalBill() {
        return pizzas.stream().mapToInt(Pizza::getPrice).sum();
    }

    public int getReceiptNumber() { return receiptNumber; }
    public void setReceiptNumber(int receiptNumber) { this.receiptNumber = receiptNumber; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public boolean isStatusReady() { return statusReady; }
    public void setStatusReady(boolean statusReady) { this.statusReady = statusReady; }
    public List<Pizza> getPizzas() { return pizzas; }
    public void setPizzas(List<Pizza> pizzas) { this.pizzas = pizzas; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return receiptNumber == order.receiptNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(receiptNumber);
    }
}