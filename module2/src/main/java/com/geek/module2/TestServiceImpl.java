package com.geek.module2;

import android.util.Log;

import com.geek.base.TestService;
import com.geek.grouter.annotation.Route;

@Route(path = "/module2/service")
public class TestServiceImpl  implements TestService {
    @Override
    public void test() {
        Log.i("Service", "我是Module2模块测试服务通信");
    }
}
