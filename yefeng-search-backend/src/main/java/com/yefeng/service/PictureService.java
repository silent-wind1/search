package com.yefeng.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yefeng.model.entity.Picture;


/**
 * @Author: 叶枫
 * @Date: 2024/07/22/23:46
 * @Description:
 */
public interface PictureService extends IService<Picture> {
    Page<Picture> searchPicture(String searchText, long pageNum, long pageSize);
}