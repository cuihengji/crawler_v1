package com.rkylin.crawler.engine.flood.model;

public class HostStatus {
	private int hostSeq;
	private int companySeq;
	private int siteSeq;
	
	private	String	hostName;
	private	String	companyName;
	private	String	siteName;
	
	private	int	cpuRate;
	private	int	memoryRate;
	private int diskRate;
	private int javaMemory;
	private int mysqlMemory;
	private int fiddlerMemory;
	private int chromes;
	private int chromeMemory;
	private int chromeDrivers;
	private int chromeDriverMemory;

	public int getHostSeq() {
		return hostSeq;
	}
	public void setHostSeq(int hostSeq) {
		this.hostSeq = hostSeq;
	}
	public int getCompanySeq() {
		return companySeq;
	}
	public void setCompanySeq(int companySeq) {
		this.companySeq = companySeq;
	}
	public int getSiteSeq() {
		return siteSeq;
	}
	public void setSiteSeq(int siteSeq) {
		this.siteSeq = siteSeq;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public int getCpuRate() {
		return cpuRate;
	}
	public void setCpuRate(int cpuRate) {
		this.cpuRate = cpuRate;
	}
	public int getMemoryRate() {
		return memoryRate;
	}
	public void setMemoryRate(int memoryRate) {
		this.memoryRate = memoryRate;
	}
	public int getDiskRate() {
		return diskRate;
	}
	public void setDiskRate(int diskRate) {
		this.diskRate = diskRate;
	}
	public int getJavaMemory() {
		return javaMemory;
	}
	public void setJavaMemory(int javaMemory) {
		this.javaMemory = javaMemory;
	}
	public int getMysqlMemory() {
		return mysqlMemory;
	}
	public void setMysqlMemory(int mysqlMemory) {
		this.mysqlMemory = mysqlMemory;
	}
	public int getFiddlerMemory() {
		return fiddlerMemory;
	}
	public void setFiddlerMemory(int fiddlerMemory) {
		this.fiddlerMemory = fiddlerMemory;
	}
	public int getChromes() {
		return chromes;
	}
	public void setChromes(int chromes) {
		this.chromes = chromes;
	}
	public int getChromeMemory() {
		return chromeMemory;
	}
	public void setChromeMemory(int chromeMemory) {
		this.chromeMemory = chromeMemory;
	}
	
	
	public int getChromeDrivers() {
		return chromeDrivers;
	}
	public void setChromeDrivers(int chromeDrivers) {
		this.chromeDrivers = chromeDrivers;
	}
	public int getChromeDriverMemory() {
		return chromeDriverMemory;
	}
	public void setChromeDriverMemory(int chromeDriverMemory) {
		this.chromeDriverMemory = chromeDriverMemory;
	}
}
