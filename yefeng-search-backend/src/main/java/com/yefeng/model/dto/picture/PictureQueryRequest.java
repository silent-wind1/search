package com.yefeng.model.dto.picture;

import lombok.Data;

/**
 * @Author: 叶枫
 * @Date: 2024/07/22/23:51
 * @Description:
 */
@Data
public class PictureQueryRequest {
    private long pageNumber;
    private long pageSize;
    private String searchText;
}
