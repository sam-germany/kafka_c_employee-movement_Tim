package com.course.microservice.scheduler;

import com.course.microservice.broker.message.OutboxPollingMessage;
import com.course.microservice.broker.publisher.OutboxPollingPublisher;
import com.course.microservice.entity.OutboxPolling;
import com.course.microservice.repository.OutboxPollingRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class PollingMessageRelay {

	@Autowired
	private OutboxPollingPublisher publisher;

	@Autowired
	private OutboxPollingRepository repository;

	private OutboxPollingMessage convertToMessage(OutboxPolling outbox) {
		var result = new OutboxPollingMessage();
		
		result.setChangedData(outbox.getChangedData());
		result.setId(outbox.getId());
		result.setTransactionType(outbox.getTransactionType());
		
		return result;
	}

	@Scheduled(fixedDelay = 5000)
	@Transactional
	public void pollAndPublish() {
		var allOutboxData = Lists.newArrayList(repository.findAll());

		for (var outbox : allOutboxData) {
			var outboxPollingMessage = convertToMessage(outbox);

			publisher.publish(outboxPollingMessage);
			repository.delete(outbox);
		}
	}
}
/*(1)
this  pollAndPublish()   method he creates with the @Scheduled(5000)  means every 5_sec it should execute this method
here we are  keep fetching the  "Lists.newArrayList(repository.findAll())"   data from the database, if any data is inserted
then will be fetched automatically and after that that data will be produced by the   "publisher.publish(outboxPollingMessage)"

and after that delete "repository.delete(outbox)"   data from the database, this is the reason from the table outbox_polling in
the mysql i do not find any data from the mysql

 */
