package com.soitio.commons.utils

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.BooleanNode
import com.fasterxml.jackson.databind.node.IntNode
import com.fasterxml.jackson.databind.node.TextNode
import com.soitio.commons.models.commons.MergePatch
import spock.lang.Specification

class MergePatchUtilsSpec extends Specification {

    ObjectMapper om = new ObjectMapper();

    def "should properly map into MergePatch"() {
        given:
        JsonNode input = om.createObjectNode()
        input.set("test", new IntNode(5))
        input.set("test2", new TextNode("test"))
        JsonNode node = om.createObjectNode()
        node.set("test4", BooleanNode.TRUE)
        input.set("test3", node)
        JsonNode node2 = om.createObjectNode()
        node2.set("test5", BooleanNode.TRUE)
        JsonNode arr = om.createArrayNode()
        arr.add(5)
        arr.add(5.5)
        arr.add("test")
        arr.add(node2)
        input.set("test6", arr)

        when:
        def result = MergePatchUtils.fromJsonNode(input)

        then:
        with (result) {
            objectType == MergePatch.ObjectType.OBJECT
            with (objectValue) {
                with(it.get("test")) {
                    objectType == MergePatch.ObjectType.INTEGER
                    intValue == 5
                }
                with(it.get("test2")) {
                    objectType == MergePatch.ObjectType.STRING
                    strValue == "test"
                }
                with(it.get("test3")) {
                    objectType == MergePatch.ObjectType.OBJECT
                    with(objectValue.get("test4")) {
                        objectType == MergePatch.ObjectType.BOOLEAN
                        boolValue
                    }
                }
                with(it.get("test6")) {
                    it.objectType == MergePatch.ObjectType.LIST
                    with(it.listValue) {
                        size() == 4
                        with(first()) {
                            objectType == MergePatch.ObjectType.INTEGER
                            intValue == 5
                        }
                        with(last()) {
                            objectType == MergePatch.ObjectType.OBJECT
                            with(objectValue) {
                                with(get("test5")) {
                                    objectType == MergePatch.ObjectType.BOOLEAN
                                    boolValue
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    def "should properly merge two MergePatch objects"() {
        given:
        var map = [
                "test1": MergePatch.intValue(5),
                "test2": MergePatch.boolValue(false),
                "test3": MergePatch.strValue("Edit test"),
                "test4": MergePatch.objectValue(["test9": MergePatch.strValue("nested edited")]),
                "test5": MergePatch.listValue([MergePatch.intValue(4)])
        ]
        MergePatch patch = MergePatch.objectValue(map)
        var map2 = [
                "test1": MergePatch.intValue(4),
                "test2": MergePatch.boolValue(true),
                "test3": MergePatch.strValue("test"),
                "test4": MergePatch.objectValue([
                        "test8": MergePatch.doubleValue(7.1),
                        "test9": MergePatch.strValue("nested to edit")
                ]),
                "test5": MergePatch.listValue([
                        MergePatch.intValue(6),
                        MergePatch.objectValue(["test7": MergePatch.strValue("nested")])
                ]),
                "test6": MergePatch.strValue("unchanged")
        ]
        MergePatch target = MergePatch.objectValue(map2)

        when:
        def result = MergePatchUtils.merge(patch, target)

        then:
        with(result) {
            objectType == MergePatch.ObjectType.OBJECT
            with(objectValue) {
                with(get("test1")) {
                    objectType == MergePatch.ObjectType.INTEGER
                    intValue == 5
                }
                with(get("test2")) {
                    objectType == MergePatch.ObjectType.BOOLEAN
                    (!boolValue)
                }
                with(get("test3")) {
                    objectType == MergePatch.ObjectType.STRING
                    strValue == "Edit test"
                }
                with(get("test4")) {
                    objectType == MergePatch.ObjectType.OBJECT
                    with (objectValue) {
                        with(get("test8")) {
                            objectType == MergePatch.ObjectType.DOUBLE
                            doubleValue == 7.1d
                        }
                        with(get("test9")) {
                            objectType == MergePatch.ObjectType.STRING
                            strValue == "nested edited"
                        }
                    }
                }
                with(get("test5")) {
                    objectType == MergePatch.ObjectType.LIST
                    with(listValue) {
                        size() == 1
                        with(last()) {
                            objectType == MergePatch.ObjectType.INTEGER
                            intValue == 4
                        }
                    }
                }
                with(get("test6")) {
                    objectType == MergePatch.ObjectType.STRING
                    strValue == "unchanged"
                }
            }
        }
    }

}
