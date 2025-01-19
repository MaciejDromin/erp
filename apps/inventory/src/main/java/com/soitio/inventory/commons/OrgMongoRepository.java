package com.soitio.inventory.commons;

import io.quarkus.mongodb.panache.PanacheMongoRepository;
import io.quarkus.mongodb.panache.PanacheQuery;
import org.bson.types.ObjectId;
import java.util.Collection;
import java.util.List;

public interface OrgMongoRepository<T> extends PanacheMongoRepository<T> {

    default PanacheQuery<T> findAllByOrgId(String orgId) {
        return find("orgId", orgId);
    }

    default T findByIdAndOrgId(String id, String orgId) {
        return findByIdAndOrgId(new ObjectId(id), orgId);
    }

    default T findByIdAndOrgId(ObjectId id, String orgId) {
        return find("_id = ?1 and orgId = ?2", id, orgId).firstResult();
    }

    default PanacheQuery<T> findAllByIdsNotInAndOrgId(Collection<String> ids, String orgId) {
        return findAllByIdsNotInAndOrgId(ids.stream()
                .map(ObjectId::new)
                .toList(), orgId);
    }

    default PanacheQuery<T> findAllByIdsNotInAndOrgId(Iterable<ObjectId> ids, String orgId) {
        return find("{_id: { $nin: [?1]}, orgId: ?2}", ids, orgId);
    }
    
    default List<T> listAllByIdsNotInAndOrgId(Collection<String> ids, String orgId) {
        return findAllByIdsNotInAndOrgId(ids, orgId).list();
    }

    default PanacheQuery<T> findAllByIdsInAndOrgId(Collection<String> ids, String orgId) {
        return findAllByIdsInAndOrgId(ids.stream()
                .map(ObjectId::new)
                .toList(), orgId);
    }

    default PanacheQuery<T> findAllByIdsInAndOrgId(Iterable<ObjectId> ids, String orgId) {
        return find("_id in ?1 and orgId = ?2", ids, orgId);
    }

    default List<T> listAllByIdsInAndOrgId(Collection<String> ids, String orgId) {
        return findAllByIdsInAndOrgId(ids, orgId).list();
    }

    default void deleteByIdsAndOrgId(Collection<String> ids, String orgId) {
        deleteByIdsAndOrgId(ids.stream()
                .map(ObjectId::new)
                .toList(), orgId);
    }

    default void deleteByIdsAndOrgId(Iterable<ObjectId> ids, String orgId) {
        delete("_id in ?1 and orgId = ?2", ids, orgId);
    }

}
