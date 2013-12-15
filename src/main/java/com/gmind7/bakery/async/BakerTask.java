package com.gmind7.bakery.async;

import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

@Component
public class BakerTask {
	
	private final Map<String, Baker> bakers = Maps.newHashMap();
	
	public BakerTask(){
		bakers.put("gmind1", new Baker(1,"gmind1"));
		bakers.put("gmind2", new Baker(2,"gmind2"));
		bakers.put("gmind3", new Baker(3,"gmind3"));
		bakers.put("gmind4", new Baker(4,"gmind4"));
		bakers.put("gmind5", new Baker(5,"gmind5"));
		bakers.put("gmind6", new Baker(6,"gmind6"));
		bakers.put("gmind7", new Baker(7,"gmind7"));
	}
	
	@Async
	public Future<Baker> findOne(String bakerName) {
		try {
			// test thread sleep
			Thread.sleep(ThreadLocalRandom.current().nextInt(3) * 1000);
	    } catch (InterruptedException e) {
	        e.printStackTrace();
	    }
		Baker baker = (Baker)bakers.get(bakerName);
		return new AsyncResult<Baker>(baker);
	}
}
