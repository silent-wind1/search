package com.yefeng.datasource;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yefeng.model.entity.Picture;
import com.yefeng.service.PictureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.*;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PictureDataSource implements DataSource<Picture> {
    @Resource
    private PictureService pictureService;

    @Override
    public Page<Picture> doSearch(String searchText, long pageNum, long pageSize) {
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
        Page<Picture> page = pictureService.searchPicture(searchText, pageNum, pageSize);
        List<Picture> records = page.getRecords();
        if (!CollUtil.isEmpty(records)) {
            return page;
        }
        try {
            getPicture(pageNum);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        page = pictureService.searchPicture(searchText, pageNum, pageSize);
        return page;
    }

    private void getPicture(long pageNum) throws IOException, InterruptedException {
        // 调用 Process 类执行 Python 命令
        // 当前idea打开的窗口
        String projectPath = System.getProperty("user.dir");
        String projectDir = projectPath + "/src/main/java/com/yefeng/script";
        log.info("command Path: {}", projectDir);
        String pythonCommand = "python 4k.py --page " + pageNum;
        log.info("python command: {}", pythonCommand);
        // 空格拆分
        ProcessBuilder processBuilder = new ProcessBuilder(pythonCommand.split(" "));
        processBuilder.directory(new File(projectDir));
        Map<String, String> environment = processBuilder.environment();
        System.out.println(environment);

        Process process = processBuilder.start();

        // 读取命令输出
        InputStream inputStream = process.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        int exitCode = process.waitFor();
        System.out.println("命令执行结束，退出码" + exitCode);
    }
}