package com.fever.events.infrastructure.repository;

import com.fever.events.infrastructure.entities.BaseEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BaseEventRepository extends JpaRepository<BaseEventEntity, Long> {

    @Query("SELECT be " + "FROM BaseEventEntity be " + "JOIN be.events e " + "JOIN e.zones z " + "WHERE CAST(e.eventStartDate AS localdatetime) >= :startsAt " + "AND CAST(e.eventEndDate AS localdatetime ) <= :endsAt " + "AND be.sellMode = :sellMode")
    List<BaseEventEntity> findEventsByDateRangeAndSellModeOnline(@Param("startsAt") LocalDateTime startsAt, @Param("endsAt") LocalDateTime endsAt, @Param("sellMode") String sellMode);

    @Query("SELECT be FROM BaseEventEntity be WHERE be.baseEventId = :baseEventId")
    Optional<BaseEventEntity> findBaseEventByBaseEventId(@Param("baseEventId") String baseEventId);
}
