package com.educacional.schoolacademicperiods.infra.commons;

import com.educacional.schoolacademicperiods.domain.exception.ExceptionCode;
import com.educacional.schoolacademicperiods.domain.exception.InternalServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;

@Repository
@RequiredArgsConstructor
@Slf4j
public class DeletedRecord {
  private static final String COLLECTION_NAME = "deletedRecords";
  private final MongoTemplate mongoTemplate;

  public void saveDeletedRecord(String id, Object deletedRecord, Instant deletedAt) {

    log.info("Saving deleted record: [id: {}, data: {}]", id, deletedRecord);

    DeletedRecordEntity deletedRecordEntity = new DeletedRecordEntity(id, deletedRecord, deletedAt);

    try {
      mongoTemplate.save(deletedRecordEntity, COLLECTION_NAME);
    } catch (Exception e) {
      log.error("An internal server error has occurred during role deletion");
      log.error(e.getMessage(), e);
      throw new InternalServiceException(ExceptionCode.INTERNAL_SERVER_ERROR);
    }
  }

  public void deleteDeletedRecord(String id) {
    log.info("Removing deleted record: [id {}", id);

    var query = Query.query(Criteria.where("_id").is(id));

    mongoTemplate.remove(query, COLLECTION_NAME);
  }
}
