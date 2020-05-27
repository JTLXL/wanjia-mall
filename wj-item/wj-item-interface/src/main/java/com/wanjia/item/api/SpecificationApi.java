package com.wanjia.item.api;

import com.wanjia.item.pojo.SpecGroup;
import com.wanjia.item.pojo.SpecParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author JT.L
 * @date 2020/4/12 17:36:37
 * @description
 */
public interface SpecificationApi {
    /**
     * 查询规格参数集合
     *
     * @param gid       组id
     * @param cid       分类id
     * @param searching 是否搜索
     * @return
     */
    @GetMapping("spec/params")
    List<SpecParam> queryParamList(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "searching", required = false) Boolean searching
    );

    /**
     * 根据cid查询规格参数组
     * 以及该组下的所有规格参数信息
     *
     * @param cid 分类id
     * @return
     */
    @GetMapping("spec/group")
    List<SpecGroup> queryGroupByCid(@RequestParam("cid") Long cid);
}
