package com.gmind7.bakery.async;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;

@Service
@Slf4j
public class BakeryAsyncFunction {

	@Autowired
	private BakerTask bakerTask;
	
	public void doAction(){
		
		StopWatch watch = new StopWatch();
		
		watch.start();
		
        try {
        	
	        String[] searchKeys = {"gmind1", "gmind2"};
			
			final CountDownLatch latch = new CountDownLatch(searchKeys.length);
			
			List<ListenableFuture<Baker>> futures = Lists.newArrayList();
			
			for(String key: searchKeys) {
				ListenableFuture<Baker> future = (ListenableFuture<Baker>) bakerTask.findOne(key);
				futures.add(future);
			}
			
			ListenableFuture<List<Baker>> collectedResults = Futures.successfulAsList(futures);
			
			AsyncFunction<List<Baker>, List<String>> flattenFunction = new AsyncFunction<List<Baker>, List<String>>() {
				public ListenableFuture<List<String>> apply(List<Baker> bakers) {
					List<String> bakerNames = Lists.newArrayList();
					for(Baker baker: bakers){
						bakerNames.add(baker.getName());
						latch.countDown();
						log.debug("asyncFunction latch Count : {}, baker id:{} name:{}", new Object[] { latch.getCount(), baker.getId(), baker.getName(), });
						}
					SettableFuture<List<String>> constFuture = SettableFuture.create();
					constFuture.set(bakerNames);
					return constFuture;
				}
			};
			
			ListenableFuture<List<String>> futureMergedResult = Futures.transform(collectedResults, flattenFunction);
			
			List<String> mergedResult = futureMergedResult.get(5, TimeUnit.SECONDS);
			
			log.debug("mergedResult : {}", mergedResult.toString());
			
			latch.await(5, TimeUnit.SECONDS);
			
		} catch (ExecutionException | TimeoutException | InterruptedException e) {
			e.printStackTrace();
		}
		
		watch.stop();
		
		log.debug("Time: {}", watch.getTotalTimeMillis());
		
	}
}
