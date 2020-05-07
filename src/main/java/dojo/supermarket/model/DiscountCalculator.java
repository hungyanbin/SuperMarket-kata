package dojo.supermarket.model;

public class DiscountCalculator {

    private Product p ;
    private double quantity;
    private Offer offer;
    private double unitPrice;
    int quantityAsInt;

    public void setProduct(Product p) {
        this.p = p;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
        this.quantityAsInt = (int)quantity;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Discount execute() {
        int specialAmount = getSpecialAmount(offer);
        Discount discount = null;

        if (offer.offerType == SpecialOfferType.TwoForAmount) {
            discount = getDiscountByTwoForAmount(p, quantity, offer, unitPrice, quantityAsInt, discount);
        }

        int numberOfXs = quantityAsInt / specialAmount;
        if (offer.offerType == SpecialOfferType.ThreeForTwo && quantityAsInt > 2) {
            discount = getDiscountByThreeForTwo(p, quantity, unitPrice, quantityAsInt, numberOfXs);
        }
        if (offer.offerType == SpecialOfferType.TenPercentDiscount) {
            discount = getDiscountByTenPercentDiscount(p, quantity, offer, unitPrice);
        }
        if (offer.offerType == SpecialOfferType.FiveForAmount && quantityAsInt >= 5) {
            discount = getDiscountByFiveForAmount(p, quantity, offer, unitPrice, quantityAsInt, specialAmount, numberOfXs);
        }
        return discount;
    }

    private Discount getDiscountByFiveForAmount(Product p, double quantity, Offer offer, double unitPrice, int quantityAsInt, int specialAmount, int numberOfXs) {
        Discount discount;
        double discountTotal = unitPrice * quantity - (offer.argument * numberOfXs + quantityAsInt % 5 * unitPrice);
        discount = new Discount(p, specialAmount + " for " + offer.argument, -discountTotal);
        return discount;
    }

    private Discount getDiscountByTenPercentDiscount(Product p, double quantity, Offer offer, double unitPrice) {
        Discount discount;
        discount = new Discount(p, offer.argument + "% off", -quantity * unitPrice * offer.argument / 100.0);
        return discount;
    }

    private Discount getDiscountByThreeForTwo(Product p, double quantity, double unitPrice, int quantityAsInt, int numberOfXs) {
        Discount discount;
        double discountAmount = quantity * unitPrice - ((numberOfXs * 2 * unitPrice) + quantityAsInt % 3 * unitPrice);
        discount = new Discount(p, "3 for 2", -discountAmount);
        return discount;
    }

    private Discount getDiscountByTwoForAmount(Product p, double quantity, Offer offer, double unitPrice, int quantityAsInt, Discount discount) {
        if (quantityAsInt >= 2) {
            double total = offer.argument * (quantityAsInt / 2) + quantityAsInt % 2 * unitPrice;
            double discountN = unitPrice * quantity - total;
            discount = new Discount(p, "2 for " + offer.argument, -discountN);
        }
        return discount;
    }

    private int getSpecialAmount(Offer offer) {
        int x = 1;
        if (offer.offerType == SpecialOfferType.ThreeForTwo) {
            x = 3;
        } else if (offer.offerType == SpecialOfferType.TwoForAmount) {
            x = 2;
        } if (offer.offerType == SpecialOfferType.FiveForAmount) {
            x = 5;
        }

        return x;
    }
}
