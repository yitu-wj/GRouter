```
	一个用于帮助 Android App 进行组件化改造的框架 —— 支持模块间的路由、通信、解耦
```
#### 一、功能介绍
1. **支持直接解析标准URL进行跳转，并自动注入参数到目标页面中**
2. **支持多模块工程使用**

#### 二、典型应用
1. 从外部URL映射到内部页面，以及参数传递与解析
2. 跨模块页面跳转，模块间解耦
3. 拦截跳转过程，处理登陆、埋点等逻辑
4. 跨模块API调用，通过控制反转来做组件解耦

#### 三、基础功能
1. 添加依赖和配置
``` gradle
android {
		defaultConfig {
			...
			javaCompileOptions {
				annotationProcessorOptions {
					arguments = [moduleName: project.getName()]
				}
			}
		}
	}
```
2. 添加注解
``` java
    // 在支持路由的页面上添加注解(必选)
    // 这里的路径需要注意的是至少需要有两级，/xx/xx
    @Route(path = "/test/activity")
    public class YourActivity extend Activity {
        ...
    }
```
3. 初始化SDK
``` java
	GRouter.init(this);
```
4. 发起路由操作
``` java
	// 1. 应用内简单的跳转(通过URL跳转在'进阶用法'中)
    GRouter.getInstance().build("/test/activity").navigation();

    // 2. 跳转并携带参数
    GRouter.getInstance().build("/test/1")
                .withLong("key1", 666L)
                .withString("key3", "888")
                .withObject("key4", new Test("Jack", "Rose"))
                .navigation();
```
