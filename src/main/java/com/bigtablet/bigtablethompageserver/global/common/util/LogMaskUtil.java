package com.bigtablet.bigtablethompageserver.global.common.util;

public final class LogMaskUtil {

    private LogMaskUtil() {
    }

    /**
     * 이메일 로컬파트를 마스킹하여 로그 PII 노출을 줄인다 (예: a***@bigtablet.com)
     * @param email 원본 이메일
     * @return 마스킹된 이메일
     */
    public static String maskEmail(String email) {
        if (email == null) {
            return "null";
        }
        int at = email.indexOf('@');
        if (at <= 0) {
            return "***";
        }
        return email.charAt(0) + "***" + email.substring(at);
    }

}
