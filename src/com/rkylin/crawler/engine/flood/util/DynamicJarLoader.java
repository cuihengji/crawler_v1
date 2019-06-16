package com.rkylin.crawler.engine.flood.util;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.ruixuesoft.crawler.open.RxRule;

public class DynamicJarLoader {
    private static final transient Logger logger = Logger.getLogger(DynamicJarLoader.class);

    /**
     * 根据指定的jar路径和类名创建类实例
     * 
     * @param jarPath
     *            jar路径(例如:E:\\temp\\Test.jar)
     * @param className
     *            类名(例：com.rkylin.Test)
     * @return 类实例
     * @throws Exception
     *             本过程中可能发生的例外
     */
    public static synchronized RxRule load(String jarPath, String className) throws Exception {
        try {
            // 根据jar路径，加载Jar到classpath
            loadJar(jarPath);
            // 创建实例并返回
            return getInstance(className);
        } catch (Exception ex) {
            logger.error(ex, ex);
            throw ex;
        }
    }

    /**
     * 根据指定的jar路径，加载Jar到classpath
     * 
     * @param jarPath
     *            jar路径(例如:E:\\temp\\Test.jar)
     * @throws Exception
     *             本过程中可能发生的例外
     */
    private static void loadJar(String jarPath) throws Exception {
    	
    	logger.info("loadJar jarPath:" + jarPath);
    	
        File testJar = new File(jarPath);
        URL url = testJar.toURI().toURL();

        URLClassLoader sysLoader = (URLClassLoader) DynamicJarLoader.class.getClassLoader();
        Class<URLClassLoader> sysClass = URLClassLoader.class;
        URL[] urls = sysLoader.getURLs();
        if (!isAlreadyLoaded(urls, url)) {
            logger.info(String.format("Load jar:%s to classpath", jarPath));
            Method method = sysClass.getDeclaredMethod("addURL", URL.class);
            method.setAccessible(true);
            method.invoke(sysLoader, new Object[] { url });
        }
    }

    /**
     * 根据类名创建类实例
     * 
     * @param className
     *            类名(例：com.rkylin.Test)
     * @return 类实例
     * @throws Exception
     *             本过程中可能发生的例外
     */
    private static RxRule getInstance(String className) throws Exception {
    	logger.info("getInstance className:" + className);
        return (RxRule) DynamicJarLoader.class.getClassLoader().loadClass(className).getConstructor().newInstance();
    }

    /**
     * 是否给定的Jar path已经存在于当前系统classpath
     * 
     * @param urls
     *            当前系统classpath
     * @param targetUrl
     *            Jar path
     * @return 存在状况
     */
    private static boolean isAlreadyLoaded(URL[] urls, URL targetUrl) {
        List<URL> urlls = Arrays.asList(urls);
        return urlls.contains(targetUrl);
    }
}
