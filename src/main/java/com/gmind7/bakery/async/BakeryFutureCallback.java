package com.gmind7.bakery.async;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

@Service
@Slf4j
public class BakeryFutureCallback {

	@Autowired
	private BakerTask bakerTask;
	
	public void doAction(){
		
		StopWatch watch = new StopWatch();
		
		watch.start();
		
        try {
        	
        	String[] searchKeys = {"gmind1", "gmind2", "gmind3", "gmind4", "gmind5", "gmind6", "gmind7"};
			
			final CountDownLatch latch = new CountDownLatch(searchKeys.length);
			
			for(String key: searchKeys) {
				ListenableFuture<Baker> future = (ListenableFuture<Baker>) bakerTask.findOne(key);
				FutureCallback<Baker> findResult = new FutureCallback<Baker>() {
					public void onSuccess(Baker baker) {
						latch.countDown();
						log.debug("futureCallback baker id:{} name:{}", new Object[] { baker.getId(), baker.getName(), });
					}
	
					public void onFailure(Throwable thrown) {
						latch.countDown();
						log.warn(thrown.getMessage());
					}
				};
				Futures.addCallback(future, findResult);
			}
			
			latch.await();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		watch.stop();
		
		log.debug("Time: {}", watch.getTotalTimeMillis());
		
	}
}
