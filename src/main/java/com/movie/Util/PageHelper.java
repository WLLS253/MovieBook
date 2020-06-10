package com.movie.Util;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PageHelper {
    static public <T> Page<T> List2Page(List<T> list, Pageable pageable) {
        int start = (int)pageable.getOffset();
        int end = (start + pageable.getPageSize()) > list.size() ? list.size() : ( start + pageable.getPageSize());
         return new PageImpl<T>(list.subList(start, end), pageable, list.size());
    }
    static public JSONObject getPageInfoWithoutContent(Page p){
        JSONObject page_info = new JSONObject();
        page_info.put("last",p.isLast());
        page_info.put("first",p.isFirst());
        page_info.put("total_pages",p.getTotalPages());
        page_info.put("total_elements",p.getTotalElements());
        page_info.put("number",p.getNumber());
        page_info.put("size",p.getSize());
        page_info.put("empty",p.isEmpty());
        return page_info;
    }
}
