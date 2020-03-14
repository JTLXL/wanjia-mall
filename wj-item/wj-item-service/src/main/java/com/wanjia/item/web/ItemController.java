package com.wanjia.item.web;

import com.wanjia.common.enums.ExceptionEnum;
import com.wanjia.common.exception.WjException;
import com.wanjia.pojo.Item;
import com.wanjia.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JT.L
 * @date 2020/3/13 15:35:39
 * @description
 */
@RestController
@RequestMapping("item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping
    public ResponseEntity<Item> saveItem(Item item) {
        // 校验价格
        if (item.getPrice() == null) {
            throw new WjException(ExceptionEnum.PRICE_CANNOT_BE_NULL);
            // 这样抛异常，相当于是SpringMVC在处理，帮我们拦截下来，返回一个友好的提示
            // throw new RuntimeException("价格不能为空");
            /*return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);*/
        }
        item = itemService.saveItem(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }
}
