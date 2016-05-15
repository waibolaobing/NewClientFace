package com.bing.service;

import com.google.gson.Gson;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by bing on 2016/5/15.
 */
@Service
public class PersistenceServiceImpl implements PersistenceService {

    private Map<String, String> configMap;

    public String getConfig(String pageName) throws IOException {
        if (configMap == null) {
            //第一次访问时，从文件中加载配置
            this.loadConfigFromFile();
        }
        return configMap.get(pageName);
    }

    public void updateConfig(String pageName, String config) throws IOException {
        if (configMap == null) {
            //第一次访问时，从文件中加载配置
            this.loadConfigFromFile();
        }
        //将更新写入内存中
        configMap.put(pageName, config);
        //将更新写入文件中
        this.updateConfigInFile();
    }

    private void updateConfigInFile() throws IOException {
        byte[] data;
        String configStr;
        configStr = new Gson().toJson(configMap);
        data = configStr.getBytes();
        File file = new File("./clientFaceConfig");
        if (!file.exists()) {
            file.createNewFile();
        }
        Files.write(Paths.get("./clientFaceConfig"), data);
    }

    private void loadConfigFromFile() throws IOException {
        //从文件中读取
        byte[] data;
        String configStr;
        File file = new File("./clientFaceConfig");
        if (!file.exists()) {
            file.createNewFile();
        }
        data = Files.readAllBytes(Paths.get("./clientFaceConfig"));

        if (data.length <= 0) {
            configMap = new LinkedHashMap<String, String>();
            return;
        }
        configStr = new String(data);

        configMap = new Gson().fromJson(configStr, Map.class);
    }
}
