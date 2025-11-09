package org.example.Amazon;

import org.example.Amazon.Cost.DeliveryPrice;
import org.example.Amazon.Cost.ExtraCostForElectronics;
import org.example.Amazon.Cost.ItemType;
import org.example.Amazon.Cost.RegularCost;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AmazonUnitTest {
    @Test
    @DisplayName("specification-based: delivery price with 0 items returns 0 dollar delivery fee")
    public void testDeliveryPriceWithZeroItems() {
        // create a DeliveryPrice instance
        DeliveryPrice deliveryPrice = new DeliveryPrice();

        // create an empty list representing a cart with no items
        List<Item> emptyCart = new ArrayList<>();

        // call priceToAggregate with the empty cart
        double result = deliveryPrice.priceToAggregate(emptyCart);

        // verify that 0 items returns 0 dollar delivery fee
        assertEquals(0.0, result, "0 items should returns 0 dollar delivery fee");
    }

    @Test
    @DisplayName("specification-based: delivery price with 1 item returns 5 dollar delivery fee ")
    public void testDeliveryPriceWithOneItem() {
        // create a DeliveryPrice instance
        DeliveryPrice deliveryPrice = new DeliveryPrice();

        // create a list wth 1 item
        List<Item> cart = new ArrayList<>();
        cart.add(new Item(ItemType.OTHER, "Book", 1, 20.0));

        // call priceToAggregate with the cart containing 1 item
        double result = deliveryPrice.priceToAggregate(cart);

        // verify that 1 item returns 5 dollar delivery fee
        assertEquals(5.0, result, "1 item should return 5 dollar delivery fee");
    }

    @Test
    @DisplayName("specification-based: delivery price with 3 items returns 5 dollar delivery fee ")
    public void testDeliveryPriceWithThreeItems() {
        // create a DeliveryPrice instance
        DeliveryPrice deliveryPrice = new DeliveryPrice();

        // create a list wth 3 items
        List<Item> cart = new ArrayList<>();
        cart.add(new Item(ItemType.OTHER, "Book", 1, 20.0));
        cart.add(new Item(ItemType.OTHER, "Pencil", 1, 5.0));
        cart.add(new Item(ItemType.OTHER, "Notebook", 1, 15.0));

        // call priceToAggregate with the cart containing 3 items
        double result = deliveryPrice.priceToAggregate(cart);

        // verify that 3 items returns 5 dollar delivery fee
        assertEquals(5.0, result, "3 items should return 5 dollar delivery fee");
    }

    @Test
    @DisplayName("specification-based: delivery price with 4 items returns 12.50 dollar delivery fee ")
    public void testDeliveryPriceWithFourItems() {
        // create a DeliveryPrice instance
        DeliveryPrice deliveryPrice = new DeliveryPrice();

        // create a list wth 4 items
        List<Item> cart = new ArrayList<>();
        cart.add(new Item(ItemType.OTHER, "Book", 1, 20.0));
        cart.add(new Item(ItemType.OTHER, "Pencil", 1, 5.0));
        cart.add(new Item(ItemType.OTHER, "Notebook", 1, 15.0));
        cart.add(new Item(ItemType.OTHER, "Pen", 1, 10.0));

        // call priceToAggregate with the cart containing 4 items
        double result = deliveryPrice.priceToAggregate(cart);

        // verify that 4 items returns 12.50 dollar delivery fee
        assertEquals(12.5, result, "4 items should return 12.50 dollar delivery fee");
    }

    @Test
    @DisplayName("specification-based: delivery price with 10 items returns 12.50 dollar delivery fee ")
    public void testDeliveryPriceWithTenItems() {
        // create a DeliveryPrice instance
        DeliveryPrice deliveryPrice = new DeliveryPrice();

        // create a list wth 10 items
        List<Item> cart = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            cart.add(new Item(ItemType.OTHER, "Book" + i, 1, 20.0));
        }

        // call priceToAggregate with the cart containing 10 items
        double result = deliveryPrice.priceToAggregate(cart);

        // verify that 10 items returns 12.50 dollar delivery fee
        assertEquals(12.5, result, "10 items should return 12.50 dollar delivery fee");
    }

    @Test
    @DisplayName("specification-based: delivery price with 11 items returns 20 dollar delivery fee ")
    public void testDeliveryPriceWithElevenItems() {
        // create a DeliveryPrice instance
        DeliveryPrice deliveryPrice = new DeliveryPrice();

        // create a list wth 11 items
        List<Item> cart = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            cart.add(new Item(ItemType.OTHER, "Book" + i, 1, 20.0));
        }

        // call priceToAggregate with the cart containing 11 items
        double result = deliveryPrice.priceToAggregate(cart);

        // verify that 11 items returns 20 dollar delivery fee
        assertEquals(20.0, result, "11 items should return 20 dollar delivery fee");
    }

    @Test
    @DisplayName("specification-based: delivery price with electronic items returns 7.50 dollar delivery fee ")
    public void testDeliveryPriceWithElectronics() {
        // create a ExtraCostForElectronics instance
        ExtraCostForElectronics electronicsFee = new ExtraCostForElectronics();

        // create a list with an electronic item
        List<Item> cart = new ArrayList<>();
        cart.add(new Item(ItemType.ELECTRONIC, "iPhone", 1, 1000.0));

        // call priceToAggregate with the cart containing an electronic item
        double result = electronicsFee.priceToAggregate(cart);

        // verify that a cart with an electronic items returns 7.50 dollar delivery fee
        assertEquals(7.5, result, "Cart with an electronic item should return 7.50 dollar delivery fee");
    }

    @Test
    @DisplayName("specification-based: delivery price without an electronic item returns 0 dollar extra fee ")
    public void testDeliveryPriceWithoutElectronics() {
        // create a ExtraCostForElectronics instance
        ExtraCostForElectronics electronicsFee = new ExtraCostForElectronics();

        // create a list without an electronic item
        List<Item> cart = new ArrayList<>();
        cart.add(new Item(ItemType.OTHER, "Book", 1, 20.0));

        // call priceToAggregate with the cart NOT containing an electronic item
        double result = electronicsFee.priceToAggregate(cart);

        // verify that a cart without  an electronic items returns 0 dollar delivery fee
        assertEquals(0.0, result, "Cart without an electronic item should return 0 dollar extra fee");
    }

    @Test
    @DisplayName("specification-based: regular cost calculates correct total for multiple items")
    public void testRegularCostCalculatesCorrectTotal() {
        // create a RegularCost instance
        RegularCost regularCost = new RegularCost();

        // create list containing multiple items
        List<Item> cart = new ArrayList<>();
        cart.add(new Item(ItemType.OTHER, "Notebook", 2, 15.0));
        cart.add(new Item(ItemType.OTHER, "Pencil", 3, 5.0));

        // call priceToAggregate wit hthe cart
        double result = regularCost.priceToAggregate(cart);

        // verify that the total is (2 * 15) + ( 3 * 5) = 30 + 15 = 45 dollars
        assertEquals(45.0, result, "Regular cost should be 45 dollars for (2 * 15) + (3 * 5)");

    }
}
