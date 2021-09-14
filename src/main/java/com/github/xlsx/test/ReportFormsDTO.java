/*
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.github.xlsx.test;

import lombok.Data;

/**
 * @author jiushu
 * @version UserBO.java, v 0.1 2021年08月27日 2:28 下午 jiushu
 */
@Data
public class ReportFormsDTO {
    private Long id;
    private String terminalMerchantName;
    private String paymentServiceProvider;
    private String terminalMerchantOrderNo;
    private Long date;
    private String currency;
    private Double amount;
    private String category;
    private String flowOfGoods;

    private ReportFormsDTO(Builder builder) {
        this.id = builder.id;
        this.terminalMerchantName = builder.terminalMerchantName;
        this.paymentServiceProvider = builder.paymentServiceProvider;
        this.terminalMerchantOrderNo = builder.terminalMerchantOrderNo;
        this.date = builder.date;
        this.currency = builder.currency;
        this.amount = builder.amount;
        this.category = builder.category;
        this.flowOfGoods = builder.flowOfGoods;
    }

    public static class Builder {
        private Long id;
        private String terminalMerchantName;
        private String paymentServiceProvider = "PRO-1";
        private String terminalMerchantOrderNo = "001";
        private Long date;
        private String currency = "USD";
        private Double amount = 12.56D;
        private String category = "TYPE-1";
        private String flowOfGoods = "FLOW-1";

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder terminalMerchantName(String terminalMerchantName) {
            this.terminalMerchantName = terminalMerchantName;
            return this;
        }

        public Builder paymentServiceProvider(String paymentServiceProvider) {
            this.paymentServiceProvider = paymentServiceProvider;
            return this;
        }

        public Builder terminalMerchantOrderNo(String terminalMerchantOrderNo) {
            this.terminalMerchantOrderNo = terminalMerchantOrderNo;
            return this;
        }

        public Builder date(Long date) {
            this.date = date;
            return this;
        }

        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public Builder amount(Double amount) {
            this.amount = amount;
            return this;
        }

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public Builder flowOfGoods(String flowOfGoods) {
            this.flowOfGoods = flowOfGoods;
            return this;
        }

        public ReportFormsDTO build() {
            return new ReportFormsDTO(this);
        }
    }
}