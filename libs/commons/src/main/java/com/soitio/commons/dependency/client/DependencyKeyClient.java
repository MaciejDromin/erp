package com.soitio.commons.dependency.client;

import com.soitio.commons.dependency.model.StoreKey;
import java.util.Set;

public interface DependencyKeyClient {

    boolean updateKey(String key, Set<String> services);

    StoreKey getCurrentValue(String key);

}
