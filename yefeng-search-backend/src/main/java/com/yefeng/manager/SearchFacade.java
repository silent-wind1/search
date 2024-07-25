package com.yefeng.manager;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yefeng.common.BaseResponse;
import com.yefeng.common.ErrorCode;
import com.yefeng.common.ResultUtils;
import com.yefeng.exception.BusinessException;
import com.yefeng.exception.ThrowUtils;
import com.yefeng.model.dto.post.PostQueryRequest;
import com.yefeng.model.dto.search.SearchRequest;
import com.yefeng.model.dto.user.UserQueryRequest;
import com.yefeng.model.entity.Picture;
import com.yefeng.model.enums.SearchTypeEnum;
import com.yefeng.model.vo.PostVO;
import com.yefeng.model.vo.SearchVO;
import com.yefeng.model.vo.UserVO;
import com.yefeng.service.PictureService;
import com.yefeng.service.PostService;
import com.yefeng.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.CompletableFuture;

/**
 * @Author: 叶枫
 * @Date: 2024/07/25/23:29
 * @Description:
 */
@Slf4j
@Component
public class SearchFacade {
    @Resource
    private UserService userService;

    @Resource
    private PostService postService;

    @Resource
    private PictureService pictureService;

    public SearchVO searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        String type = searchRequest.getType();
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(type);
        ThrowUtils.throwIf(StringUtils.isBlank(type), ErrorCode.PARAMS_ERROR);
        String searchText = searchRequest.getSearchText();

        // 搜索出所有数据
        if (searchTypeEnum == null) {
            CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(() -> {
                UserQueryRequest userQueryRequest = new UserQueryRequest();
                userQueryRequest.setUserName(searchText);
                Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest);
                return userVOPage;
            });

            CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(() -> {
                PostQueryRequest postQueryRequest = new PostQueryRequest();
                postQueryRequest.setSearchText(searchText);
                Page<PostVO> postVOPage = postService.listPostVOByPage(postQueryRequest, request);
                return postVOPage;
            });

            CompletableFuture<Page<Picture>> pictureTask = CompletableFuture.supplyAsync(() -> {
                Page<Picture> picturePage = pictureService.searchPicture(searchText, 1, 10);
                return picturePage;
            });

            CompletableFuture.allOf(userTask, postTask, pictureTask).join();
            try {
                Page<UserVO> userVOPage = userTask.get();
                Page<PostVO> postVOPage = postTask.get();
                Page<Picture> picturePage = pictureTask.get();

                SearchVO searchVO = new SearchVO();
                searchVO.setUserList(userVOPage.getRecords());
                searchVO.setPostList(postVOPage.getRecords());
                searchVO.setPictureList(picturePage.getRecords());
                return ResultUtils.success(searchVO);
            } catch (Exception e) {
                log.error("查询异常", e);
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询异常");
            }
        } else {
            SearchVO searchVO = new SearchVO();
            switch (searchTypeEnum) {
                case POST:
                    PostQueryRequest postQueryRequest = new PostQueryRequest();
                    postQueryRequest.setSearchText(searchText);
                    Page<PostVO> postVOPage = postService.listPostVOByPage(postQueryRequest, request);
                    searchVO.setPostList(postVOPage.getRecords());
                    break;
                case USER:
                    UserQueryRequest userQueryRequest = new UserQueryRequest();
                    userQueryRequest.setUserName(searchText);
                    Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest);
                    searchVO.setUserList(userVOPage.getRecords());
                    break;
                case PICTURE:
                    Page<Picture> picturePage = pictureService.searchPicture(searchText, 1, 10);
                    searchVO.setPictureList(picturePage.getRecords());
                    break;
                default:
            }
            return ResultUtils.success(searchVO);
        }
    }
}
