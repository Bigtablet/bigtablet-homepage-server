package com.bigtablet.bigtablethompageserver.domain.recruit.application.constant;

public final class RecruitMailSubject {

    private static final String PREFIX = "[Bigtablet, Inc. 채용]";

    private RecruitMailSubject() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * 채용 도메인 이메일에 공통으로 붙는 브랜드 prefix.
     * 본문 없이 prefix만 필요한 경우(예: 인재풀 등록 안내)에 사용한다.
     * @return String 브랜드 prefix
     */
    public static String brand() {
        return PREFIX;
    }

    /**
     * 지원 접수 완료 안내 이메일 제목
     * @param name String 지원자 이름
     * @return String 이메일 제목
     */
    public static String applyConfirmed(String name) {
        return personalized(name, "지원 접수 완료 안내드립니다");
    }

    /**
     * 면접 전형 안내 이메일 제목
     * @param name String 지원자 이름
     * @return String 이메일 제목
     */
    public static String interviewGuide(String name) {
        return personalized(name, "면접 전형 안내드립니다");
    }

    /**
     * 채용 전형 최종 결과(합격/불합격 공통) 안내 이메일 제목
     * @param name String 지원자 이름
     * @return String 이메일 제목
     */
    public static String finalResult(String name) {
        return personalized(name, "채용 전형 최종 결과 안내드립니다");
    }

    /**
     * 공통 포맷: `{PREFIX} {name}님, {message}` — 호칭/구분자 변경 시 이 메서드만 수정.
     * @param name String 수신자 이름
     * @param message String 안내 메시지 본문
     * @return String 조립된 이메일 제목
     */
    private static String personalized(String name, String message) {
        return PREFIX + " " + name + "님, " + message;
    }

}
