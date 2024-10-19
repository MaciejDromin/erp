package com.soitio.commons.dependency

import com.soitio.commons.dependency.model.Dependent
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject;

class DependencyInitializerBaseServiceSpec extends Specification {

    @Shared
    DependencyCheckService depService = Mock()

    @Subject
    DependencyInitializerBaseService service = new DependencyInitializerBaseService(null,
            null,
            [depService]) {

        @Override
        DependencyCheckMap produceDependencyMap() {
            return null
        }
    }

    def setupSpec() {
        depService.getServiceName() >> "TestDependency"
    }

    def "should validate and build dependency map"() {
        given:
        def depsToValidate = [TestDependency.class] as Set

        when:
        def ret = service.validateAndMakeDependencies(depsToValidate)

        then:
        ret.size() == 1
        ret.containsKey(Dependent.INVENTORY_ITEM)
    }

}
