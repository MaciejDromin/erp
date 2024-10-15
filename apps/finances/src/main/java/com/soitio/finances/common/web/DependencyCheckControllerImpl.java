package com.soitio.finances.common.web;

import com.soitio.commons.dependency.DependencyCheckMap;
import com.soitio.commons.dependency.model.DependencyCheckRequest;
import com.soitio.commons.dependency.model.DependencyCheckResponse;
import com.soitio.commons.dependency.web.DependencyCheckControllerBaseImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dependencies")
public class DependencyCheckControllerImpl extends DependencyCheckControllerBaseImpl {

    public DependencyCheckControllerImpl(DependencyCheckMap dependencyCheckMap) {
        super(dependencyCheckMap);
    }

    @Override
    @PostMapping
    public DependencyCheckResponse check(@RequestBody DependencyCheckRequest dependencyCheckRequest) {
        return this.handle(dependencyCheckRequest);
    }

}
