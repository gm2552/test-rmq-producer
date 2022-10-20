package com.example.demo.function;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import org.springframework.cloud.function.context.PollableBean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SynchronousSink;

@Configuration
@Slf4j
public class MessageProducer 
{
	protected AtomicInteger cnt = new AtomicInteger(1);
	
	@PollableBean
	public Supplier<Flux<String>> createHelloString()
	{
		return () ->
		{
			log.info("Generating strings to send.");
			

			final Flux<String> strings = Flux.generate((SynchronousSink<String> synchronousSink) -> {
				   synchronousSink.next("Hello " + cnt.getAndIncrement());
				});
			
			return strings.take(10);
			
		};
	}
	
	
}
