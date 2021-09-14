/*
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.github.xlsx.test;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author jiushu
 * @version Starter.java, v 0.1 2021年09月13日 5:53 下午 jiushu
 */
public class Starter {
    public static void main(String[] agrs) {
        try {
            new Starter().run();
            System.out.println("success...");
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public void run() throws Throwable {
        AtomicLong idNum = new AtomicLong();
        CreateExcel createExcel = null;
        try {
            createExcel = new CreateExcel(false);
            // 模拟分批写数据
            writeData(createExcel, idNum.incrementAndGet());
            // 拼接模板list底部数据
            createExcel.paste();
        } finally {
            createExcel.release();
        }
    }

    public void writeData(CreateExcel createExcel, long id) throws Throwable {
        for (int i = 0; i < 100; i++) {
            createExcel.fillData(new ArrayList<ReportFormsDTO>() {{
                for (int j = 0; j < 4000; j++) {
                    this.add(new ReportFormsDTO.Builder().
                            id(id).
                            date(System.currentTimeMillis()).
                            terminalMerchantName(UUID.randomUUID().toString()).
                            build());
                }
            }});
        }
    }
}