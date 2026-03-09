package org.example;

import java.util.Map;
import java.util.*;

public class Pizza {
    private String name;
    private Map<String, Integer> ingredients;
    private int price;
    private int timeToCook;

    public Pizza(String name, Map<String, Integer> ingredients, int price, int timeToCook) {
        this.name = name;
        this.ingredients = new HashMap<>(ingredients);
        this.price = price;
        this.timeToCook = timeToCook;
    }

    public int calculateCalories() {
        if (ingredients == null || ingredients.isEmpty()) return 0;
        return ingredients.values().stream().mapToInt(Integer::intValue).sum();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Map<String, Integer> getIngredients() { return ingredients; }
    public void setIngredients(Map<String, Integer> ingredients) { this.ingredients = ingredients; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    public int getTimeToCook() { return timeToCook; }
    public void setTimeToCook(int timeToCook) { this.timeToCook = timeToCook; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pizza pizza = (Pizza) o;
        return price == pizza.price && timeToCook == pizza.timeToCook && Objects.equals(name, pizza.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, timeToCook);
    }
}
