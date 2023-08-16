package com.khs.domain.util

object Constants {
    const val DEFAULT_OFFSET: Int = 0
    const val SEARCH_LIMIT: Int = 100
    const val SEARCH_PAGE_SIZE: Int = 20
//    {
//        "matchtype": 30,
//        "desc": "리그 친선"
//    },
//    {
//        "matchtype": 40,
//        "desc": "클래식 1on1"
//    },
//    {
//        "matchtype": 50,
//        "desc": "공식경기"
//    },
//    {
//        "matchtype": 52,
//        "desc": "감독모드"
//    },
//    {
//        "matchtype": 60,
//        "desc": "공식 친선"
//    },
//    {
//        "matchtype": 204,
//        "desc": "볼타 친선"
//    },
//    {
//        "matchtype": 214,
//        "desc": "볼타 공식"
//    },
//    {
//        "matchtype": 224,
//        "desc": "볼타 AI대전"
//    },
//    {
//        "matchtype": 234,
//        "desc": "볼타 커스텀"
//    }
//    ]

    enum class MatchType(val matchType: Int) {
        TYPE_NORMAL(50),
        TYPE_COACH(52)
    }

    //    200	OK	성공
    //    301	Moved Permanently	HTTP 프로토콜로 호출
    //    400	Bad Request	요청 형식 오류 (잘못된 파라미터 입력)
    //    401	Unauthorized	미승인 서비스 (미지원 service, service type)
    //    403	Forbidden	허용되지 않은 AccessToken 사용
    //    404	Not Found	해당 리소스가 존재하지 않음
    //    405	Method not allowed	미지원 API
    //    413	Request Entity Too Large	너무 긴 요청 파라미터 입력
    //    429	Too many request	AccessToken의 요청 허용량(Rate Limit) 초과
    //    500	Internal Server Error	서버 내부 에러
    //    504	Gateway Timeout	서버 내부 처리 timeout

    const val SUCCESS_CODE: Int = 200
    const val ERROR_BAD_REQUEST = 400
    const val ERROR_UNAUTHORIZED = 401
    const val ERROR_FORBIDDEN = 403
    const val ERROR_NOT_FOUND= 404
    const val ERROR_METHOD_NOT_ALLOWED = 405
    const val ERROR_REQUEST_ENTITY_TOO_LARGE = 413
    const val ERROR_TOO_MANY_REQUEST = 429
    const val ERROR_INTERNAL_SERVER_ERROR = 500
    const val ERROR_GATEWAY_TIMEOUT = 504
    const val ERROR_NETWORK_DISCONNECTED = 990
    const val ERROR_OTHERS = 999
}