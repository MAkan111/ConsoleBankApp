package ru.makan1.bankapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "account")
public class AccountProperties {
    private Double defaultAmount;
    private Double transferCommission;

    public Double getDefaultAmount() {
        return defaultAmount;
    }

    public Double getTransferCommission() {
        return transferCommission;
    }

    public void setDefaultAmount(Double defaultAmount) {
        this.defaultAmount = defaultAmount;
    }

    public void setTransferCommission(Double transferCommission) {
        this.transferCommission = transferCommission;
    }
}
