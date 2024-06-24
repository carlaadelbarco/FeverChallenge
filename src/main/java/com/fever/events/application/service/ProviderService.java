package com.fever.events.application.service;

import com.fever.events.infrastructure.adapters.EventProviderAdapter;
import com.fever.events.infrastructure.entities.BaseEventEntity;
import com.fever.events.infrastructure.repository.BaseEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderService {

    private static final Logger logger = LoggerFactory.getLogger(ProviderService.class);

    @Autowired
    private EventProviderAdapter eventProviderAdapter;

    @Autowired
    private BaseEventRepository baseEventRepository;

    //     To try de service change this @Scheduled(fixedRate = 12000)
    @Scheduled(fixedRate = 12000)
//     Execution schedule once a day at night
//    @Scheduled(cron = "0 0 3 * * ?")
    public void fetchAndStoreEvents() {
        logger.info("Starting scheduled task to fetch and store events.");

        try {
            List<BaseEventEntity> baseEvents = eventProviderAdapter.fetchEvents();
            logger.info("Fetched {} events from the provider.", baseEvents.size());

            for (BaseEventEntity baseEvent : baseEvents) {
                Optional<BaseEventEntity> existingEvent = baseEventRepository.findBaseEventByBaseEventId(baseEvent.getBaseEventId());
                if (existingEvent.isEmpty()) {
                    baseEventRepository.save(baseEvent);
                    logger.info("Saved event with ID {} to the database.", baseEvent.getBaseEventId());
                } else {
                    logger.info("Event with ID {} already exists in the database, skipping save.", baseEvent.getBaseEventId());
                }
            }
        } catch (Exception e) {
            logger.error("Error while fetching and storing events: ", e);
        }

        logger.info("Scheduled task to fetch and store events completed.");
    }

}

