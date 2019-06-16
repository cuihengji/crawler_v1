package com.rkylin.crawler.engine.flood.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.rkylin.crawler.engine.flood.common.Constant.ScriptFile;
import com.rkylin.crawler.engine.flood.common.Constant.WebDriverConfig;
import com.web2data.system.config._main.ConfigFacade;

public class FileUtil {
	
    private static final Logger logger = Logger.getLogger(FileUtil.class);
    
	public static void main(String[] args) {
		
	}

	

	// 删除文件和目录
	public static void deleteFiles(String workspaceRootPath) {
		File file = new File(workspaceRootPath);
		if (file.exists()) {
			deleteFile(file);
		}
	}

	public static void deleteFile(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				deleteFile(files[i]);
			}
		}
		
		try {
			file.delete();
		} catch (Exception e) {
			logger.error("文件删除异常。",e);
		}
	}
	
	public static void deleteChromeDriverExe(int sessionId) {
		
		String strSessid = StringUtil.preZeroAdd(sessionId);

		ChromeDriverFilter filter = new ChromeDriverFilter(WebDriverConfig.CHROMEDRIVER_V2_43 + "_" + strSessid);
		logger.info("ChromeDriverFilter file: -->" + WebDriverConfig.CHROMEDRIVER_V2_43 + "_" + strSessid);
		
		boolean isWindowsHost = ConfigFacade.isWindowsHost();
		String rootDirPath = WebDriverConfig.WINDOWS_PREFIX + File.separator + WebDriverConfig.CHROME_DRIVERS_FOLDER + File.separator;

		if (!isWindowsHost) {
			rootDirPath = File.separator + WebDriverConfig.CHROME_DRIVERS_FOLDER + File.separator;
		}
		logger.info("rootDirPath: -->" + rootDirPath);

		File chromeDriverDir = new File(rootDirPath);
		File[] chromeDriverFileNames = chromeDriverDir.listFiles(filter);
		
		if (chromeDriverFileNames != null) {
			for (File chromeDriver : chromeDriverFileNames) {
				try {
					logger.info("deleted file: -->" + chromeDriver);
					chromeDriver.delete();
				} catch (Exception e) {
					logger.error("文件删除异常。", e);
				}
			}
		}
		
		try {
			logger.info("-----------------deleteChromeDriverExe sleep 3 seoncds: ");
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			logger.warn("InterruptedException ", e1);
		}
	}
	
    /**
     * 压缩代理设置文件
     *  
     * @param sessionId     爬虫机session Id
     * @param templateDir   代理设置模板文件目录
     * @param zipTargetDir  压缩文件目录
     * @param zipFileName   压缩文件名
     * @throws IOException
     */
    public synchronized static void zipProxyFile(int sessionId,String templateDir,String zipTargetDir,String zipFileName) throws IOException {
        
    	//createConfigFile(sessionId,zipTargetDir);
        editBackgroundFile(sessionId,templateDir);
        
        byte[] buffer = new byte[1024];

        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipTargetDir + File.separator +zipFileName));

        File[] file1 = { new File(templateDir + File.separator + "background.js"), new File(templateDir + File.separator + "background.html"), new File(templateDir + File.separator + "manifest.json")};

        for (int i = 0; i < file1.length; i++) {
            FileInputStream fis = new FileInputStream(file1[i]);
            out.putNextEntry(new ZipEntry(file1[i].getName()));
            int len;
            while ((len = fis.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.closeEntry();
            fis.close();
        }
        out.close();
    }
    
    /**
     * 压缩Web插件设置文件
     *  
     * @param sessionId     爬虫机session Id
     * @param templateDir   代理设置模板文件目录
     * @param zipTargetDir  压缩文件目录
     * @param zipFileName   压缩文件名
     * @throws IOException
     */
    public synchronized static void zipFrontFile(int sessionId,String templateDir,String zipTargetDir,String zipFileName) throws IOException {
        
    	//createConfigFile(sessionId,zipTargetDir);
    	editFrontendFile(sessionId,templateDir);
        
        byte[] buffer = new byte[1024];

        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipTargetDir + File.separator +zipFileName));

        File[] file1 = { new File(templateDir + File.separator + "flood_frontend.js"), new File(templateDir + File.separator + "manifest.json")};

        for (int i = 0; i < file1.length; i++) {
            FileInputStream fis = new FileInputStream(file1[i]);
            out.putNextEntry(new ZipEntry(file1[i].getName()));
            int len;
            while ((len = fis.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.closeEntry();
            fis.close();
        }
        out.close();
    }
    
    
    
    /**
     * 编辑background.js 文件

     * @param templateDir   代理设置模板文件目录
     * @param sessionId   爬虫机Session Id
     */
    public synchronized static void editBackgroundFile(int sessionId,String templateDir) {
        write(templateDir+File.separator + "background.js", read(String.valueOf(sessionId),templateDir + File.separator + "background_template.js"));
    }
    
    /**
     * 编辑frontend.js 文件

     * @param templateDir   代理设置模板文件目录
     * @param sessionId   爬虫机Session Id
     * 
     * 前台Web 插件的ID为 10001, 10002, etc
     */
    public synchronized static void editFrontendFile(int sessionId,String templateDir) {
        write(templateDir+File.separator+"flood_frontend.js", read(String.valueOf(10000+sessionId), templateDir + File.separator + "flood_frontend_template.js"));
    }
    
    /**
     * 读background.js文件，并替换相关字符串
     * 
     * @param proxyIp   代理IP
     * @param proxyPort 代理端口
     * @param proxyUserName 代理服务器用户名
     * @param proxyPassword 代理服务器密码
     * @param filePath 文件件background.js目录 
     * @return 编辑完的文件内容
     */
    public static String read(String sessionId,String filePath) {
        BufferedReader br = null;
        String line = null;
        StringBuffer buf = new StringBuffer();
        String newLine = null;
        
        
        try {
            // 根据文件路径创建缓冲输入流
            br = new BufferedReader(new FileReader(filePath));
            
            // 循环读取文件的每一行, 对需要修改的行进行修改, 放入缓冲对象中
            while ((line = br.readLine()) != null) {
                // 此处根据实际需要修改某些行的内容
                if (line.contains("{SESSION_ID}")) {
                    newLine = StringUtils.replace(line, "{SESSION_ID}", sessionId);
                    buf.append(newLine);
                }
              
                // 如果不用修改, 则按原来的内容回写
                else {
                    buf.append(line);
                }
                buf.append(System.getProperty("line.separator"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    br = null;
                }
            }
        }
        
        return buf.toString();
    }
    
    /**
     * 把文件读到字符串
     * 
     * @param filePath
     * @return 编辑完的文件内容
     */
    public static String read(String filePath) {
        BufferedReader br = null;
        String line = null;
        StringBuffer buf = new StringBuffer();
        
        // 文件不存在
        File file = new File(filePath);
        if (!file.exists()) {
           return null;
        }
        
        try {
            // 根据文件路径创建缓冲输入流
            br = new BufferedReader(new FileReader(filePath));
            
            // 循环读取文件的每一行, 对需要修改的行进行修改, 放入缓冲对象中
            while ((line = br.readLine()) != null) {
                    buf.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // 关闭流
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    br = null;
                }
            }
        }
        
        return buf.toString();
    }
    
    /**
     * 将内容回写到文件中
     * 
     * @param filePath 文件目录
     * @param content 文件内容
     */
    public static void write(String filePath, String content) {
        BufferedWriter bw = null;
        
        try {
            // 根据文件路径创建缓冲输出流
            bw = new BufferedWriter(new FileWriter(filePath));
            
            //bw = new BufferedWriter (new OutputStreamWriter (new FileOutputStream (filePath,true),"UTF-8"));
            // 将内容写入文件中
            bw.write(content);
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭流
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    bw = null;
                }
            }
        }
    }
    
	/**
	 * 复制文件
	 * 
	 * @param srcFileName
	 *            待复制文件的文件名
	 * @param destFileName
	 *            目标文件名
	 * @return 如果复制成功返回true，否则返回false
	 */
    public static void copyFile(String srcFileName,String destFileName) {
    	
		String chromeDriverDir = WebDriverConfig.WINDOWS_PREFIX + File.separator + WebDriverConfig.CHROME_DRIVERS_FOLDER;

		boolean isWindowsHost = ConfigFacade.isWindowsHost();
		if (!isWindowsHost) {
			chromeDriverDir = File.separator + WebDriverConfig.CHROME_DRIVERS_FOLDER;
		}
		
		try {
			
			Path driversFolder = Paths.get(chromeDriverDir);
			boolean foldExists = Files.exists(driversFolder, new LinkOption[] { LinkOption.NOFOLLOW_LINKS });
			if( !foldExists ) {
				
				Files.createDirectories(Paths.get(chromeDriverDir));
				logger.info("-----------------createDirectories: " + chromeDriverDir);
				
			}
			
			Path driverFile = Paths.get(destFileName);
			boolean fileExists = Files.exists(driverFile, new LinkOption[] { LinkOption.NOFOLLOW_LINKS });
			if( !fileExists ) {
				
				Files.copy(Paths.get(srcFileName), Paths.get(destFileName), StandardCopyOption.COPY_ATTRIBUTES);
				logger.info("---------复制文件: " + "源文件:->  "+ srcFileName + " 到目标文件:-> " + destFileName);
				
				logger.info("-----------------copyFile sleep 3 seoncds: ");
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
					logger.warn("InterruptedException ", e1);
				}
			}
			
		} catch (Exception e) {
			logger.error("-----------------copyFile: ", e);
		}
    }
    
    
    
    /**
	 * 复制文件
	 * 
	 * @param srcFileName
	 *            待复制文件的文件名
	 * @param destFileName
	 *            目标文件名
	 * @return 如果复制成功返回true，否则返回false
	 */
    public synchronized static void copyPythonFile(int sessionId, String srcFileName) {
    	
		//String chromeDriverDir = WebDriverConfig.WINDOWS_PREFIX + File.separator + WebDriverConfig.CHROME_DRIVERS_FOLDER;
		String pythonPluginFolder = WebDriverConfig.WINDOWS_PREFIX + File.separator + WebDriverConfig.FLOOD + File.separator + WebDriverConfig.SESSION + sessionId;

		boolean isWindowsHost = ConfigFacade.isWindowsHost();
		if (!isWindowsHost) {
			pythonPluginFolder = File.separator + WebDriverConfig.FLOOD + File.separator + WebDriverConfig.SESSION + sessionId;
		}
		
		try {
			Path driversFolder = Paths.get(pythonPluginFolder);
			boolean foldExists = Files.exists(driversFolder, new LinkOption[] { LinkOption.NOFOLLOW_LINKS });
			if( !foldExists ) {
				Files.createDirectories(Paths.get(pythonPluginFolder));
				logger.info("-----------------create Python plugin Directory: " + pythonPluginFolder);
			}
			
			//sample python file
			//.\download\app_rule_101_1_1_v172.jar

			Path scriptFilePath = Paths.get(pythonPluginFolder + File.separator + ScriptFile.SCRIPT_PY);
			Files.deleteIfExists(scriptFilePath);
				
			Files.copy(Paths.get(srcFileName), scriptFilePath, StandardCopyOption.COPY_ATTRIBUTES);
    		logger.info("---------复制Python plugin 文件: " + "源文件:->  "+ srcFileName + " 到目标文件:-> " + pythonPluginFolder + File.separator + ScriptFile.SCRIPT_PY);
			
		} catch (Exception e) {
			logger.error("-----------------copyFile: ", e);
		}
    }
    
    /**
	 * 复制文件
	 * 
	 * @param srcFileName
	 *            待复制文件的文件名
	 * @param destFileName
	 *            目标文件名
	 * @param isDeleteTargetFile 是否删除已经存在的目标文件
	 *            true
	 *            false
	 * @return 如果复制成功返回true，否则返回false
	 */
    public synchronized static void copyPythonFile(int sessionId, String srcFileName, String destFileName, boolean isDeleteTargetFile) {
    	
		//String chromeDriverDir = WebDriverConfig.WINDOWS_PREFIX + File.separator + WebDriverConfig.CHROME_DRIVERS_FOLDER;
		String pythonFileFolder = WebDriverConfig.WINDOWS_PREFIX + File.separator + WebDriverConfig.FLOOD + File.separator + WebDriverConfig.SESSION + sessionId;

		boolean isWindowsHost = ConfigFacade.isWindowsHost();
		if (!isWindowsHost) {
			pythonFileFolder = File.separator + WebDriverConfig.FLOOD + File.separator + WebDriverConfig.SESSION + sessionId;
		}
		
		try {
			Path driversFolder = Paths.get(pythonFileFolder);
			boolean foldExists = Files.exists(driversFolder, new LinkOption[] { LinkOption.NOFOLLOW_LINKS });
			if( !foldExists ) {
				Files.createDirectories(Paths.get(pythonFileFolder));
				logger.info("-----------------create Python plugin Directory: " + pythonFileFolder);
			}
			
			//sample python file
			//.\download\app_rule_101_1_1_v172.jar

			Path pythonFilePath = Paths.get(pythonFileFolder + File.separator + destFileName);
			logger.info("======pythonFilePath:" + pythonFilePath);
			if ( isDeleteTargetFile ) {
				
				Files.deleteIfExists(pythonFilePath);
				
			} else {
				
				if (Files.exists(pythonFilePath,new LinkOption[] { LinkOption.NOFOLLOW_LINKS })) {
					return;

				}
			}

				
			Files.copy(Paths.get(srcFileName), pythonFilePath, StandardCopyOption.COPY_ATTRIBUTES);
    		logger.info("---------复制Python plugin 文件: " + "源文件:->  "+ srcFileName + " 到目标文件:-> " + pythonFilePath);
			
		} catch (Exception e) {
			logger.error("-----------------copyFile: ", e);
		}
    }

	
	public static synchronized void killChromeProcessOnly(int sessid) {
		try {
			String strSessid = StringUtil.preZeroAdd(sessid);
			
        	String chromeDriverName = WebDriverConfig.CHROMEDRIVER_V2_43 + "_" + strSessid + WebDriverConfig.EXE;

			boolean isWindowsHost = ConfigFacade.isWindowsHost();
			if (isWindowsHost) {
				
					String killCmd = "TASKKILL /IM " + chromeDriverName + " /F /T";
					String[] command = { "cmd", "/c", killCmd};
				
					Process p = Runtime.getRuntime().exec(command);
					int exitVal = p.waitFor();
					logger.info("--------------------------------killed chrome process result: " + chromeDriverName + " kill result --- " + exitVal);

			} else {
				// pstree | pidof chromedriver -p| awk -F"[()]" '{print $2}'| xargs kill -9
				// String linuxKillProcessCommand = "pstree | pidof " + chromeDriverName + " -p| awk -F\"[()]\" '{print $2}'| xargs kill -9";
				// ps -aux | grep chromedriver_v2.27_12_* | grep -v grep | awk '{print $2}' | xargs pstree -p | awk -F"[()]" '{print $2}' | xargs kill -9
				// ps -aux | grep chromedriver_v2.27_2_* | grep -v grep | awk '{print $2}' | xargs pstree -p | awk -F"[()]" '{print $2}' | xargs kill -9
				chromeDriverName = WebDriverConfig.CHROMEDRIVER_V2_43 + "_" + strSessid;
//				String linuxKillProcessCommand = "ps -aux | grep " + chromeDriverName + " | grep -v grep | awk '{print $2}' | xargs pstree -p | awk -F\"[()]\" '{print $2}' | xargs kill -9";
				
//				 pids=`ps -aux | grep chromedriver_v2.27_2 | grep -v grep | awk '{print $2}'`;for pid in ${pids[@]};do pstree -p $pid|awk -F"[()]" '{print $2}' | xargs kill -9;done
				//String linuxKillProcessCommand = "#!/bin/bash  pids=`ps -aux | grep " + chromeDriverName + " | grep -v grep | awk '{print $2}'`; for pid in ${pids[@]}; do pstree -p $pid | awk -F\"[()]\" '{print $2}' | xargs kill -9;done";
				String linuxKillProcessCommand = "/flood/kill_chrome_driver.sh " + chromeDriverName; 
				logger.info("--------------------------------linuxKillProcessCommand: " + linuxKillProcessCommand);
				//Process p = Runtime.getRuntime().exec(new String[] { "/bin/sh", "-c", linuxKillProcessCommand});
				ProcessBuilder pb = new ProcessBuilder("/bin/sh", "-c", linuxKillProcessCommand);
				Process p = pb.start();
				/*BufferedReader reader = new BufferedReader(new InputStreamReader(p.getErrorStream()));
		        String line;		        
		        while((line = reader.readLine()) != null) {		            
		        	logger.info("kill process ---------" + line);		            
		        }*/
				int exitVal = p.waitFor();
				logger.info("--------------------------------killed chrome process result: " + chromeDriverName + " kill result --- " + exitVal);
			}
			
			logger.info("--------------------------------killed chrome process: " + chromeDriverName);
			logger.info("--------------------------------sleep 3 seconds.");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				logger.warn("InterruptedException ", e1);
			}
		} catch (Throwable e1) {
			logger.warn("can't kill the chromeDriver " + sessid, e1);
		}
	}
	
	
	public static synchronized void deleteChromeData(int sessid){
		
		
		try {
			String rootDirectory = WebDriverConfig.WINDOWS_PREFIX + File.separator + WebDriverConfig.CHROME_DATA;
			boolean isWindowsHost = ConfigFacade.isWindowsHost();
			if (!isWindowsHost) {
				rootDirectory = File.separator + WebDriverConfig.DATA_DIR + File.separator + WebDriverConfig.CHROME_DATA;
			}

			String chromeDataFolder = rootDirectory + File.separator + "chrome" + sessid;

			FileUtil.deleteFiles(chromeDataFolder);
			logger.info("--------------------------------deleted folder: " + chromeDataFolder);

			logger.info("----------------------deleteChromeData sleep 3 seconds.");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				logger.warn("InterruptedException ", e1);
			}
			
		} catch (Exception e) {
			logger.warn("can't delete Chrome data Folder : " + sessid, e);
		}
		
	}
	
	
	public static synchronized void restartComputer() {
		try {
			
//			String linuxKillProcessCommand = "/flood/kill_chrome_driver.sh " + chromeDriverName; 
//			logger.info("--------------------------------linuxKillProcessCommand: " + linuxKillProcessCommand);
//			//Process p = Runtime.getRuntime().exec(new String[] { "/bin/sh", "-c", linuxKillProcessCommand});
//			
			String command = "shutdown -r -time 1";
			
			boolean isWindowsHost = ConfigFacade.isWindowsHost();
			if (!isWindowsHost) {
				command = "shutdown -r -time 1";
				ProcessBuilder pb = new ProcessBuilder("/bin/sh", "-c", command);
				Process process = pb.start();
			}
			
			Process process = Runtime.getRuntime().exec(command);
			int exitVal = process.waitFor();
			logger.info("--------------------------------shutdown process result: " + exitVal);

		} catch (Throwable e1) {
			logger.warn("can't restart the computer ", e1);
		}
	}
		
	static class ChromeDriverFilter implements FilenameFilter {
		private String type;

		public ChromeDriverFilter(String type) {
			this.type = type;
		}

		public boolean accept(File dir, String name) {
			return name.contains(type);
		}
	}
	
	 public static void checkPath(String path) {
	        File file = new File(path);
	        // 如果文件夹不存在则创建
	        if (!file.exists() && !file.isDirectory()) {
	            file.mkdirs();
	        }
	    }
	 
	 public static String getImageFolderPath() {

			String imageFolderPath = WebDriverConfig.WINDOWS_PREFIX + File.separator + WebDriverConfig.WWW + File.separator;
			
			boolean isWindowsHost = ConfigFacade.isWindowsHost();
			if (!isWindowsHost) {
				return File.separator + WebDriverConfig.DATA_DIR + File.separator + WebDriverConfig.WWW + File.separator;
			}

			return imageFolderPath;
		}
}
