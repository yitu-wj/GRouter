package com.geek.module1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.geek.base.TestService;
import com.geek.grouter.annotation.Extra;
import com.geek.grouter.annotation.Route;
import com.geek.grouter.core.GRouter;

@Route(path = "/module1/test")
public class Module1Activity extends AppCompatActivity {
    @Extra
    String msg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module1);
        GRouter.getInstance().inject(this);

        TestService testService = (TestService) GRouter.getInstance().build("/main/service1")
                .navigation();
        testService.test();

        TestService testService1 = (TestService) GRouter.getInstance().build("/main/service2")
                .navigation();
        testService1.test();

        TestService testService2 = (TestService) GRouter.getInstance().build("/module1/service")
                .navigation();
        testService2.test();

        TestService testService3 = (TestService) GRouter.getInstance().build("/module2/service")
                .navigation();
        testService3.test();
    }
    public void mainJump(View view) {
        GRouter.getInstance().build("/main/test").withString("a",
                "从Module1").navigation(this);
    }

    public void module2Jump(View view) {
        GRouter.getInstance().build("/module2/test").withString("msg",
                "从Module1").navigation(this);
    }
}
