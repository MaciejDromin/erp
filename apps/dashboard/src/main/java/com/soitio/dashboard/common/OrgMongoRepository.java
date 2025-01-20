package com.soitio.dashboard.common;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.mongodb.panache.PanacheQuery;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;

public interface OrgMongoRepository<T> extends PanacheMongoRepository<T> {

    default PanacheQuery<T> findAllByOrgId(String orgId) {
        return find("orgId = ?1", orgId);
    }

    default T findByIdAndOrgId(String id, String orgId) {
        return findByIdAndOrgId(new ObjectId(id), orgId);
    }

    default T findByIdAndOrgId(ObjectId id, String orgId) {
        return find("_id = ?1 and orgId = ?2", id, orgId).firstResult();
    }

    default Optional<T> findByIdAndOrgIdOptional(String id, String orgId) {
        return findByIdAndOrgIdOptional(new ObjectId(id), orgId);
    }

    default Optional<T> findByIdAndOrgIdOptional(ObjectId id, String orgId) {
        return find("_id = ?1 and orgId = ?2", id, orgId).firstResultOptional();
    }

    default long deleteByIdAndOrgId(String id, String orgId) {
        return deleteByIdAndOrgId(new ObjectId(id), orgId);
    }

    default long deleteByIdAndOrgId(ObjectId id, String orgId) {
        return delete("_id = ?1 and orgId = ?2", id, orgId);
    }

    default List<T> listByIdsAndOrgId(Iterable<ObjectId> ids, String orgId) {
        return find("_id in ?1 and orgId = ?2", ids, orgId).list();
    }

}
