package org.example;

import java.io.IOException;
import java.util.List;

public interface FileStorage {
    void exportData(List<Order> orders, String filePath) throws IOException;
    List<Order> importData(String filePath) throws IOException;
}
