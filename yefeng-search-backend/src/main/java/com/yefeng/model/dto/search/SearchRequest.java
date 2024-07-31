package com.yefeng.model.dto.search;

import com.yefeng.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class SearchRequest extends PageRequest implements Serializable {
    /**
     * 搜索词
     */
    private String searchText;

    /**
     * 类型
     */
    private String type;

    private long pageNum;
    private long pageSize;

    private static final long serialVersionUID = 1L;
}