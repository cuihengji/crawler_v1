package com.rkylin.crawler.engine.flood.websocket;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.apache.log4j.Logger;

import com.rkylin.crawler.engine.flood.model.SimpleProxyContent;
import com.rkylin.crawler.engine.flood.proxy.client.ProxyFacade;
import com.rkylin.crawler.engine.flood.util.JsonUtil;
 
//该注解用来指定一个URI，客户端可以通过这个URI来连接到WebSocket。类似Servlet的注解mapping。无需在web.xml中配置。
@ServerEndpoint("/proxyAddressProducer")
public class ProxyAddressProducer {
    
    private static final Logger logger = Logger.getLogger(ProxyAddressProducer.class);

	
	//静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static AtomicInteger onlineSessionBrowserCount = new AtomicInteger(); 
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    private static ConcurrentHashMap<String, Session> onlineSessionHashMap = new ConcurrentHashMap<>();
     
    
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    public Session session;
    
	/**
     * 连接建立成功调用的方法
     * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        
        String sessionId = session.getQueryString().split("=")[1];
        onlineSessionHashMap.put(sessionId, session);     //加入set中
        addOnlineCount();           //在线数加1
      
        logger.info("有新连接加入！当前在线人数为 " + getOnlineCount() + " SessionId: " + sessionId);
    }
     
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(){
    	logger.info("removed session Id:"+ session.getQueryString().split("=")[1]);
    	System.out.println("removed session Id:"+ session.getQueryString().split("=")[1]);
    	onlineSessionHashMap.remove(session.getQueryString().split("=")[1]);  //从set中删除
    	
        subOnlineCount();           //在线数减1    
    	logger.info("有一连接关闭！当前在线人数为" + getOnlineCount());

    }
     
    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
    	logger.info("来自客户端的消息:" + message + "session :" + session);
    	
		if (message.contains("ip") && (message.contains("port"))) {
			try {
				SimpleProxyContent ngProxy = JsonUtil.convertJsonStr2Obj(message, SimpleProxyContent.class);
				logger.info("ngProxy:" + ngProxy + " ngProxy Ip:" + ngProxy.getIp() + " ngProxy Port:" + ngProxy.getPort());
				ProxyFacade.getInstance().reportNGProxy(0, 0, ngProxy.getIp(), ngProxy.getPort());
			} catch (Exception e) {
				logger.error("onMessage error:  ", e);
			}
		}

    }
     
    /**
     * 发生错误时调用
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error){
    	logger.error("发生错误 " + session.getQueryString().split("=")[1] + " : " + error);
        //error.printStackTrace();
    }
     
    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException{
    	
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }
 
	public static void sendMessage(String sessionId, String message) throws IOException {

		for (String currentProxyAddressSession : onlineSessionHashMap.keySet()) {
			if (sessionId.equalsIgnoreCase(currentProxyAddressSession)) {
			
				onlineSessionHashMap.get(currentProxyAddressSession).getBasicRemote().sendText(message);
		    	
		    	logger.info("--------------------------: webSocketHashMap.get(currentProxyAddressSession).session Id " + currentProxyAddressSession);
		    	logger.info("--------------------------: webSocketHashMap.get(currentProxyAddressSession) message: " + message);
		    	
				break;
			}
		}
	}

    public static AtomicInteger getOnlineCount() {
        return onlineSessionBrowserCount;
    }
 
    public static void addOnlineCount() {
    	onlineSessionBrowserCount.incrementAndGet();
    }
     
    public static void subOnlineCount() {
    	onlineSessionBrowserCount.decrementAndGet();
    }
}