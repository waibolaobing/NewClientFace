package com.bing.service;

import java.io.IOException;

/**
 * Created by bing on 2016/5/15.
 */
public interface PersistenceService {

    /**
     * 取得页面配置
     *
     * @return
     */
    String getConfig(String pageName) throws IOException;

    /**
     * 更新页面配置
     */
    void updateConfig(String pageName, String config) throws IOException;
}
