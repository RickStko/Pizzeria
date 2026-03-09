package tests;

import org.example.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PizzeriaTests {

    private Pizza testPizza;
    private Order testOrder;

    @BeforeEach
    void setUp() {
        testPizza = new Pizza("Test", Map.of("Cheese", 100, "Tomato", 50), 200, 10);
        testOrder = new Order(12345);
    }

    @Test
    void testPizzaCalculateCalories_Valid() {
        assertEquals(150, testPizza.calculateCalories());
    }

    @Test
    void testPizzaCalculateCalories_Empty() {
        Pizza emptyPizza = new Pizza("Empty", new HashMap<>(), 100, 5);
        assertEquals(0, emptyPizza.calculateCalories());
    }

    @Test
    void testOrderAddPizza_Valid() {
        testOrder.addPizza(testPizza);
        assertEquals(1, testOrder.getPizzas().size());
    }

    @Test
    void testOrderAddPizza_NullThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> testOrder.addPizza(null));
        assertEquals("Pizza cannot be null", exception.getMessage());
    }

    @Test
    void testOrderCalculateTotalBill() {
        testOrder.addPizza(testPizza);
        testOrder.addPizza(new Pizza("Test2", Map.of(), 300, 10));
        assertEquals(500, testOrder.calculateTotalBill());
    }

    @Test
    void testManagerSortByPrice() {
        PizzeriaManager manager = new PizzeriaManager();
        Order order1 = new Order(1); order1.addPizza(new Pizza("A", Map.of(), 100, 5));
        Order order2 = new Order(2); order2.addPizza(new Pizza("B", Map.of(), 500, 5));

        manager.addOrderToArchive(order1);
        manager.addOrderToArchive(order2);

        List<Order> sorted = manager.getOrdersSortedBy(Comparator.comparingInt(Order::calculateTotalBill).reversed());
        assertEquals(2, sorted.getFirst().getReceiptNumber());
    }

    @Test
    void testManagerSortByDate() throws InterruptedException {
        PizzeriaManager manager = new PizzeriaManager();
        Order orderOld = new Order(1);
        Thread.sleep(10);
        Order orderNew = new Order(2);

        manager.addOrderToArchive(orderNew);
        manager.addOrderToArchive(orderOld);

        List<Order> sorted = manager.getOrdersSortedBy(Comparator.comparing(Order::getDate));
        assertEquals(1, sorted.getFirst().getReceiptNumber());
    }

    @Test
    void testExportData_WithMock() throws IOException {
        FileStorage mockStorage = Mockito.mock(FileStorage.class);
        List<Order> orders = List.of(testOrder);

        mockStorage.exportData(orders, "test_archive.csv");

        verify(mockStorage, times(1)).exportData(orders, "test_archive.csv");
    }

    @Test
    void testImportData_WithMock() throws IOException {
        FileStorage mockStorage = Mockito.mock(FileStorage.class);

        when(mockStorage.importData("test_archive.csv")).thenReturn(List.of(testOrder));

        List<Order> result = mockStorage.importData("test_archive.csv");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testOrder.getReceiptNumber(), result.getFirst().getReceiptNumber());
    }
}
