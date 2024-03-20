package org.rg.common;

import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 格式化返回状态信息缓存
 *
 * @author Qnxy
 */
final class StatusMessageFormatCache {

    private static final Map<String, MessageFormat> STATUS_MESSAGE_FORMAT_CACHE_MAP = new ConcurrentHashMap<>();


    public static MessageFormat obtainMessageFormat(String pattern) {
        return STATUS_MESSAGE_FORMAT_CACHE_MAP.computeIfAbsent(pattern, MessageFormat::new);
    }
}
