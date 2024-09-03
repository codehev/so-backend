package com.codehev.sobackend;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.codehev.sobackend.model.entity.Picture;
import com.codehev.sobackend.model.entity.Post;
import com.codehev.sobackend.service.PostService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author codehev
 * @email codehev@qq.com
 * @date 2024-02-10 21:32
 * @description 爬取数据
 */
@SpringBootTest
public class CrawlerTest {
    @Resource
    private PostService postService;

    /**
     * 搜索图片（通过jsoup爬虫）
     *
     * @throws IOException
     */
    @Test
    public void test() throws IOException {
        String searchText = "小黑子";
        Integer current = 1;
        String url = "https://www.bing.com/images/search?q=" + searchText + "&form=HDRSC2&first=" + current;
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select(".iuscp.isv");
        ArrayList<Picture> pictures = new ArrayList<>();
        for (Element element : elements) {
            String m = element.select(".iusc").get(0).attr("m");
            Map<String, Object> map = JSONUtil.toBean(m, Map.class);
            //图片地址
            String murl = (String) map.get("murl");
            //标题
            String title = element.select(".inflnk").get(0).attr("aria-label");
            pictures.add(new Picture(title, murl));
        }
        System.out.println("size:" + pictures.size());
    }

    /**
     * 获取文章数据（通过api）
     */
    @Test
    void testFetchPassage() {
        // 1. 获取数据
        String json = "{\"current\":1,\"pageSize\":8,\"sortField\":\"createTime\",\"sortOrder\":\"descend\",\"category\":\"文章\",\"reviewStatus\":1}";
        String url = "https://www.code-nav.cn/api/post/search/page/vo";
        String result = HttpRequest
                .post(url)
                .body(json)
                .execute()
                .body();
//        System.out.println(result);
        // 2. json 转对象
        Map<String, Object> map = JSONUtil.toBean(result, Map.class);
        JSONObject data = (JSONObject) map.get("data");
        JSONArray records = (JSONArray) data.get("records");
        List<Post> postList = new ArrayList<>();
        for (Object record : records) {
            JSONObject tempRecord = (JSONObject) record;
            Post post = new Post();
            post.setTitle(tempRecord.getStr("title"));
            post.setContent(tempRecord.getStr("content"));
            JSONArray tags = (JSONArray) tempRecord.get("tags");
            List<String> tagList = tags.toList(String.class);
            post.setTags(JSONUtil.toJsonStr(tagList));
            post.setUserId(1L);
            postList.add(post);
        }
        // 3. 数据入库
        boolean b = postService.saveBatch(postList);
        Assertions.assertTrue(b);
    }
}
