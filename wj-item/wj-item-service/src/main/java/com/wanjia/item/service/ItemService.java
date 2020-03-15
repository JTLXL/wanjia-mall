package com.wanjia.item.service;

import com.wanjia.item.pojo.Item;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * @author JT.L
 * @date 2020/3/13 15:30:22
 * @description
 */
@Service
public class ItemService {
    public Item saveItem(Item item) {
        // 商品新增
        int id = new Random().nextInt(100);
        item.setId(id);
        return item;
    }
}
