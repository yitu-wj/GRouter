package com.geek.module1;

import android.util.Log;

import com.geek.base.TestService;
import com.geek.grouter.annotation.Route;

@Route(path = "/module1/service")
public class TestServiceImpl implements TestService {
    @Override
    public void test() {
        Log.i("Service", "我是Module1模块测试服务通信");
    }
}
