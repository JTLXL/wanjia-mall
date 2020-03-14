package com.wanjia.common.exception;

import com.wanjia.common.enums.ExceptionEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author JT.L
 * @date 2020/3/14 14:59:28
 * @description
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WjException extends RuntimeException {
    private ExceptionEnum exceptionEnum;
}
