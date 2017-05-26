package com.lili.cms.util;

import java.util.List;

import com.github.pagehelper.Page;
import com.lili.cms.entity.PagedResult;

/**
 * 将MyBatis的PageHelper的Page对象封装成PagedResult对象
 */


public class BeanUtil {

    public static <T> PagedResult<T> toPagedResult(List<T> datas) {
        PagedResult<T> result = new PagedResult<T>();
        if (datas instanceof Page) {
            Page<T> page = (Page<T>) datas;
            result.setPageNo(page.getPageNum());
            result.setPageSize(page.getPageSize());
            result.setDataList(page.getResult());
            result.setTotal(page.getTotal());
            result.setPages(page.getPages());
        }
        else {
            result.setPageNo(1);
            result.setPageSize(datas.size());
            result.setDataList(datas);
            result.setTotal(datas.size());
        }

        return result;
    }

}
