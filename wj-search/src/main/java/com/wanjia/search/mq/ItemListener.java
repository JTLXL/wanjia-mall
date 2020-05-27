package com.wanjia.search.mq;

import com.wanjia.search.service.SearchService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author JT.L
 * @date 2020/5/14 17:48:28
 * @description
 */
@Component
public class ItemListener {

    @Autowired
    private SearchService searchService;

    /**
     * 监听[item.insert]和[item.update]消息
     *
     * @param spuId
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "search.item.insert.queue", durable = "true"),
            exchange = @Exchange(name = "wj.item.exchange", type = ExchangeTypes.TOPIC),
            key = {"item.insert", "item.update"}
            // key = {"item.#"} 通配符写法
    ))
    public void listenInsertOrUpdate(Long spuId) {
        if (spuId == null) {
            return;
        }
        // 处理消息，对索引库进行新增或修改
        searchService.createOrUpdateIndex(spuId);

    }

    /**
     * 监听[item.delete]消息
     *
     * @param spuId
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "search.item.delete.queue", durable = "true"),
            exchange = @Exchange(name = "wj.item.exchange", type = ExchangeTypes.TOPIC),
            key = {"item.delete"}
            // key = {"item.#"} 通配符写法
    ))
    public void listenDelete(Long spuId) {
        if (spuId == null) {
            return;
        }
        // 处理消息，对索引库进行删除
        searchService.deleteIndex(spuId);
    }
}
