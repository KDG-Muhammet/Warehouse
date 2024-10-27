package be.kdg.sa.warehouse.util;

import java.math.BigDecimal;

public class Calculation {

    public static BigDecimal calculateStoragePricePerDelivery(int days, BigDecimal sellingPrice, BigDecimal amount) {
        return new BigDecimal(days).multiply(sellingPrice).multiply(amount);
    }
}

