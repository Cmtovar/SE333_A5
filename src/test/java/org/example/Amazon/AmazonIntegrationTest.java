package org.example.Amazon;

import org.example.Amazon.Cost.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AmazonIntegrationTest {

    private static Database database;

    @BeforeAll
    static void setupDatabase() {
        // initialize the databse once for all tests
        database = new Database();
    }

    @AfterEach
    void clearDatabase() {
        // reset the database after each test to isolate each test
        database.resetDatabase();
    }


    @Test
    @DisplayName("specification-based: empty cart returns 0 dollar total cost")
    public void testEmptyCartReturnsZeroTotal() {
        // create a ShoppingCartAdaptor with real database
        // database for all tests is initialized in the @BeforeAll tag
        ShoppingCartAdaptor cart = new ShoppingCartAdaptor(database);

        // create a list of all price rules
        List<PriceRule> rules = new ArrayList<>();
        rules.add(new RegularCost());
        rules.add(new DeliveryPrice());
        rules.add(new ExtraCostForElectronics());

        //create Amazon instance with cart and rules
        Amazon amazon = new Amazon(cart, rules);

        // calculate the total price for an empty cart
        double result = amazon.calculate();

        // verify that an empty cart returns 0 dollar total cost
        assertEquals(0.0, result, "Empty cart should return 0 dollar total cost");

        // database is cleaned at the end of every test in the @AfterEach tag
    }

    @Test
    @DisplayName("specification-based: simple order calculates correct total with all fees")
    public void testSimpleOrderCalculatesCorrectTotal() {
        // create a ShoppingCartAdaptor with the real database
        ShoppingCartAdaptor cart = new ShoppingCartAdaptor(database);

        // add items to cart (uses real database)
        cart.add(new Item(ItemType.OTHER, "Notebook", 2, 15.0));
        cart.add(new Item(ItemType.OTHER, "Pencil", 3, 5.0));

        // create a list of all price rules
        List<PriceRule> rules = new ArrayList<>();
        rules.add(new RegularCost());
        rules.add(new DeliveryPrice());
        rules.add(new ExtraCostForElectronics());

        //create Amazon instance with cart and rules
        Amazon amazon = new Amazon(cart, rules);

        // calculate the total price for an empty cart
        double result = amazon.calculate();

        // verify that the total is (2 * 15) + (3 * 5) = 30 + 15 = 45 dollars (regular cost)
        //                      and  + 5 (delivery for 2 items)
        //                      and  + 0 (no electronics in cart)
        //                           = 50 dollars
        assertEquals(50.0, result, "Simple order should return 50 dollar total cost");
    }

    @Test
    @DisplayName("specification-based: order with electronics calculates correct total with all fees (including electronics fee)")
    public void testOrderWithElectronicsIncludesFee() {
        // create a ShoppingCartAdaptor with the real database
        ShoppingCartAdaptor cart = new ShoppingCartAdaptor(database);

        // add items to cart (uses real database)
        cart.add(new Item(ItemType.ELECTRONIC, "iPhone", 1, 1000.0));
        cart.add(new Item(ItemType.OTHER, "Book", 3, 20.0));

        // create a list of all price rules
        List<PriceRule> rules = new ArrayList<>();
        rules.add(new RegularCost());
        rules.add(new DeliveryPrice());
        rules.add(new ExtraCostForElectronics());

        //create Amazon instance with cart and rules
        Amazon amazon = new Amazon(cart, rules);

        // calculate the total price for an empty cart
        double result = amazon.calculate();

        // verify that the total is (1 * 1000) + (3 * 20) = 1000 + 60 = 1060 dollars (regular cost)
        //                      and  + 5 (delivery for 2 items)
        //                      and  + 7.5 (no electronics in cart)
        //                           = 1072.50 dollars
        assertEquals(1072.5, result, "Order with electronics should return 1072.5 dollar total cost");
    }

    @Test
    @DisplayName("structural-based: database close method executes without error")
    public void testDatabaseCloseMethod() {
        // I noticed there was a lack of coverage on the database close method in the jacoco report
        // this test exists to fill in that gap

        // create a separate database instance for this test
        // this isn't using the shared database so it doesn't affect other tests
        Database testDatabase = new Database();

        // call the close method for coverage of uncovered code path
        testDatabase.close();

        // verify that close executes without throwing an exception
        // if this line is reached, no exception occurred
        // since no exception occurred, test passes beyond this point
    }
}
