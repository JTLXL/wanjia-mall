package com.wanjia.common.vo;

import com.wanjia.common.enums.ExceptionEnum;
import lombok.Data;

/**
 * @author JT.L
 * @date 2020/3/14 15:26:37
 * @description
 */
@Data
public class ExceptionResult {
    private int status;
    private String message;
    private Long timestamp;

    public ExceptionResult(ExceptionEnum em) {
        this.status = em.getCode();
        this.message = em.getMsg();
        this.timestamp = System.currentTimeMillis();
    }
}
