package com.wanjia.search.web;

import com.wanjia.common.vo.PageResult;
import com.wanjia.search.pojo.Goods;
import com.wanjia.search.pojo.SearchRequest;
import com.wanjia.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JT.L
 * @date 2020/4/14 18:06:29
 * @description
 */
@RestController
public class SearchController {
    @Autowired
    private SearchService searchService;

    /**
     * 搜索功能
     * @param request
     * @return
     */
    @PostMapping("page")
    public ResponseEntity<PageResult<Goods>> search(@RequestBody SearchRequest request) {
        return ResponseEntity.ok(searchService.search(request));
    }
}
