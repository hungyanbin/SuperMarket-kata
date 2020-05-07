package dojo.supermarket.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {

    private final List<ProductQuantity> items = new ArrayList<>();
    Map<Product, Double> productQuantities = new HashMap<>();


    List<ProductQuantity> getItems() {
        return new ArrayList<>(items);
    }

    void addItem(Product product) {
        this.addItemQuantity(product, 1.0);
    }

    Map<Product, Double> productQuantities() {
        return productQuantities;
    }


    public void addItemQuantity(Product product, double quantity) {
        items.add(new ProductQuantity(product, quantity));
        if (productQuantities.containsKey(product)) {
            productQuantities.put(product, productQuantities.get(product) + quantity);
        } else {
            productQuantities.put(product, quantity);
        }
    }

    void handleOffers(Receipt receipt, Map<Product, Offer> offers, SupermarketCatalog catalog) {
        for (Product p: productQuantities().keySet()) {
            handleProduct(receipt, offers, catalog, p);
        }
    }

    private void handleProduct(Receipt receipt, Map<Product, Offer> offers, SupermarketCatalog catalog, Product p) {
        double quantity = productQuantities.get(p);
        if (!offers.containsKey(p)) {
            return;
        }

        Offer offer = offers.get(p);
        double unitPrice = catalog.getUnitPrice(p);
        DiscountCalculator discountCalculator = new DiscountCalculator();
        discountCalculator.setOffer(offer);
        discountCalculator.setUnitPrice(unitPrice);
        discountCalculator.setQuantity(quantity);
        discountCalculator.setUnitPrice(unitPrice);
        //bra bra bra...
        Discount discount = discountCalculator.execute();

        if (discount != null)
            receipt.addDiscount(discount);
    }
}
