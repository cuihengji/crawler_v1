package com.web2data.engine.database;

public class ConnectionInfo {

    private String ip;
    private int port;
    private String username;
    private String password;
    private String schema;
    
    private String databaseType;
    private String oracleSericeValue;
    private String oracleSericeType;
    
    
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public int getPort() {
        return port;
    }
    public void setPort(int port) {
        this.port = port;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getSchema() {
        return schema;
    }
    public void setSchema(String schema) {
        this.schema = schema;
    }
	public String getDatabaseType() {
		return databaseType;
	}
	public void setDatabaseType(String databaseType) {
		this.databaseType = databaseType;
	}
	public String getOracleSericeValue() {
		return oracleSericeValue;
	}
	public void setOracleSericeValue(String oracleSericeValue) {
		this.oracleSericeValue = oracleSericeValue;
	}
	public String getOracleSericeType() {
		return oracleSericeType;
	}
	public void setOracleSericeType(String oracleSericeType) {
		this.oracleSericeType = oracleSericeType;
	}
	
}
