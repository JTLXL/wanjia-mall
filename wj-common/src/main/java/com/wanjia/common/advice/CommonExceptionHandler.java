package com.wanjia.common.advice;

import com.wanjia.common.enums.ExceptionEnum;
import com.wanjia.common.exception.WjException;
import com.wanjia.common.vo.ExceptionResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author JT.L
 * @date 2020/3/13 17:35:24
 * @description
 */
@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(WjException.class)
    public ResponseEntity<ExceptionResult> handleException(WjException e) {
        ExceptionEnum em = e.getExceptionEnum();
        return ResponseEntity.status(em.getCode())
                .body(new ExceptionResult(e.getExceptionEnum()));
        //return ResponseEntity.status(em.getCode()).body(em.getMsg());
        // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

    }
}
