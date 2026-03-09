package data;

import org.example.Pizza;
import java.util.Map;

import java.util.List;

public class Stock {
    public static List<Pizza> getMenuList() {
        return List.of(
                new Pizza("Margarita", Map.of("Tomato Sauce", 50, "Mozzarella", 250, "Basil", 5), 180, 15),
                new Pizza("Pepperoni", Map.of("Tomato Sauce", 50, "Mozzarella", 200, "Pepperoni", 300), 220, 12),
                new Pizza("Four Cheese", Map.of("Mozzarella", 150, "Parmesan", 100, "Gorgonzola", 120, "Fontina", 110), 280, 10),
                new Pizza("Hawaiian", Map.of("Ham", 120, "Pineapple", 60, "Mozzarella", 180, "Tomato Sauce", 40), 210, 14),
                new Pizza("Vegetarian", Map.of("Mushrooms", 40, "Bell Peppers", 30, "Olives", 50, "Corn", 60, "Tomato Sauce", 50), 190, 15),
                new Pizza("Meat Lover's BBQ", Map.of("Bacon", 250, "Grilled Chicken", 180, "BBQ Sauce", 80, "Red Onion", 10), 310, 18),
                new Pizza("Seafood Special", Map.of("Shrimp", 90, "Mussels", 80, "Garlic Sauce", 120, "Mozzarella", 150), 350, 20)
        );
    }
}
