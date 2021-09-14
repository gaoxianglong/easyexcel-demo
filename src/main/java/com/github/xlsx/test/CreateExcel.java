/*
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.github.xlsx.test;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 生成复杂Excel
 *
 * @author jiushu
 * @version CreateExcel.java, v 0.1 2021年09月10日 10:05 上午 jiushu
 */
public class CreateExcel {
    private final String SOURCE_FILE_PATH = "/Users/jiushu/Desktop/template.xlsx";
    private final String TARGET_FILE_PATH = "/Users/jiushu/Desktop/my-excel.xlsx";
    private XSSFWorkbook workbook;
    private Sheet sheet;
    private RowWriteHandlerImpl rowWriteHandler;
    private ExcelWriter writer;
    private WriteSheet writeSheet;
    private int lastRowNum;
    private boolean isForceNewRow;

    public CreateExcel(boolean isForceNewRow) throws Throwable {
        this.isForceNewRow = isForceNewRow;
        // 相关初始化
        init();
    }

    private void init() throws Throwable {
        workbook = new XSSFWorkbook(new BufferedInputStream(new FileInputStream(SOURCE_FILE_PATH)));
        sheet = workbook.getSheetAt(0);
        rowWriteHandler = new RowWriteHandlerImpl(sheet);
        writer = EasyExcel.write(TARGET_FILE_PATH)
                // 渲染list底部style
                .registerWriteHandler(rowWriteHandler)
                .withTemplate(SOURCE_FILE_PATH).build();
        writeSheet = EasyExcel.writerSheet().build();
        lastRowNum = sheet.getLastRowNum();
    }

    /**
     * 填充list数据
     *
     * @param data
     * @throws Throwable
     */
    public void fillData(List<ReportFormsDTO> data) throws Throwable {
        Objects.requireNonNull(data);
        writer.fill(data, FillConfig.builder().forceNewRow(isForceNewRow).build(), writeSheet);
    }

    /**
     * 渲染list后的数据
     *
     * @throws Throwable
     */
    public void paste() throws Throwable {
        // 判断是否需要拼接模板list底部数据
        if (isForceNewRow) {
            return;
        }
        // 获取模板list起始索引
        int pasteIndex = writeSheet.getListStartIndex() + 1;
        // 设置拼接索引
        rowWriteHandler.setIndex(pasteIndex);
        List<List<String>> datas = new ArrayList<>();
        // list之后的数据就是需要继续拼接的数据
        for (int i = pasteIndex; i < lastRowNum; i++) {
            List<String> rd = new ArrayList<>();
            List<String> finalRd = rd;
            sheet.getRow(i).cellIterator().forEachRemaining(cell -> {
                switch (cell.getCellTypeEnum()) {
                    case NUMERIC:
                        finalRd.add(String.valueOf(cell.getNumericCellValue()));
                        break;
                    case STRING:
                        finalRd.add(cell.getStringCellValue());
                        break;
                    case BLANK:
                        finalRd.add(null);
                        break;
                    default:
                        break;
                }
            });
            // 判断是否为空行
            if (rd.parallelStream().allMatch(x -> Objects.isNull(x))) {
                rd = new ArrayList<>();
            } else {
                List<String> nrd = new ArrayList<>(rd);
                for (int j = nrd.size() - 1; j >= 0; j--) {
                    if (Objects.isNull(nrd.get(j))) {
                        nrd.remove(j);
                    } else {
                        break;
                    }
                }
                rd = nrd;
            }
            datas.add(rd);
        }
        // 使用相同的文件句柄实现数据和样式的追加
        writer.write(datas, writeSheet);
    }

    /**
     * 释放资源
     */
    public void release() {
        if (Objects.nonNull(writer)) {
            writer.finish();
        }
    }
}