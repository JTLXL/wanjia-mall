package com.jtl.page.mq;

import com.jtl.page.service.PageService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author JT.L
 * @date 2020/5/15 18:23:53
 * @description
 */
@Component
public class ItemListener {

    @Autowired
    private PageService pageService;

    /**
     * 监听[item.insert]和[item.update]消息
     *
     * @param spuId
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "page.item.insert.queue", durable = "true"),
            exchange = @Exchange(name = "wj.item.exchange", type = ExchangeTypes.TOPIC),
            key = {"item.insert", "item.update"}
            // key = {"item.#"} 通配符写法
    ))
    public void listenInsertOrUpdate(Long spuId) {
        if (spuId == null) {
            return;
        }
        // 处理消息，创建新的静态页
        pageService.createHtml(spuId);

    }

    /**
     * 监听[item.delete]消息
     *
     * @param spuId
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "page.item.delete.queue", durable = "true"),
            exchange = @Exchange(name = "wj.item.exchange", type = ExchangeTypes.TOPIC),
            key = {"item.delete"}
            // key = {"item.#"} 通配符写法
    ))
    public void listenDelete(Long spuId) {
        if (spuId == null) {
            return;
        }
        // 处理消息，对静态页进行删除
        pageService.deleteHtml(spuId);
    }
}
