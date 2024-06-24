//package com.fever.events.infrastructure.repository;
//
//import com.fever.events.infrastructure.entities.BaseEventEntity;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//
//import java.time.LocalDateTime;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.when;
//
//@DataJpaTest
//@EnableJpaRepositories(basePackageClasses = BaseEventRepository.class)
//public class BaseEventRepositoryTest {
//
//    @Mock
//    private BaseEventRepository baseEventRepository;
//
//    @InjectMocks
//    private BaseEventRepositoryTest baseEventRepositoryTest;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testFindEventsByDateRangeAndSellModeOnline() {
//        LocalDateTime startsAt = LocalDateTime.of(2023, 1, 1, 0, 0);
//        LocalDateTime endsAt = LocalDateTime.of(2023, 12, 31, 23, 59);
//
//        BaseEventEntity mockEvent = new BaseEventEntity();
//        mockEvent.setId(1L);
//        mockEvent.setTitle("Test Event");
//
//        when(baseEventRepository.findEventsByDateRangeAndSellModeOnline(startsAt, endsAt))
//                .thenReturn(Collections.singletonList(mockEvent));
//
//        List<BaseEventEntity> events = baseEventRepository.findEventsByDateRangeAndSellModeOnline(startsAt, endsAt);
//
//        assertEquals(1, events.size());
//        assertEquals("Test Event", events.get(0).getTitle());
//    }
//
//    @Test
//    void testFindBaseEventByBaseEventId() {
//        String baseEventId = "1";
//
//        BaseEventEntity mockEvent = new BaseEventEntity();
//        mockEvent.setId(1L);
//        mockEvent.setTitle("Test Event");
//
//        when(baseEventRepository.findBaseEventByBaseEventId(baseEventId))
//                .thenReturn(Optional.of(mockEvent));
//
//        Optional<BaseEventEntity> event = baseEventRepository.findBaseEventByBaseEventId(baseEventId);
//
//        assertTrue(event.isPresent());
//        assertEquals("Test Event", event.get().getTitle());
//    }
//}
