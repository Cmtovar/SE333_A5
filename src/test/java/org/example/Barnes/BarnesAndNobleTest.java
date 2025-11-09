package org.example.Barnes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BarnesAndNobleTest {
    @Test
    @DisplayName("specification-based: null order returns null")
    public void testNullOrderReturnsNull() {
        // create a mock BookDatabase and mock BuyBookProcess
        BookDatabase mockDatabase = mock(BookDatabase.class);
        BuyBookProcess mockProcess = mock(BuyBookProcess.class);

        // create BarnesAndNoble instance with mocked dependencies
        BarnesAndNoble barnesAndNoble = new BarnesAndNoble(mockDatabase, mockProcess);

        // call getPriceForCart using null as the order parameter
        PurchaseSummary result = barnesAndNoble.getPriceForCart(null);

        // Verify that the result is null when given a null order
        assertEquals(null, result, "Null order should return null");
    }

    @Test
    @DisplayName("Specification-based: empty order returns zero total price")
    public void testEmptyOrderReturnsZeroTotalPrice() {
        // create a mock BookDatabase and mock BuyBookProcess
        BookDatabase mockDatabase = mock(BookDatabase.class);
        BuyBookProcess mockProcess = mock(BuyBookProcess.class);

        //Create BarnesAndNoble instance with mocked dependencies
        BarnesAndNoble barnesAndNoble = new BarnesAndNoble(mockDatabase, mockProcess);

        // create empty hashmap - represents an order with no books
        Map<String, Integer> emptyOrder = new HashMap<>();

        // call getPriceForCart with the empty order
        PurchaseSummary result = barnesAndNoble.getPriceForCart(emptyOrder);

        // Verify that the total price is zero for an empty order
        assertEquals(0, result.getTotalPrice(), "Empty order should have zero total price");
        // Verfiy that there are no unavailable items for an empty order
        assertTrue(result.getUnavailable().isEmpty(), "Empty order should have no unavailable items");
    }

    @Test
    @DisplayName("specification-based: valid order with sufficient stock calculates correct price")
    public void testValidOrderWithSufficientStockCalculatesCorrectPrice() {
        // create a mock BookDatabase and mock BuyBookProcess
        BookDatabase mockDatabase = mock(BookDatabase.class);
        BuyBookProcess mockProcess = mock(BuyBookProcess.class);

        // create a book object with sample data
        Book book = new Book("123456789", 8,5);

        // stub the mock database to return this book when findByISBN is called
        // this mimics a database response for book whenever specifically "123456789" is searched for
        when(mockDatabase.findByISBN("123456789")).thenReturn(book);

        // create hashmap which represents an order for 3 books with ISBN "123456789
        Map<String, Integer> order = new HashMap<>();
        order.put("123456789", 3);

        // create BarnesAndNoble instance with mocked dependencies
        BarnesAndNoble barnesAndNoble = new BarnesAndNoble(mockDatabase, mockProcess);

        // call getPriceForCart with the order
        PurchaseSummary result = barnesAndNoble.getPriceForCart(order);

        // Verify that the total price is $24 (3 books x 8 dollars each)
        assertEquals(24, result.getTotalPrice(), "3 books at 8 dollars each should return 24 dollars total");
        // Verify that there are no unavailable items (since there are more than 3 books in stock)
        assertTrue(result.getUnavailable().isEmpty(), "Order with suffient stock should have no unavailable items");
    }

    @Test
    @DisplayName("specification-based: insufficient stock adds unavailable items to summary")
    public void testInsufficientStockAddsUnavailableItems() {
        // create a mock BookDatabase, and mock BuyBookProcess
        BookDatabase mockDatabase = mock(BookDatabase.class);
        BuyBookProcess mockProcess = mock(BuyBookProcess.class);

        // create a book object with sample data
        Book book = new Book("123456789", 8,5);

        // stub the mock database to return this book when findByISBN is called
        // this mimics a database response for book whenever specifically "123456789" is searched for
        when(mockDatabase.findByISBN("123456789")).thenReturn(book);

        // create hashmap which represents an order for 3 books with ISBN "123456789
        Map<String, Integer> order = new HashMap<>();
        order.put("123456789", 10);

        // create BarnesAndNoble instance with mocked dependencies
        BarnesAndNoble barnesAndNoble = new BarnesAndNoble(mockDatabase, mockProcess);

        // call getPriceForCart with the order
        PurchaseSummary result = barnesAndNoble.getPriceForCart(order);

        // verify that the total price is $40 (5 books x 8 dollars each)
        assertEquals(40, result.getTotalPrice(), "5 books at 8 dollars each should return 40 dollars total");
        // verify that the unavailable map contains exactly 1 entry
        assertEquals(1, result.getUnavailable().size(), "Should have 1 book in unavailable list");
        // verify that the unavailable map contains this specific book
        assertTrue(result.getUnavailable().containsKey(book), "Unavailable list should contain this book");
        // verify that the unavailable quantity is 5 (10 is requested yet there are only 5 available; 5 should remain)
        assertEquals(5, result.getUnavailable().get(book), "Should have 5 unavailable items");
    }

    @Test
    @DisplayName("structural-based: multiple books in order tests looop iteration coverage")
    public void testMultipleBooksInOrderIteratesCorrectly() {
        // create a mock BookDatabase, and mock BuyBookProcess
        BookDatabase mockDatabase = mock(BookDatabase.class);
        BuyBookProcess mockProcess = mock(BuyBookProcess.class);

        // create a book object with sample data
        Book book1 = new Book("111111111", 8,5);

        // stub the mock database to return this book when findByISBN is called
        // this mimics a database response for book whenever specifically "111111111" is searched for
        when(mockDatabase.findByISBN("111111111")).thenReturn(book1);

        // create a book object with sample data
        Book book2 = new Book("222222222", 10,7);

        // stub the mock database to return this book when findByISBN is called
        // this mimics a database response for book whenever specifically "222222222" is searched for
        when(mockDatabase.findByISBN("222222222")).thenReturn(book2);

        // create hashmap which represents an order for two different books
        Map<String, Integer> order = new HashMap<>();
        // Add order for 2 copies of book1
        order.put("111111111", 2);
        // Add order for 3 copies of book2
        order.put("222222222", 3);

        // create BarnesAndNoble instance with mocked dependencies
        BarnesAndNoble barnesAndNoble = new BarnesAndNoble(mockDatabase, mockProcess);

        // call getPriceForCart with the order
        PurchaseSummary result = barnesAndNoble.getPriceForCart(order);

        // verify that the total price is $46 = (2 books x 8 dollars each) + (3 books x 10 dollars each)
        assertEquals(46, result.getTotalPrice(), "Multiple books should calculate combined total price");
        // verify that no items are unavailable since both have sufficient stock
        assertEquals(0, result.getUnavailable().size(), "All books have suffiecient stock");
    }
}
