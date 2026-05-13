package com.bigtablet.bigtablethompageserver.global.infra.email.subject;

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
        return PREFIX + " " + name + "님, 지원 접수 완료 안내드립니다";
    }

    /**
     * 면접 전형 안내 이메일 제목
     * @param name String 지원자 이름
     * @return String 이메일 제목
     */
    public static String interviewGuide(String name) {
        return PREFIX + " " + name + "님, 면접 전형 안내드립니다";
    }

    /**
     * 채용 전형 최종 결과(합격/불합격 공통) 안내 이메일 제목
     * @param name String 지원자 이름
     * @return String 이메일 제목
     */
    public static String finalResult(String name) {
        return PREFIX + " " + name + "님, 채용 전형 최종 결과 안내드립니다";
    }

}
