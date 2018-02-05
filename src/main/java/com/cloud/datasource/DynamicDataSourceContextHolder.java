package com.cloud.datasource;


import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author weisen.yang@hand-china.com
 * @Date 2018/2/1 9:16
 * @description
 */
public class DynamicDataSourceContextHolder {
    private static final ThreadLocal<Stack<String>> CONTEXT_HOLDER = new ThreadLocal<>();

    public static final List<String> DATA_SOURCE_IDS = new ArrayList<>();

    public static void setDataSourceType(String dataSourceType) {
        if (CONTEXT_HOLDER.get() == null) {
            CONTEXT_HOLDER.set(new Stack<>());
        }
        CONTEXT_HOLDER.get().push(dataSourceType);
    }

    public static String getDataSourceType() {
        if (CONTEXT_HOLDER.get() ==null|| CONTEXT_HOLDER.get().empty()){ return null;}
        return CONTEXT_HOLDER.get().peek();
    }

    public static void clearDataSourceType() {
        if (CONTEXT_HOLDER.get() == null) {
            CONTEXT_HOLDER.set(new Stack<>());
        } else if (!CONTEXT_HOLDER.get().empty()){
            CONTEXT_HOLDER.get().pop();
        }
    }


    public static boolean containsDataSource(String dataSourceId) {
        return DATA_SOURCE_IDS.contains(dataSourceId);
    }
}
