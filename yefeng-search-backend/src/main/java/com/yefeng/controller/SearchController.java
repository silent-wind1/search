package com.yefeng.controller;

import com.yefeng.common.BaseResponse;
import com.yefeng.common.ResultUtils;
import com.yefeng.manager.SearchFacade;
import com.yefeng.model.dto.search.SearchRequest;
import com.yefeng.model.vo.SearchVO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author: 叶枫
 * @Date: 2024/07/25/22:58
 * @Description:
 */
@RequestMapping("/search")
@RestController
public class SearchController {
    @Resource
    private SearchFacade searchFacade;

    @PostMapping("/all")
    public BaseResponse<SearchVO> searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        return ResultUtils.success(searchFacade.searchAll(searchRequest, request));
    }
}
