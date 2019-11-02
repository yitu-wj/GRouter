package com.geek.module2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.geek.base.TestService;
import com.geek.grouter.annotation.Extra;
import com.geek.grouter.annotation.Route;
import com.geek.grouter.core.GRouter;

@Route(path = "/module2/test")
public class Module2Activity extends AppCompatActivity {

    @Extra
    String msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module2);
        GRouter.getInstance().inject(this);

        // 当处于组件模式的时候
        if(BuildConfig.isModule){
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
    }
    public void mainJump(View view) {
        if (BuildConfig.isModule){
            GRouter.getInstance().build("/main/test").withString("a",
                    "从Module2").navigation(this);
        }else{
            Toast.makeText(this,"当前处于组件模式,无法使用此功能",0).show();
        }
    }

    public void module1Jump(View view) {
        GRouter.getInstance().build("/module1/test").withString("msg",
                "从Module2").navigation(this);
    }
}
