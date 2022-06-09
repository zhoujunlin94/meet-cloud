package com.you.meet.cloud.lib.api.common.util;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.safety.Whitelist;

/**
 * @author zhoujl
 * @date 2021/6/1 15:04
 * @desc
 */
@Slf4j
public class JsoupUtil {

    /**
     * jsoup白名单种类，每一种针对的标签类型不一样，具体的可以ctrl+左键点击relaxed，在jsoup源码中有响应的注释和标签名单
     * 可手动添加
     */
    private static final Whitelist WHITELIST = Whitelist.relaxed();

    private static final Document.OutputSettings OUTPUTSETTINGS = new Document.OutputSettings().prettyPrint(false);

    static {
        /*
          将自定义标签添加进白名单，除开白名单之外的标签都会被过滤
         */
        WHITELIST.addAttributes(":all", "style").addTags("p").addTags("strong");
        /*
          这个配置的意思的过滤如果找不到成对的标签，就只过滤单个标签，而不用把后面所有的文本都进行过滤。
         */
        WHITELIST.preserveRelativeLinks(true);
    }

    /**
     * 过滤方法
     *
     * @param name  属性名
     * @param value 属性值
     * @return
     */
    public static String clean(String name, String value) {
        log.debug("jsoup过滤标签及属性原字符串,{}--{}", name, value);
        String baseUri = "";
        String valueNew = Jsoup.clean(value, baseUri, WHITELIST, OUTPUTSETTINGS);
        valueNew = Parser.unescapeEntities(valueNew, true);
        log.debug("jsoup过滤标签及属性新字符串,{}--{}", name, valueNew);
        return valueNew;
    }

    /**
     * 处理Json类型的Html标签,进行xss过滤
     *
     * @param name  属性名
     * @param value 属性值
     * @return
     */
    public static String cleanJson(String name, String value) {
        //先处理双引号的问题
        value = jsonStringConvert(value);
        return clean(name, value);
    }

    /**
     * 将json字符串本身的双引号以外的双引号变成单引号
     * 参考 https://blog.csdn.net/zzzgd_666/article/details/82870495
     *
     * @param json
     * @return
     */
    public static String jsonStringConvert(String json) {
        log.debug("[处理JSON字符串] [将嵌套的双引号转成单引号] [原JSON] :{}", json);
        char[] temp = json.toCharArray();
        int n = temp.length;
        for (int i = 0; i < n; i++) {
            if (temp[i] == ':' && temp[i + 1] == '"') {
                for (int j = i + 2; j < n; j++) {
                    if (temp[j] == '"') {
                        //如果该字符为双引号,下个字符不是逗号或大括号,替换
                        if (temp[j + 1] != ',' && temp[j + 1] != '}') {
                            //将json字符串本身的双引号以外的双引号变成单引号
                            temp[j] = '\'';
                        } else if (temp[j + 1] == ',' || temp[j + 1] == '}') {
                            break;
                        }
                    }
                }
            }
        }
        String r = new String(temp);
        log.debug("[处理JSON字符串] [将嵌套的双引号转成单引号] [处理后的JSON] :{}", r);
        return r;
    }
}
