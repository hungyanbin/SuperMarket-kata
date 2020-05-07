package dojo.supermarket.model;

import dojo.supermarket.ReceiptPrinter;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SupermarketTest {

    // Todo: test all kinds of discounts are applied properly

    @Test
    public void tenPercentDiscount() {
        SupermarketCatalog catalog = new FakeCatalog();
        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 0.99);
        Product apples = new Product("apples", ProductUnit.Kilo);
        catalog.addProduct(apples, 1.99);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, toothbrush, 10.0);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(apples, 2.5);
        
        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        // ASSERT
        assertEquals(4.975, receipt.getTotalPrice(), 0.01);
        assertEquals(Collections.emptyList(), receipt.getDiscounts());
        assertEquals(1, receipt.getItems().size());
        ReceiptItem receiptItem = receipt.getItems().get(0);
        assertEquals(apples, receiptItem.getProduct());
        assertEquals(1.99, receiptItem.getPrice());
        assertEquals(2.5*1.99, receiptItem.getTotalPrice());
        assertEquals(2.5, receiptItem.getQuantity());

    }

    @Test
    void verify_toothbrush_not_add_special_odder_should_pay_2_97() {
        SupermarketCatalog catalog = new FakeCatalog();
        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 0.99);

        Teller teller = new Teller(catalog);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 3);

        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals(2.97, receipt.getTotalPrice(), 0.01);
    }

    @Test
    void verify_toothbrush_add_special_odder_should_pay_1_98() {
        SupermarketCatalog catalog = new FakeCatalog();
        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 0.99);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.ThreeForTwo, toothbrush, 1);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 3);

        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals(1.98, receipt.getTotalPrice(), 0.01);
    }

    //20% discount on apples, normal price €1.99 per kilo.
    @Test
    void verify_app_should_pay() {
        SupermarketCatalog catalog = new FakeCatalog();
        Product apples = new Product("apples", ProductUnit.Kilo);
        catalog.addProduct(apples, 1.99);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, apples, 20);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(apples, 1);

        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals(1.99 * 0.8, receipt.getTotalPrice(), 0.01);
    }

//    10% discount on rice, normal price €2.49 per bag
    @Test
    void verify_rice_should_pay_() {
        SupermarketCatalog catalog = new FakeCatalog();
        Product rice = new Product("rice", ProductUnit.Each);
        catalog.addProduct(rice, 2.49);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.TenPercentDiscount, rice, 10);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(rice, 1);

        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals(2.49 * 0.9, receipt.getTotalPrice(), 0.01);
    }

//    Five tubes of toothpaste for €7.49, normal price €1.79

    @Test
    void five_tubes() {
        SupermarketCatalog catalog = new FakeCatalog();
        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        catalog.addProduct(toothbrush, 1.79);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.FiveForAmount, toothbrush, 7.49);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(toothbrush, 5);

        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals(7.49, receipt.getTotalPrice(), 0.01);
    }

//    Two boxes of cherry tomatoes for €0.99, normal price €0.69 per box.
    @Test
    void cherry_tomatoes() {
        SupermarketCatalog catalog = new FakeCatalog();
        Product cherry = new Product("cherry", ProductUnit.Each);
        catalog.addProduct(cherry, 0.69);

        Teller teller = new Teller(catalog);
        teller.addSpecialOffer(SpecialOfferType.TwoForAmount, cherry, 0.99);

        ShoppingCart cart = new ShoppingCart();
        cart.addItemQuantity(cherry, 2);

        // ACT
        Receipt receipt = teller.checksOutArticlesFrom(cart);

        assertEquals(0.99, receipt.getTotalPrice(), 0.01);
    }
}
