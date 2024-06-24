package com.fever.events.infrastructure.adapters;

import com.fever.events.infrastructure.entities.BaseEventEntity;
import com.fever.events.infrastructure.entities.EventEntity;
import com.fever.events.infrastructure.entities.ZoneEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EventProviderAdapter {

    private static final Logger logger = LoggerFactory.getLogger(EventProviderAdapter.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    @Value("${provider.url}")
    private String providerUrl;

    private static void setBaseEventAttributes(BaseEventEntity baseEvent, NodeList baseEventNodes, int i) {
        baseEvent.setBaseEventId(baseEventNodes.item(i).getAttributes().getNamedItem("base_event_id").getNodeValue());
        baseEvent.setSellMode(baseEventNodes.item(i).getAttributes().getNamedItem("sell_mode").getNodeValue());
        baseEvent.setTitle(baseEventNodes.item(i).getAttributes().getNamedItem("title").getNodeValue());
    }

    private static void setEventAttributes(EventEntity event, NodeList eventNodes, int j) {
        event.setEventId(eventNodes.item(j).getAttributes().getNamedItem("event_id").getNodeValue());
        event.setSoldOut(Boolean.parseBoolean(eventNodes.item(j).getAttributes().getNamedItem("sold_out").getNodeValue()));

        try {
            event.setEventStartDate(LocalDateTime.parse(eventNodes.item(j).getAttributes().getNamedItem("event_start_date").getNodeValue(), formatter));
        } catch (DateTimeParseException e) {
            System.err.println("Invalid event start date: " + e.getParsedString());
        }

        try {
            event.setEventEndDate(LocalDateTime.parse(eventNodes.item(j).getAttributes().getNamedItem("event_end_date").getNodeValue(), formatter));
        } catch (DateTimeParseException e) {
            System.err.println("Invalid event end date: " + e.getParsedString());
        }

        try {
            event.setSellFrom(LocalDateTime.parse(eventNodes.item(j).getAttributes().getNamedItem("sell_from").getNodeValue(), formatter));
        } catch (DateTimeParseException e) {
            System.err.println("Invalid sell from date: " + e.getParsedString());
        }

        try {
            event.setSellTo(LocalDateTime.parse(eventNodes.item(j).getAttributes().getNamedItem("sell_to").getNodeValue(), formatter));
        } catch (DateTimeParseException e) {
            System.err.println("Invalid sell to date: " + e.getParsedString());
        }
    }

    private static void setZoneAttributes(ZoneEntity zone, NodeList zoneNodes, int k, EventEntity event) {
        zone.setZoneId(zoneNodes.item(k).getAttributes().getNamedItem("zone_id").getNodeValue());
        zone.setCapacity(Integer.parseInt(zoneNodes.item(k).getAttributes().getNamedItem("capacity").getNodeValue()));
        zone.setPrice(Double.parseDouble(zoneNodes.item(k).getAttributes().getNamedItem("price").getNodeValue()));
        zone.setName(zoneNodes.item(k).getAttributes().getNamedItem("name").getNodeValue());
        zone.setNumbered(Boolean.parseBoolean(zoneNodes.item(k).getAttributes().getNamedItem("numbered").getNodeValue()));
        zone.setEvent(event);
    }

    public List<BaseEventEntity> fetchEvents() {
        logger.info("Fetching events from provider URL: {}", providerUrl);
        RestTemplate restTemplate = new RestTemplate();
        String xmlResponse;
        try {
            xmlResponse = restTemplate.getForObject(providerUrl, String.class);
        } catch (Exception e) {
            logger.error("Error fetching events from provider", e);
            return new ArrayList<>();
        }

        return parseEvents(xmlResponse);
    }

    public List<BaseEventEntity> parseEvents(String xml) {
        List<BaseEventEntity> baseEvents = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xml)));

            NodeList baseEventNodes = doc.getElementsByTagName("base_event");
            List<EventEntity> events = new ArrayList<>();

            for (int i = 0; i < baseEventNodes.getLength(); i++) {
                NodeList eventNodes = baseEventNodes.item(i).getChildNodes();
                BaseEventEntity baseEvent = new BaseEventEntity();
                setBaseEventAttributes(baseEvent, baseEventNodes, i);

                for (int j = 0; j < eventNodes.getLength(); j++) {
                    if (eventNodes.item(j).getNodeName().equals("event")) {
                        EventEntity event = new EventEntity();
                        setEventAttributes(event, eventNodes, j);
                        baseEvent.setEvents(event);
                        NodeList zoneNodes = eventNodes.item(j).getChildNodes();
                        List<ZoneEntity> zones = new ArrayList<>();

                        for (int k = 0; k < zoneNodes.getLength(); k++) {
                            if (zoneNodes.item(k).getNodeName().equals("zone")) {
                                ZoneEntity zone = new ZoneEntity();
                                setZoneAttributes(zone, zoneNodes, k, event);
                                zones.add(zone);
                            }
                        }
                        event.setZones(zones);
                        event.setBaseEvent(baseEvent);
                        events.add(event);
                    }
                }

                baseEvents.add(baseEvent);
            }
        } catch (Exception e) {
            logger.error("Error parsing events from XML", e);
        }

        logger.info("Finished parsing events, total events: {}", baseEvents.size());
        return baseEvents;
    }
}
