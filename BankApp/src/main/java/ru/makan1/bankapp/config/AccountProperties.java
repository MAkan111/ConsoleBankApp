package ru.makan1.bankapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AccountProperties {
    private final Double defaultAmount;
    private final Double transferCommission;

    public AccountProperties(@Value("${account.default-amount}") Double defaultAmount,
                             @Value("${account.transfer-commission}") Double transferCommission
    ) {
        this.defaultAmount = defaultAmount;
        this.transferCommission = transferCommission;
    }

    public Double getDefaultAmount() {
        return defaultAmount;
    }

    public Double getTransferCommission() {
        return transferCommission;
    }
}
