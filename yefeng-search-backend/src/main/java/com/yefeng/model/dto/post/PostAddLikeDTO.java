package com.yefeng.model.dto.post;

import lombok.Data;

/**
 * @Author: 叶枫
 * @Date: 2024/08/20/18:41
 * @Description:
 */
@Data
public class PostAddLikeDTO {
    private Long postId;
    private Boolean liked;
}
