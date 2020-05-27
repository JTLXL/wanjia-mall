package com.wanjia.item.service;

import com.wanjia.common.enums.ExceptionEnum;
import com.wanjia.common.exception.WjException;
import com.wanjia.item.mapper.SpecGroupMapper;
import com.wanjia.item.mapper.SpecParamMapper;
import com.wanjia.item.pojo.SpecGroup;
import com.wanjia.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author JT.L
 * @date 2020/3/24 15:21:39
 * @description
 */
@Service
public class SpecificationService {

    @Autowired
    private SpecGroupMapper groupMapper;

    @Autowired
    private SpecParamMapper paramMapper;

    public List<SpecGroup> queryGroupByCid(Long cid) {
        SpecGroup group = new SpecGroup();
        group.setCid(cid);
        List<SpecGroup> list = groupMapper.select(group);
        if (CollectionUtils.isEmpty(list)) {
            throw new WjException(ExceptionEnum.SPEC_GROUP_NOT_FOUND);
        }
        return list;
    }

    public List<SpecParam> queryParamList(Long gid, Long cid, Boolean searching) {
        SpecParam param = new SpecParam();
        param.setGroupId(gid);
        param.setCid(cid);
        param.setSearching(searching);
        List<SpecParam> list = paramMapper.select(param);
        if (CollectionUtils.isEmpty(list)) {
            throw new WjException(ExceptionEnum.SPEC_PARAM_NOT_FOUND);
        }
        return list;
    }

    public List<SpecGroup> queryListByCid(Long cid) {
        // 查询规格组
        List<SpecGroup> specGroups = queryGroupByCid(cid);
        // 查询当前分类下的参数
        List<SpecParam> specParams = queryParamList(null, cid, null);

        // 先把规格参数变成map，map的key是规格组id，map的值是组下的所有参数
        Map<Long, List<SpecParam>> map = new HashMap<>();

        specParams.forEach(param -> {
            Long key = param.getGroupId();
            if (!map.containsKey(key)) {
                // 这个组id在map中不存在，新增一个list
                map.put(key, new LinkedList<>());
            }
            map.get(key).add(param);
        });

        // 填充param到group
        specGroups.forEach(specGroup -> specGroup.setParams(map.get(specGroup.getId())));
        return specGroups;
    }
}
