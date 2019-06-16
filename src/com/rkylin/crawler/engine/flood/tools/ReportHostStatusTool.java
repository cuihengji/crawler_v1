package com.rkylin.crawler.engine.flood.tools;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.ProcMem;
import org.hyperic.sigar.ProcState;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import com.rkylin.crawler.engine.flood.model.HostStatus;
import com.rkylin.crawler.engine.flood.monitor.client.MonitorFacade;
import com.rkylin.crawler.engine.flood.pausecrawler.SessionStatus;
import com.web2data.__main.LocalConfigLoader;
import com.web2data.system.config._main.ConfigFacade;
import com.web2data.system.config.entity.host_of_crawler;

public class ReportHostStatusTool {
	
    private static final transient Logger logger = Logger.getLogger(ReportHostStatusTool.class);

    private static Sigar sinarInstance = null;

    private static 	ConcurrentHashMap <Integer, SessionStatus> sessionStatusMap  = new ConcurrentHashMap <Integer, SessionStatus>();

	public static Sigar getSigarInstance() {
		if (sinarInstance == null) {
			sinarInstance = new Sigar();
		}
		return sinarInstance;
	}
    
    public static void scheduleReportHostStatus(ConfigFacade cf) {
        ThreadPoolManager4Tool.sexec(new Runnable() {
            @Override
            public void run() {
                HostStatus hostStatus = getHostStatus(cf);
                // 调用php保存数据到monitor
                MonitorFacade.getInstance().doCrawlerReportByPhp(hostStatus);
            }
        }, 0, 5, TimeUnit.MINUTES);
    }
    
    /**
     * 获取本机（爬虫机）的监控信息
     */
    public static HostStatus getHostStatus(ConfigFacade cf){
    	
    	host_of_crawler hoc = cf.getLocalCrawler();
    	
		HostStatus hostStatus = new HostStatus();
		hostStatus.setHostSeq(hoc.getSeq());
		hostStatus.setHostName(hoc.getName());
		hostStatus.setCompanySeq(hoc.getCompany_seq());
		hostStatus.setCompanyName("-1");
		hostStatus.setSiteSeq(hoc.getSite_seq());
		hostStatus.setSiteName("-1");
		
		int cpu = 0;
		int mem = 0;
		int diskRate = 0;
		
		try {
			
			Sigar sigar = new Sigar();
			
			cpu = (int) Math.ceil(Double.parseDouble(CpuPerc.format(
					sigar.getCpuPerc().getCombined()).replace("%", "")));
			mem = (int) Math.ceil(sigar.getMem().getUsedPercent());
			
			hostStatus.setCpuRate(cpu);
			hostStatus.setMemoryRate(mem);
			hostStatus.setDiskRate(0);
			hostStatus.setJavaMemory(0);
			hostStatus.setMysqlMemory(0);
			hostStatus.setFiddlerMemory(0);
			hostStatus.setChromes(0);
			hostStatus.setChromeMemory(0);
			hostStatus.setChromeDrivers(0);
			hostStatus.setChromeDriverMemory(0);
			
			long[] pids = sigar.getProcList();
			for (long pid : pids) {
				ProcState prs = sigar.getProcState(pid);
				if (prs == null) {
					continue;
				}
				String name = prs.getName();
                if("java".equals(name) || "Tomcat8".equals(name)){
					ProcMem procMem = sigar.getProcMem(pid);
					hostStatus.setJavaMemory((int)( procMem.getResident()/(1024L * 1024L) )  + hostStatus.getJavaMemory());
				}
				else if("mysqld".equals(name)){	
					ProcMem procMem = sigar.getProcMem(pid);
					hostStatus.setMysqlMemory((int)( procMem.getResident()/(1024L * 1024L) )  + hostStatus.getMysqlMemory());
				}
				else if("Fiddler".equals(name)){
					ProcMem procMem = sigar.getProcMem(pid);
					hostStatus.setFiddlerMemory((int)( procMem.getResident()/(1024L * 1024L) ) + hostStatus.getFiddlerMemory());
				}
				else if("chrome".equals(name)){
					hostStatus.setChromes(hostStatus.getChromes() + 1);
					
					ProcMem procMem = sigar.getProcMem(pid);
					hostStatus.setChromeMemory((int)( procMem.getResident()/(1024L * 1024L) ) + hostStatus.getChromeMemory());
				}
				else if( name != null && name.startsWith("chromedriver_")) {
					hostStatus.setChromeDrivers(hostStatus.getChromeDrivers() + 1);
					
					ProcMem procMem = sigar.getProcMem(pid);
					hostStatus.setChromeDriverMemory((int)( procMem.getResident()/(1024L * 1024L) ) + hostStatus.getChromeDriverMemory());
				}
			}
			
			long usedDisk = 0L;
			long totalDisk = 0L;
			boolean isWindowsHost = ConfigFacade.isWindowsHost();
			FileSystem[] fileSystemArray = sigar.getFileSystemList();
			for ( FileSystem fileSystem:fileSystemArray ) {
				
				if (isWindowsHost) {
					if (!fileSystem.getDevName().equals("C:\\")&&!fileSystem.getDevName().equals("c:\\")) {
						continue;
					}
				}
				
	            FileSystemUsage fileSystemUsage = null;    
	            try {
	                fileSystemUsage = sigar.getFileSystemUsage(fileSystem.getDirName());  
	            } catch (SigarException e) {//当fileSystem.getType()为5时会出现该异常——此时文件系统类型为光驱  
	            	 logger.error("SigarException", e);
	                continue;  
	            }
	            
	            usedDisk = fileSystemUsage.getUsed() + usedDisk;
	            totalDisk = fileSystemUsage.getTotal() + totalDisk;
			}
			
			if (totalDisk != 0L) {
				diskRate = (int)(((double)usedDisk / (double)totalDisk)*100);
			}
			else {
				diskRate = -1;
			}
			
			hostStatus.setDiskRate(diskRate);
			
			logger.info("*************diskRate:"  + diskRate);
			
		} catch (NumberFormatException e) {
		    logger.error(e.getMessage(), e);
		} catch (SigarException e) {
		    logger.error("No such process!");
		} catch (Throwable e) {
			logger.error("!!!!!getHostStatus异常发生!!!!!", e);
		}	
		return hostStatus;
	}
    
