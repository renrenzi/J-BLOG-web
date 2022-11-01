package com.jj.jblog.util;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * 敏感词过滤工具类
 *
 * @author 任人子
 * @date 2022/7/5  - {TIME}
 */
@Component
public class SensitiveUtil {
    private final static Logger log = Logger.getLogger(SensitiveUtil.class);

    private TrieNode trieNode = new TrieNode();

    private final static String REPLACEMENT = "***";

    @PostConstruct
    public void init() {
        try (
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-word.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        ) {
            String keyword;
            while ((keyword = reader.readLine()) != null) {
                this.addTrieNode(keyword);
            }
        } catch (IOException e) {
            log.info("加载敏感词文件失败 " + e.getMessage());
        }
    }



    /**
     * 添加到前缀树
     *
     * @param keyword
     */
    private void addTrieNode(String keyword) {
        TrieNode tempNode = trieNode;
        for (int i = 0; i < keyword.length(); i++) {
            TrieNode subNode = trieNode.getSubNode(keyword.charAt(i));
            if (subNode == null) {
                subNode = new TrieNode();
                tempNode.setSubNode(keyword.charAt(i), subNode);
            }
            tempNode = subNode;
            if (i == keyword.length() - 1) {
                tempNode.setKeywordEnd(true);
            }
        }
    }

    /**
     * 过滤敏感词
     * @param keyword
     * @return
     */
    public String filter(String keyword){
        if(StringUtils.isBlank(keyword)){
            return null;
        }
        TrieNode tempNode = trieNode;
        int begin = 0, position = 0;
        // 可能会有线程安全问题
        StringBuilder sb = new StringBuilder();
        while(position < keyword.length()){
            char c = keyword.charAt(position);
            if(isSystem(c)){
                if(tempNode == trieNode){
                    sb.append(c);
                    begin++;
                }
                position++;
                continue;
            }

            tempNode = tempNode.getSubNode(c);
            if(tempNode == null){
                sb.append(keyword.charAt(begin));
                position = ++begin;
                tempNode = trieNode;
            }else if(tempNode.isKeywordEnd){
                sb.append(REPLACEMENT);
                begin = ++position;
                tempNode = trieNode;
            }else{
                position++;
            }
        }
        sb.append(keyword.substring(begin));
        return sb.toString();
    }

    /**
     * 是否为特殊符号
     * @param c
     * @return
     */
    private boolean isSystem(Character c){
        return !CharUtils.isAsciiAlphanumeric(c) && (c < 0x2E80 || c > 0x9FFF);
    }

    private class TrieNode {
        private boolean isKeywordEnd = false;
        private Map<Character, TrieNode> map = new HashMap<>();

        public boolean isKeywordEnd() {
            return isKeywordEnd;
        }

        public void setKeywordEnd(boolean keywordEnd) {
            isKeywordEnd = keywordEnd;
        }

        public void setSubNode(Character key, TrieNode subNode) {
            map.put(key, subNode);
        }

        public TrieNode getSubNode(Character key) {
            return map.get(key);
        }
    }
}
