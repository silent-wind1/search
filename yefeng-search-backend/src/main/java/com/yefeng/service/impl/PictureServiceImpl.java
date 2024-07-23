package com.yefeng.service.impl;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yefeng.mapper.PictureMapper;
import com.yefeng.model.entity.Picture;
import com.yefeng.service.PictureService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture> implements PictureService {
//    @Override
//    public Page<Picture> searchPicture(String searchText, long pageNum, long pageSize) {
//        long current = (pageNum - 1) * pageSize;
//        String url = String.format("https://www.bing.com/images/search?q=%s&first=%s", searchText, current);
//        Document doc = null;
//        try {
//            doc = Jsoup.connect(url).get();
//        } catch (Exception e) {
//            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据获取异常");
//        }
//        Elements elements = doc.select(".iuscp.isv");
//        List<Picture> pictures = new ArrayList<>();
//        for (Element element : elements) {
//            // 取图片地址 (murl)
//            String m = element.select(".iusc").get(0).attr("m");
//            Map<String, Object> map = JSONUtil.toBean(m, Map.class);
//            String murl = (String) map.get("murl");
//            // 取标题
//            String title = element.select(".inflnk").get(0).attr("aria-label");
//            Picture picture = new Picture();
//            picture.setTitle(title);
//            picture.setUrl(murl);
//            pictures.add(picture);
//            if (pictures.size() >= pageSize) {
//                break;
//            }
//        }
//        Page<Picture> picturePage = new Page<>(pageNum, pageSize);
//        picturePage.setRecords(pictures);
//        return picturePage;
//    }

    @Override
    public Page<Picture> searchPicture(String searchText, long pageNum, long pageSize) {
        Page<Picture> current = new Page<>(pageNum, pageSize);
        return query().like("title", searchText).page(current);
    }
}