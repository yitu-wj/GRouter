package com.geek.grouter.core;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;

import com.geek.grouter.annotation.model.RouteMeta;
import com.geek.grouter.core.callback.NavigationCallback;
import com.geek.grouter.core.exception.NoRouteFoundException;
import com.geek.grouter.core.template.IRouteGroup;
import com.geek.grouter.core.template.IRouteRoot;
import com.geek.grouter.core.template.IService;
import com.geek.grouter.core.utils.ClassUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

public class GRouter {
    private static final String ROUTE_ROOT_PAKCAGE = "com.geek.grouter.routes";
    private static final String SDK_NAME = "GRouter";
    private static final String SEPARATOR = "$$";
    private static final String SUFFIX_ROOT = "Root";
    private static final String TAG = "GRouter";
    private static GRouter instance;
    private static Application mContext;
    private Handler handler;
    private GRouter(){
        handler=new Handler(Looper.getMainLooper());
    }
    public static GRouter getInstance(){
        synchronized (GRouter.class){
            if(instance==null){
                instance=new GRouter();
            }
        }
        return instance;
    }

    /**
     * 初始化
     * @param application
     */
    public static void init(Application application){
        mContext=application;
        try {
            loadInfo();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG,"初始化失败！",e);
        }
    }

    private static void loadInfo() throws PackageManager.NameNotFoundException, InterruptedException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        //获取所有apt生成的路由类的全类名（路由表）
        Set<String> routerMap = ClassUtils.getFileNameByPackageName(mContext, ROUTE_ROOT_PAKCAGE);
        for (String className : routerMap) {
            if(className.startsWith(ROUTE_ROOT_PAKCAGE+"."+SDK_NAME+SEPARATOR+SUFFIX_ROOT)){
                //root中注册的是分组信息，将分组信息加入仓库中
                ((IRouteRoot)Class.forName(className).getConstructor().newInstance()).loadInto(Warehouse.groupsIndex);
            }
        }
        for(Map.Entry<String,Class<? extends IRouteGroup>> stringClassEntry:Warehouse.groupsIndex.entrySet()){
            Log.e(TAG, "Root映射表[ " + stringClassEntry.getKey() + " : " + stringClassEntry
                    .getValue() + "]");
        }
    }
    public Postcard build(String path){
        if(TextUtils.isEmpty(path)){
            throw new RuntimeException("路由地址无效！");
        }else {
            return build(path,extractGroup(path));
        }
    }
    public Postcard build(String path,String group){
        if(TextUtils.isEmpty(path)||TextUtils.isEmpty(group)){
            throw new RuntimeException("路由地址无效！");
        }else {
            return new Postcard(path,group);
        }
    }

    /**
     * 获取组别
     * @param path
     * @return
     */
    private String extractGroup(String path){
        if(TextUtils.isEmpty(path)||!path.startsWith("/")){
            throw new RuntimeException(path+": 不能提取group");
        }
        try {
            String defaultGroup = path.substring(1, path.indexOf("/", 1));
            if(TextUtils.isEmpty(defaultGroup)){
                throw new RuntimeException(path+"：不能提取group.");
            }else {
                return defaultGroup;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据跳卡跳转页面
     * @param context
     * @param postcard
     * @param requestCode
     * @param callback
     * @return
     */
    protected Object navigation(Context context, final Postcard postcard, final int requestCode, final NavigationCallback callback){
        try {
            prepareCard(postcard);
        }catch (NoRouteFoundException e){
            e.printStackTrace();
            //没找到
            if(callback!=null){
                callback.onLost(postcard);
            }
            return null;
        }
        if(callback!=null){
            callback.onFound(postcard);
        }
        switch (postcard.getType()) {
            case ACTIVITY:
                final Context currentContext = null == context ? mContext : context;
                final Intent intent = new Intent(currentContext, postcard.getDestination());
                intent.putExtras(postcard.getExtras());
                int flags = postcard.getFlags();
                if (-1 != flags) {
                    intent.setFlags(flags);
                } else if (!(currentContext instanceof Activity)) {
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //可能需要返回码
                        if (requestCode > 0) {
                            ActivityCompat.startActivityForResult((Activity) currentContext, intent,
                                    requestCode, postcard.getOptionsBundle());
                        } else {
                            ActivityCompat.startActivity(currentContext, intent, postcard
                                    .getOptionsBundle());
                        }

                        if ((0 != postcard.getEnterAnim() || 0 != postcard.getExitAnim()) &&
                                currentContext instanceof Activity) {
                            //老版本
                            ((Activity) currentContext).overridePendingTransition(postcard
                                            .getEnterAnim()
                                    , postcard.getExitAnim());
                        }
                        //跳转完成
                        if (null != callback) {
                            callback.onArrival(postcard);
                        }
                    }
                });
                break;
            case ISERVICE:
                return postcard.getService();
            default:
                break;
        }
        return null;
    }

    /**
     * 准备卡片
     * @param card
     */
    private void prepareCard(Postcard card){
        RouteMeta routeMeta = Warehouse.routes.get(card.getPath());
        //还没有准备的
        if(routeMeta==null){
            //创建并调用 loadInto 函数，然后记录在仓库
            Class<? extends IRouteGroup> groupMeta = Warehouse.groupsIndex.get(card.getGroup());
            if(groupMeta==null){
                throw new NoRouteFoundException("没找到对应路由："+card.getGroup()+" "+card.getPath());
            }
            IRouteGroup iGroupInstance;
            try {
                iGroupInstance=groupMeta.getConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("路由分组映射表记录失败.", e);
            }
            iGroupInstance.loadInto(Warehouse.routes);
            //已经准备过了就可以移除了（不会一直存在内存中）
            Warehouse.groupsIndex.remove(card.getGroup());
            //再次进入 else
            prepareCard(card);
        }else {
            //类 要跳转的activity 或 IService类
            card.setDestination(routeMeta.getDestination());
            card.setType(routeMeta.getType());
            switch (routeMeta.getType()){
                case ISERVICE:
                    Class<?> destination = routeMeta.getDestination();
                    IService service = Warehouse.services.get(destination);
                    if(service==null){
                        try {
                            service= (IService) destination.getConstructor().newInstance();
                            Warehouse.services.put(destination,service);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    card.setService(service);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 注入
     * @param instance
     */
    public void inject(Activity instance){
        ExtraManager.getInstance().loadExtras(instance);
    }
}
