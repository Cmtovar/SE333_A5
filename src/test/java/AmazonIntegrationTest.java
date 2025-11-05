package org.example.Amazon;

import org.example.Amazon.Cost.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class AmazonIntegrationTest {
    private static Database database;

    @BeforeAll
    static void setupDatabase() {
        database = new Database();
    }

    @AfterEach
    void clearDatabase() {
        database.resetDatabase();
    }

    @Test
    @DisplayName("specification-based")
    public void testEmptyCartDelivery(){
        //Arrange
        ShoppingCartAdaptor cart = new ShoppingCartAdaptor(database);
        List<PriceRule> rules = List.of(new DeliveryPrice());
        Amazon amazon = new Amazon(cart, rules);

        //Act
        double result = amazon.calculate();
        //Assert
        assertEquals(0.0, result, "Empty cart should return 0 dollar delivery fee");
    }


}
