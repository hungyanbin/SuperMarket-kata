package dojo.supermarket.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DiscountCalculatorTest {

    //20% discount on apples, normal price €1.99 per kilo.
    @Test
    void apple_discount_test() {
        Product apple = new Product("apple", ProductUnit.Kilo);
        Offer offer = new Offer(SpecialOfferType.TenPercentDiscount, apple, 20);
        double quantity = 1;
        double unitPrice = 1.99;
        DiscountCalculator discountCalculator = new DiscountCalculator();
        discountCalculator.setOffer(offer);
        discountCalculator.setProduct(apple);
        discountCalculator.setQuantity(quantity);
        discountCalculator.setUnitPrice(unitPrice);

        Discount result = discountCalculator.execute();

        assertEquals(1.99 * 0.8 - 1.99, result.getDiscountAmount(), 0.01);
    }

    @Test
    void verify_toothbrush_add_special_odder_should_pay_1_98() {
        Product toothbrush = new Product("toothbrush", ProductUnit.Each);
        Offer offer = new Offer(SpecialOfferType.ThreeForTwo, toothbrush, 1);
        double quantity = 3;
        double unitPrice = 0.99;

        DiscountCalculator discountCalculator = new DiscountCalculator();
        discountCalculator.setOffer(offer);
        discountCalculator.setProduct(toothbrush);
        discountCalculator.setQuantity(quantity);
        discountCalculator.setUnitPrice(unitPrice);

        Discount result = discountCalculator.execute();

        assertEquals(2 *0.99 - 3 * 0.99, result.getDiscountAmount(), 0.01);
    }
}