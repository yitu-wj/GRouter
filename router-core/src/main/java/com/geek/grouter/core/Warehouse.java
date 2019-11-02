package com.geek.grouter.core;

import com.geek.grouter.annotation.model.RouteMeta;
import com.geek.grouter.core.template.IRouteGroup;
import com.geek.grouter.core.template.IService;

import java.util.HashMap;
import java.util.Map;

public class Warehouse {
    // root分组表 保存分组信息
    static Map<String,Class<? extends IRouteGroup>> groupsIndex=new HashMap<>();
    // group 映射表 保存组中的所有数据
    static Map<String,RouteMeta> routes=new HashMap<>();
    // group 映射表 保存组中的所有数据
    static Map<Class,IService> services=new HashMap<>();
}
