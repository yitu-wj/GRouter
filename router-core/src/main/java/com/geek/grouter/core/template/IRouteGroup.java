package com.geek.grouter.core.template;


import com.geek.grouter.annotation.model.RouteMeta;

import java.util.Map;

public interface IRouteGroup {
    void loadInto(Map<String, RouteMeta> atlas);
}
