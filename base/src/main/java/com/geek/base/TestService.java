package com.geek.base;

import com.geek.grouter.core.template.IService;

/**
 * 需要组件共享的服务需要将服务在此暴露
 */
public interface TestService extends IService {
    void test();
}
