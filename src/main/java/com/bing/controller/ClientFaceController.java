package com.bing.controller;

import com.bing.service.PersistenceService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.sun.corba.se.impl.util.RepositoryIdCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by bing on 2016/5/15.
 */
@Controller
public class ClientFaceController {


    @Autowired
    private PersistenceService persistenceService;

    @RequestMapping(method = RequestMethod.POST, value = "/getConfigByPageName.json")
    @ResponseBody
    public String getConfigByPageName(String pageName) {
        Map<String,Object> returnMap = new LinkedHashMap<String, Object>();
        try {
            String clientFaceConfig = persistenceService.getConfig(pageName);
            if (clientFaceConfig == null){
                returnMap.put("respCode","2000");
                returnMap.put("respMsg","没有发现配置");
            }else {
                returnMap.put("respCode","1000");
                returnMap.put("respMsg","查询成功");
                returnMap.put("configData",clientFaceConfig);
            }
        } catch (Exception e){
            returnMap.put("respCode","1001");
            returnMap.put("respMsg","系统异常");
        }
        return new Gson().toJson(returnMap);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/updateConfigByPageName.json")
    @ResponseBody
    public String updateConfigByPageName(String pageName, String config) {
        Map<String,Object> returnMap = new LinkedHashMap<String, Object>();
        try {
            persistenceService.updateConfig(pageName, config);
            return persistenceService.getConfig(pageName);
        } catch (Exception e){
        returnMap.put("respCode","1001");
        returnMap.put("respMsg","系统异常");
    }
        return new Gson().toJson(returnMap);
    }
}
