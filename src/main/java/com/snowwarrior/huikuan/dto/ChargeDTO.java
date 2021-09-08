package com.snowwarrior.huikuan.dto;

import javax.validation.constraints.*;

public class ChargeDTO {
    @NotBlank
    @Size(min = 4, max = 30)
    private String receiver;

    @NotNull
    @Min(value = 1)
    @Max(value = 50000)
    private Double amount;

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
