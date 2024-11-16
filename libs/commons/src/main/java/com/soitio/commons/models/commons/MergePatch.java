package com.soitio.commons.models.commons;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class MergePatch {

    public static final MergePatch NULL;

    static {
        MergePatch empty = new MergePatch();
        empty.isNull = true;
        NULL = empty;
    }

    private String strValue;
    private int intValue;
    private double doubleValue;
    private List<MergePatch> listValue;
    private Map<String, MergePatch> objectValue;
    private boolean boolValue;
    private BigDecimal bigNumberValue;
    private ObjectType objectType;
    private boolean isNull;

    public static MergePatch strValue(String strValue) {
        MergePatch ret = new MergePatch();
        ret.strValue = strValue;
        ret.objectType = ObjectType.STRING;
        return ret;
    }

    public static MergePatch intValue(int intValue) {
        MergePatch ret = new MergePatch();
        ret.intValue = intValue;
        ret.objectType = ObjectType.INTEGER;
        return ret;
    }

    public static MergePatch doubleValue(double doubleValue) {
        MergePatch ret = new MergePatch();
        ret.doubleValue = doubleValue;
        ret.objectType = ObjectType.DOUBLE;
        return ret;
    }

    public static MergePatch listValue(List<MergePatch> listValue) {
        MergePatch ret = new MergePatch();
        ret.listValue = listValue;
        ret.objectType = ObjectType.LIST;
        return ret;
    }

    public static MergePatch objectValue(Map<String, MergePatch> objectValue) {
        MergePatch ret = new MergePatch();
        ret.objectValue = objectValue;
        ret.objectType = ObjectType.OBJECT;
        return ret;
    }

    public static MergePatch boolValue(boolean boolValue) {
        MergePatch ret = new MergePatch();
        ret.boolValue = boolValue;
        ret.objectType = ObjectType.BOOLEAN;
        return ret;
    }

    public static MergePatch bigNumberValue(BigDecimal bigNumberValue) {
        MergePatch ret = new MergePatch();
        ret.bigNumberValue = bigNumberValue;
        ret.objectType = ObjectType.BIG_NUMBER;
        return ret;
    }

    public String getStrValue() {
        return strValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public List<MergePatch> getListValue() {
        return listValue;
    }

    public Map<String, MergePatch> getObjectValue() {
        return objectValue;
    }

    public boolean getBoolValue() {
        return boolValue;
    }

    public BigDecimal getBigNumberValue() {
        return bigNumberValue;
    }

    public ObjectType getObjectType() {
        return objectType;
    }

    public boolean getIsNull() {
        return isNull;
    }

    public void setStrValue(String strValue) {
        this.strValue = strValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public void setListValue(List<MergePatch> listValue) {
        this.listValue = listValue;
    }

    public void setObjectValue(Map<String, MergePatch> objectValue) {
        this.objectValue = objectValue;
    }

    public void setBoolValue(boolean boolValue) {
        this.boolValue = boolValue;
    }

    public void setBigNumberValue(BigDecimal bigNumberValue) {
        this.bigNumberValue = bigNumberValue;
    }

    public void setObjectType(ObjectType objectType) {
        this.objectType = objectType;
    }

    public void setNull(boolean aNull) {
        isNull = aNull;
    }

    public enum ObjectType {
        STRING,
        INTEGER,
        DOUBLE,
        LIST,
        OBJECT,
        BOOLEAN,
        BIG_NUMBER
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