    /**
     * 获取本机（爬虫机）的监控信息
     */
    public static HostStatus getHostCpuAndMemoryStatus(ConfigFacade cf){
    	
    	host_of_crawler hoc = cf.getLocalCrawler();
    	
		HostStatus hostStatus = new HostStatus();
		hostStatus.setHostSeq(hoc.getSeq());
		hostStatus.setHostName(hoc.getName());
		hostStatus.setCompanySeq(hoc.getCompany_seq());
		hostStatus.setCompanyName("-1");
		hostStatus.setSiteSeq(hoc.getSite_seq());
		hostStatus.setSiteName("-1");
		
		int cpu = -1;
		int mem = -1;
		
		try {
			Sigar sigar = new Sigar();
			
			String cpuInfo = CpuPerc.format(sigar.getCpuPerc().getCombined()).replace("%", "");
			if(cpuInfo.equalsIgnoreCase("N")){
				logger.error("can't get the CPU usage!!!" +  cpuInfo);	
			}else{
				cpu = (int) Math.ceil(Double.parseDouble(cpuInfo));
			}
			hostStatus.setCpuRate(cpu);
			
			mem = (int) Math.ceil(sigar.getMem().getUsedPercent());
			hostStatus.setMemoryRate(mem);
			
		} catch (NumberFormatException e) {
		    logger.error(e.getMessage(), e);
		} catch (SigarException e) {
		    logger.error("No SigarException process!", e);
		} catch (Throwable e) {
			
			logger.error("!!!!!getHostCpuAndMemoryStatus异常发生!!!!!", e);
			logger.info("sleep 3 seconds since getHostCpuAndMemoryStatus异常发生!!!!!");

			try {
				Thread.sleep(3000);
			} catch (InterruptedException e1) {
				logger.error("InterruptedException", e);
			}
			//set default value as -1
			hostStatus.setCpuRate(cpu);
			hostStatus.setMemoryRate(mem);
		}
		return hostStatus;
	}
    
    public static boolean isCpuBusy() {
    	
    	boolean isBusy = true;
		
    	HostStatus hostStatus = ReportHostStatusTool.getHostCpuAndMemoryStatus(ConfigFacade.getInstance());
    	
    	if (hostStatus == null) {
    		return false;
    	}
    	
		int curCpuRate = hostStatus.getCpuRate();
		int configCpuRate = ConfigFacade.getInstance().getCpuRate();
		logger.info("*****current cpu rate:" + curCpuRate + " config cpu rate:" + configCpuRate);
		
		if (hostStatus.getCpuRate() < configCpuRate) {
			isBusy = false;
		}
		
    	return isBusy;
    }
    
    public static boolean isCpuAndMemoryBusy() {
    	
    	boolean isBusy = true;
    	
		HostStatus hostStatus = ReportHostStatusTool.getHostCpuAndMemoryStatus(ConfigFacade.getInstance());
    	
		int curCpuRate = hostStatus.getCpuRate();
		int configCpuRate = ConfigFacade.getInstance().getCpuRate();
		logger.info("*****current cpu rate:" + curCpuRate + " CONFIG　CPU  rate:" + configCpuRate);
		
		int curMemoryRate = hostStatus.getMemoryRate();
		int configMemoryRate = ConfigFacade.getInstance().getMemoryRate();
		logger.info("*****current Memory rate:" + curMemoryRate + " CONFIG Memory rate:" + configMemoryRate);

		if (hostStatus.getCpuRate() < configCpuRate && hostStatus.getMemoryRate() < configMemoryRate) {
			isBusy = false;
		}
		
    	return isBusy;
    }
    
    
    public static ConcurrentHashMap<Integer, SessionStatus> getSessionStatusMap() {
		return sessionStatusMap;
	}

	public static void setSessionStatusMap(Integer sessionId, SessionStatus sessionStatus) {
		sessionStatusMap.put(sessionId, sessionStatus);
	}

	public static void main(String[] args) {
		
    	try {
			LocalConfigLoader.load();
			 // 取得所有的配置，必须在业务代码之前进行！
	        ConfigFacade cf = ConfigFacade.getInstance();
	        cf.initializeConf();
	    	getHostStatus(cf);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	

//    	for (int i = 0; i < 10; i++) {
//		
//    		try {
//				Thread.sleep(100000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//        	ReportHostStatusTool.isCpuAndMemoryBusy();
//
//		}
//    	long usedDisk = 14004296L;
//    	long totalDisk = 105434160L;
//    	
//    	System.out.println("===" + (int)(((double)usedDisk / (double)totalDisk)*100));
    	
    	
	}
}
