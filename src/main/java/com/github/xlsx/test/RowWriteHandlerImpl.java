/*
 * Alipay.com Inc.
 * Copyright (c) 2004-2021 All Rights Reserved.
 */
package com.github.xlsx.test;

import com.alibaba.excel.write.handler.AbstractRowWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Iterator;
import java.util.Objects;

/**
 * @author jiushu
 * @version RowWriteHandlerImpl.java, v 0.1 2021年09月12日 11:42 下午 jiushu
 */
public class RowWriteHandlerImpl extends AbstractRowWriteHandler {
    /**
     * 拼接索引
     */
    Integer pasteIndex;
    private Sheet sheet;

    protected RowWriteHandlerImpl(Sheet sheet) {
        this.sheet = sheet;
    }

    public void setIndex(Integer pasteIndex) {
        this.pasteIndex = pasteIndex;
    }

    /**
     * 设置row和cell的style
     *
     * @param writeSheetHolder
     * @param writeTableHolder
     * @param row
     * @param relativeRowIndex
     * @param isHead
     */
    @Override
    public void afterRowDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row, Integer relativeRowIndex, Boolean isHead) {
        if (Objects.isNull(pasteIndex)) {
            return;
        }
        Iterator<Cell> rowIterator = row.cellIterator();
        Row source = sheet.getRow(pasteIndex++);
        row.setHeight(source.getHeight());
        while (rowIterator.hasNext()) {
            Cell cell = rowIterator.next();
            CellStyle cellStyle = writeSheetHolder.getSheet().getWorkbook().createCellStyle();
            cellStyle.cloneStyleFrom(source.getCell(cell.getColumnIndex()).getCellStyle());
            cell.setCellStyle(cellStyle);
        }
    }
}