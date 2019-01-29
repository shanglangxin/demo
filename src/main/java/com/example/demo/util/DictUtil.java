package com.example.demo.util;

import com.google.common.collect.Maps;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
public class DictUtil {

    private static Map<String, Map<String, String>> dictMap = Maps.newHashMap();

    @Override
    public void run(String... args) throws Exception {
        log.info("开始初始化字典");
        InputStream resourceAsStream = this.getClass().getResourceAsStream("/dict.xml");
        if (AssertUtil.isNotEmpty(resourceAsStream)) {
            SAXReader saxReader = new SAXReader();
            Document doc = saxReader.read(resourceAsStream);
            Element rootElement = doc.getRootElement();
            if (AssertUtil.isNotEmpty(rootElement)) {
                Iterator typeIter = rootElement.elementIterator("type");
                while (typeIter.hasNext()) {
                    Element type = (Element) typeIter.next();
                    String typeCode = type.attributeValue("code");
                    Iterator itemIter = type.elementIterator("item");
                    Map<String, String> itemMap = Maps.newHashMap();
                    while (itemIter.hasNext()) {
                        Element item = (Element) itemIter.next();
                        String code = item.attributeValue("code");
                        String name = item.attributeValue("name");
                        if (itemMap.containsKey(code)) {
                            log.error("{}类型中存在重复的code:{},请检查配置文件", typeCode, code);
                        } else {
                            itemMap.put(code, name);
                        }
                    }
                    dictMap.put(typeCode, itemMap);
                }
            }
        }
        log.info("初始化字典结束");

    }

    /**
     * 根据type获取对应的字典项
     *
     * @param type
     * @return
     */
    public static Map<String, String> getDictItem(@NonNull String type) {
        return dictMap.get(type);
    }

}
