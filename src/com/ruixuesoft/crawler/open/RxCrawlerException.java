package com.ruixuesoft.crawler.open;

public class RxCrawlerException extends RuntimeException {

	private static final long serialVersionUID = 575302167451995047L;

	private int code = 999;
	
	public int getCode() {
		return code;
	}

	public RxCrawlerException(int code, String message) {
		super(message);
		this.code = code;
	}

	public RxCrawlerException(Throwable cause) {
        super(cause);
    }
	
	public RxCrawlerException(String message) {
		super(message);
	}
	
	
}
