package com.yefeng;

import com.yefeng.esdao.PostEsDao;
import com.yefeng.model.dto.post.PostEsDTO;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.*;

/**
 * 主类测试
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MainApplicationTests {
    @Resource
    private PostEsDao postEsDao;

//    @Test
//    void contextLoads() {
//        // 1. 获取数据
//        String json = "{\"current\": 1, \"pageSize\": 8, \"sortField\": \"createTime\", \"sortOrder\": \"descend\", \"category\": \"文章\",\"reviewStatus\": 1}";
//        String url = "https://www.code-nav.cn/api/post/search/page/vo";
//        String result = HttpRequest
//                .post(url)
//                .body(json)
//                .execute()
//                .body();
//        // 2. json 转对象
//        Map map = JSONUtil.toBean(result, Map.class);
//        System.out.println(map.get("data"));
//        JSONObject data = (JSONObject) map.get("data");
//        JSONArray records = (JSONArray) data.get("records");
//        List<Post> postList = new ArrayList<>();
//        for (Object record : records) {
//            JSONObject tempRecord = (JSONObject) record;
//            Post post = new Post();
//            // todo 取值过程中,需要判空
//            post.setTitle(tempRecord.getStr("title"));
//            post.setContent(tempRecord.getStr("content"));
//            JSONArray tags = (JSONArray) tempRecord.get("tags");
//            List<String> tagList = tags.toList(String.class);
//            post.setTags(JSONUtil.toJsonStr(tagList));
//            post.setUserId(Long.valueOf(1L));
//            postList.add(post);
//        }
//        System.out.println(postList);
//    }

    @Test
    void testAdd() {
        PostEsDTO postEsDTO = new PostEsDTO();
        postEsDTO.setId(1L);
        postEsDTO.setTitle("鱼皮是狗");
        postEsDTO.setContent("鱼皮是真割韭菜");
        postEsDTO.setTags(Arrays.asList("java", "python"));
        postEsDTO.setUserId(1L);
        postEsDTO.setCreateTime(new Date());
        postEsDTO.setUpdateTime(new Date());
        postEsDTO.setIsDelete(0);
        postEsDao.save(postEsDTO);
        System.out.println(postEsDTO.getId());
    }

    @Test
    void testSelect() {
        System.out.println(postEsDao.count());
        //分页查询
        Page<PostEsDTO> PostPage = postEsDao.findAll(PageRequest.of(0, 5, Sort.by("createTime")));
        List<PostEsDTO> postList = PostPage.getContent();
        System.out.println(postList);
        // 根据 Id 查询
        Optional<PostEsDTO> byId = postEsDao.findById(1L);
        System.out.println(byId);
    }

    @Test
    void testFindByTitle() {
        List<PostEsDTO> postEsDTOS = postEsDao.findByTitle("鱼皮");
        System.out.println(postEsDTOS);
    }
}
