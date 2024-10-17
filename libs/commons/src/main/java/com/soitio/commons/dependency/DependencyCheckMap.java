package com.soitio.commons.dependency;

import com.soitio.commons.dependency.model.Dependent;
import java.util.List;
import java.util.Map;

public class DependencyCheckMap {

    private final Map<Dependent, List<DependencyCheckService>> dependentListMap;

    public DependencyCheckMap(Map<Dependent, List<DependencyCheckService>> dependentListMap) {
        this.dependentListMap = dependentListMap;
    }

    public List<DependencyCheckService> getServicesForDependent(Dependent dependent) {
        return dependentListMap.get(dependent);
    }

}
