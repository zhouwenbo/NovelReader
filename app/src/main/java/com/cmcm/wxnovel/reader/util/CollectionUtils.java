package com.cmcm.wxnovel.reader.util;

import java.util.Collection;
import java.util.Map;

/**
 * Created by cm on 2016/12/28.
 */
public class CollectionUtils {

    public static boolean isNotEmpty(Collection collection) {
        return collection != null && collection.size() > 0;
    }

    public static boolean isEmpty(Collection collection) {
        return !isNotEmpty(collection);
    }

    public static boolean isNotEmpty(Map map) {
        return map != null && !map.isEmpty();
    }

    public static boolean isEmpty(Map map) {
       return !isNotEmpty(map);
    }


}
