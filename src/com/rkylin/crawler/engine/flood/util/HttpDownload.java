package com.rkylin.crawler.engine.flood.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;

public class HttpDownload {
    private static final transient Logger logger = Logger.getLogger(HttpDownload.class);

    public static final int cache = 4 * 1024;
    public static final String splash;
    public static final String root;

    static {
        splash = System.getProperty("file.separator");
        root = "." + splash + "download";
    }

    /**
     * 根据指定的URL download内容，只能download jar或者zip
     * 
     * @param url
     *            URL
     * @return 保存文件路径
     * @throws Exception
     *             Exception
     */
    public static synchronized String downloadJar(String url) throws Exception {
		if (url == null || (!url.endsWith(".jar") && !url.endsWith(".py"))) {
			throw new Exception("File format is invalid!");
		}
        
        if (HttpDownloadCache.isAlreadyDownloaded(url)) {
            return root + splash + createFilePathFromUrl(url);
        }
        
        logger.info("downloadJar:" + url);
        
        return download(url, root + splash + createFilePathFromUrl(url));
    }

    /**
     * 根据指定的URL download内容，并保存为filepath指定的文件
     * 
     * @param url
     *            URL
     * @param filePath
     *            保存文件路径
     * @return 保存文件路径
     * @throws Exception
     *             Exception
     */
    public static String download(String url, String filePath) throws Exception {
        logger.info("download file:" + url);
        try {
            HttpClient client = HttpClientBuilder.create().build();
            HttpGet get = new HttpGet(url);
            HttpResponse response = client.execute(get);

            if (filePath == null) {
                filePath = getFilePath(url, response);
            }
            if (filePath.endsWith(splash)) {
                throw new Exception("Download failed!");
            }

            HttpEntity entity = response.getEntity();
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            try (InputStream is = entity.getContent(); FileOutputStream fileOut = new FileOutputStream(file)) {
                byte[] buffer = new byte[cache];
                int ch = 0;
                while ((ch = is.read(buffer)) != -1) {
                    fileOut.write(buffer, 0, ch);
                }
                fileOut.flush();
            }

            HttpDownloadCache.putDownloadInfo(url);
            return filePath;
        } catch (Exception e) {
            logger.error(e, e);
            throw e;
        }
    }

    private static String getFilePath(String url, HttpResponse response) {
        String filePath = root + splash;
        String filename = getFileName(response);

        if (filename != null) {
            filePath += filename;
        } else {
            filename = createFilePathFromUrl(url);
            filePath = (filename == null ? filePath : filePath + filename);
        }
        return filePath;
    }

    private static String createFilePathFromUrl(String url) {
        int index = url.lastIndexOf("/");
        return url.substring(index + 1, url.length());
    }

    private static String getFileName(HttpResponse response) {
        Header[] contentHeader = response.getHeaders("Content-Disposition");
        if (contentHeader == null || contentHeader.length == 0) {
            return null;
        }
        String fileName = null;
        for (Header h : contentHeader) {
            HeaderElement[] values = h.getElements();
            if (values.length != 0) {
                for (HeaderElement he : values) {
                    try {
                        NameValuePair param = he.getParameterByName("filename");
                        if (param != null) {
                            fileName = param.getValue();
                            break;
                        }
                    } catch (Exception e) {
                        logger.warn(e, e);
                    }
                }
            }

            if (fileName != null) {
                return fileName;
            }
        }
        return null;
    }

    public static void recoverCache() {
        try (FileInputStream fis = new FileInputStream(root + splash + "httpdownloadcache.ser");
                ObjectInputStream ois = new ObjectInputStream(fis)) {
            HashMap<String, Boolean> m = (HashMap<String, Boolean>) ois.readObject();
            if (m != null) {
                HttpDownloadCache.dc = m;
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
    }

    public static void serializeCache() {
        try (FileOutputStream fos = new FileOutputStream(root + splash + "httpdownloadcache.ser");
                ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(HttpDownloadCache.dc);
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        }
    }
}

class HttpDownloadCache {
    static HashMap<String, Boolean> dc = new HashMap<String, Boolean>();

    /**
     * Add loaded info of a jar to maintenance container
     * 
     * @param url
     *            url of jar
     */
    public static void putDownloadInfo(String url) {
        if (dc.containsKey(url)) {
            return;
        }

        dc.put(url, true);
    }

    /**
     * Is the jar loaded?
     * 
     * @param url
     *            url of jar
     * @return loaded flag
     */
    public static boolean isAlreadyDownloaded(String url) {
        return dc.containsKey(url);
    }
}
