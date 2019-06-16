package com.rkylin.crawler.engine.flood.redis;

import java.util.List;

import com.web2data.system.config._main.ConfigFacade;
import com.web2data.system.config.entity.host_of_scheduler;

import redis.clients.jedis.Jedis;

public class RedisUtils {

	public final static Jedis getJedis( ) {
		
		List<host_of_scheduler> schedulers = ConfigFacade.getInstance().getAllSchedulerHosts();
		host_of_scheduler scheduler = schedulers.get(0);
		
		Jedis jedis = new Jedis(scheduler.getIp1(), scheduler.getMq_port());
		jedis.auth(scheduler.getMq_password());
		return jedis;
	}
	
}
