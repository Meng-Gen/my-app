package com.menggen.app;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

public class App {
    public static void main(String[] args) {
    	ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());

	    try {
	    	for (int i = 0; i < 10; i++) {
	    		final int j = i;
		        final ListenableFuture<String> listenableFuture = service.submit(new Callable<String>() {
		            @Override
		            public String call() throws Exception {
		                return String.valueOf(j);
		            }
		        });

		        Futures.addCallback(listenableFuture, new FutureCallback() {
		            @Override
		            public void onFailure(Throwable thrown) {
		                System.out.println(thrown);
		            }

		            @Override
		            public void onSuccess(Object result) {
		                System.out.println((String)result);
		            }
		        });
	    	}
	    } finally {
      		Preconditions.checkNotNull(service);
      		service.shutdown();
      	}
    }
}
