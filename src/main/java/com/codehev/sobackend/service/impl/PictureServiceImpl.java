package com.codehev.sobackend.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codehev.sobackend.common.ErrorCode;
import com.codehev.sobackend.exception.BusinessException;
import com.codehev.sobackend.model.entity.Picture;
import com.codehev.sobackend.service.PictureService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author codehev
 * @email 2529799312@qq.com
 * @date 2024-02-10 22:33
 * @description
 */
@Service
public class PictureServiceImpl implements PictureService {
    @Override
    public Page<Picture> searchPictures(String searchText, long current, long pageSize) {
        current = (current - 1) * pageSize;
        String url = "https://www.bing.com/images/search?q=" + searchText + "&form=HDRSC2&first=" + current;
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据获取异常");
        }
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
            if (pictures.size() >= pageSize) {
                break;
            }
        }
        Page<Picture> picturePage = new Page<>(current, pageSize);
        picturePage.setRecords(pictures);
        picturePage.setTotal(pageSize);
        return picturePage;
    }
}
