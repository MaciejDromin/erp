package com.soitio.commons.dependency.client;

import com.soitio.commons.dependency.model.KeyValue;
import com.soitio.commons.dependency.model.Session;
import java.util.List;
import java.util.Set;

public interface ConsulStoreClient {

    boolean updateKey(Set<String> services, String key, String sessionId);

    List<KeyValue> getCurrentValue(String key);

    Session createSession();

}
