package ru.evotor.framework.core.action.event.receipt.discount;

import android.os.Bundle;

import java.math.BigDecimal;

import static ru.evotor.framework.calculator.MoneyCalculator.HUNDRED;
import static ru.evotor.framework.calculator.MoneyCalculator.MONEY_PRECISION;

public class ReceiptDiscountEvent {
    private static final String TAG = "ReceiptDiscountEvent";

    public static final String NAME = "evo.v2.receipt.sell.receiptDiscount";
    private static final String KEY_DISCOUNT = "discount";

    public static ReceiptDiscountEvent create(Bundle bundle) {
        BigDecimal discount = new BigDecimal(bundle.getLong(KEY_DISCOUNT, 0)).setScale(MONEY_PRECISION).divide(HUNDRED, BigDecimal.ROUND_FLOOR);
        return new ReceiptDiscountEvent(discount);
    }

    private BigDecimal discount;

    public ReceiptDiscountEvent(BigDecimal discount) {
        this.discount = discount;
    }

    public Bundle toBundle() {
        Bundle result = new Bundle();
        result.putLong(KEY_DISCOUNT, discount.multiply(HUNDRED).longValue());
        return result;
    }

    public BigDecimal getDiscount() {
        return discount;
    }
}
