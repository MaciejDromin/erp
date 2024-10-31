package com.soitio.commons.models.commons;

public class MergePatchUtils {

    private MergePatchUtils() {
        throw new AssertionError("Utility class");
    }

    public static MergePatch fromString(String value) {
        return null;
    }

    public static MergePatch merge(MergePatch patch, MergePatch target) {
        if (patch.getIsNull()) return target;

        patch.getObjectValue().forEach((k, v) -> {
            MergePatch to = target.getObjectValue().get(k);
            if (to.getObjectType() != v.getObjectType()) throw new IllegalStateException("Object Types do not match");
            handleBasic(v, to);
            if (v.getIsNull()) {
                to.setNull(true);
                to.setObjectType(null);
            }
            if (v.getObjectType() == MergePatch.ObjectType.OBJECT) merge(v, to);
        });

        return target;
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
