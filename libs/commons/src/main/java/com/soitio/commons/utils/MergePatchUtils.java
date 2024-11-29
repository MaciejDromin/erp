package com.soitio.commons.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.soitio.commons.dependency.model.DependencyCheckContext;
import com.soitio.commons.dependency.model.DependencyCheckDiff;
import com.soitio.commons.models.commons.MergePatch;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class MergePatchUtils {

    private MergePatchUtils() {
        throw new AssertionError("Utility class");
    }

    public static MergePatch fromJsonNode(JsonNode node) {
        if (node.isNull()) return MergePatch.NULL;
        if (node.isTextual()) return MergePatch.strValue(node.textValue());
        if (node.isInt()) return MergePatch.intValue(node.intValue());
        if (node.isLong()) return MergePatch.intValue(node.intValue()); // TODO: For now
        if (node.isDouble()) return MergePatch.doubleValue(node.doubleValue());
        if (node.isBoolean()) return MergePatch.boolValue(node.booleanValue());
        if (node.isBigDecimal()) return MergePatch.bigNumberValue(node.decimalValue());
        if (node.isObject()) {
            Map<String, MergePatch> tmp = new HashMap<>();
            for (Iterator<Map.Entry<String, JsonNode>> it = node.fields(); it.hasNext(); ) {
                Map.Entry<String, JsonNode> e = it.next();
                tmp.put(e.getKey(), fromJsonNode(e.getValue()));
            }
            return MergePatch.objectValue(tmp);
        }
        if (node.isArray()) {
            List<MergePatch> tmp = new ArrayList<>();
            for (JsonNode e : node) {
                tmp.add(fromJsonNode(e));
            }
            return MergePatch.listValue(tmp);
        }

        throw new IllegalStateException();
    }

    public static MergePatch merge(MergePatch patch, MergePatch target) {
        if (patch.getIsNull()) return target;

        patch.getObjectValue().forEach((k, v) -> {
            MergePatch to = target.getObjectValue().get(k);
            if (to == null) return;
            if (to.getObjectType() != v.getObjectType() && !(to.getIsNull() || v.getIsNull())) throw new IllegalStateException("Object Types do not match");
            handleBasic(v, to);
            if (v.getIsNull()) {
                to.setNull(true);
                to.setObjectType(null);
            }
            if (v.getObjectType() == MergePatch.ObjectType.OBJECT) merge(v, to);
        });

        return target;
    }

    public static DependencyCheckContext buildContext(MergePatch patch, MergePatch target) {
        return new DependencyCheckContext(target.getObjectValue().get("id").getStrValue(), diff(patch, target));
    }

    public static Set<DependencyCheckDiff> diff(MergePatch patch, MergePatch target) {
        if (patch.getIsNull()) return Set.of();

        return streamAndRunDiff(patch, target, "");
    }

    private static Set<DependencyCheckDiff> runDiff(String key, MergePatch value, MergePatch target, String parentKey) {
        MergePatch to = target.getObjectValue().get(key);
        if (to == null) return Set.of();
        // if basic just return the diff value or empty if there is no change
        // TODO: make diff for basic types

        // if value is null but not target
        // TODO: handle above scenario

        if (value.getObjectType() == MergePatch.ObjectType.OBJECT) return streamAndRunDiff(value, target, parentKey + key);

        // default just in case
        return Set.of();
    }

    private static Set<DependencyCheckDiff> streamAndRunDiff(MergePatch patch, MergePatch target, String parentKey) {
        return patch.getObjectValue().entrySet().stream()
                .map(e -> runDiff(e.getKey(), e.getValue(), target, parentKey))
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    private static void handleBasic(MergePatch v, MergePatch to) {
        switch (v.getObjectType()) {
            case LIST -> to.setListValue(v.getListValue()); // Might need extra handling
            case STRING -> to.setStrValue(v.getStrValue());
            case INTEGER -> to.setIntValue(v.getIntValue());
            case DOUBLE -> to.setDoubleValue(v.getDoubleValue());
            case BOOLEAN -> to.setBoolValue(v.getBoolValue());
            case BIG_NUMBER -> to.setBigNumberValue(v.getBigNumberValue());
        }
    }

}
