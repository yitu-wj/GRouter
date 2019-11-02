package com.geek.test;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.geek.grouter.annotation.Route;

@Route(path = "/a/b")
public class MainActivity extends AppCompatActivity {

    @Route(path = "/a/c")
    public class A extends Activity {

    }

    @Route(path = "/c/b")
    public class B extends Activity {

    }

    @Route(path = "/d/b")
    public class C extends Activity {

    }

    @Route(path = "/d/c")
    public class D extends Activity {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
