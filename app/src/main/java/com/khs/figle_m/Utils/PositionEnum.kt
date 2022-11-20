package com.khs.figle_m.Utils


enum class PositionEnum(val spposition:Int, val description:String) {
    GK(0,"GK"),

    SW(1,"SW"),
    RWB(2,"RWB"),
    RB(3,"RB"),
    RCB(4,"RCB"),
    CB(5,"CB"),
    LCB(6,"LCB"),
    LB(7,"LB"),
    LWB(8,"LWB"),



    RDM(9,"RDM"),
    CDM(10,"CDM"),
    LDM(11,"LDM"),

    RM(12,"RM"),
    RCM(13,"RCM"),
    CM(14,"CM"),
    LCM(15,"LCM"),
    LM(16,"LM"),

    RAM(17,"RAM"),
    CAM(18,"CAM"),
    LAM(19,"LAM"),



    RF(20,"RF"),
    CF(21,"CF"),
    LF(22,"LF"),
    RW(23,"RW"),
    RS(24,"RS"),
    ST(25,"ST"),
    LS(26,"LS"),
    LW(27,"LW"),

    SUB(28,"SUB")
}
/*

---------------------
스쿼드 : 3-4-3
스쿼드 점수 : 25,22,20,16,15,13,12,6,5,4
ST
LF    RF
LM LCM RCM RM
LCB CB RCB
GK

---------------------
스쿼드 : 3-4-3(2)
스쿼드 점수 : 27,25,23,16,15,13,12,6,5,4
ST
LW      RW
LM LCM RCM RM
LCB CB RCB
GK

---------------------
스쿼드 : 3-4-1-2
스쿼드 점수 : 26,24,18,16,15,13,12,6,5,4
LS   RS
CAM
LM LCM RCM RM
LCB CB RCB
GK

---------------------
스쿼드 : 3-2-3-2
스쿼드 점수 : 25,21,16,14,12,11,9,6,5,4
ST
CF
LM CM RM
LDM RDM
LCB CB RCB
GK

---------------------
스쿼드 : 3-2-2-1-2
스쿼드 점수 : 26,24,18,16,12,11,9,6,5,4
LS RS
CAM
LM         RM
LDM RDM
LCB CB RCB
GK

---------------------
스쿼드 : 3-1-2-1-3
스쿼드 점수 : 27,25,23,18,16,12,10,6,5,4
LW, ST  RW
CAM
LM      RM
CDM
LCB  CB  RCB
GK

---------------------
스쿼드 : 3-1-4-2
스쿼드 점수 : 26,24,16,15,13,12,10,6,5,4
LS  RS
LM LCM RCM RM
CDM
LCB CB RCB
GK

---------------------
스쿼드 : 4-5-1
스쿼드 점수 : 25,16,15,14,13,12,7,6,4,3
ST
LM LCM CM RCM RM
LB LCB RCB RB
GK

---------------------
스쿼드 : 4-4-2
스쿼드 점수 : 25,21,16,15,13,12,7,6,4,3
ST
CF
LM LCM  RCM RM
LB LCB  RCB RB
GK

---------------------
스쿼드 : 4-4-2(2)
스쿼드 점수 : 26,24,16,15,13,12,7,6,4,3
LS   RS
LM LCM  RCM RM
LB LCB  RCB RB
GK

---------------------
스쿼드 : 4-4-1-1
스쿼드 점수 : 25,18,16,15,13,12,7,6,4,3
ST
CAM
LM LCM  RCM RM
LB LCB  RCB RB
GK

---------------------
스쿼드 : 4-3-3
스쿼드 점수 : 27,25,23,15,14,13,7,6,4,3
LW   ST   RW
LCM CM RCM
LB LCB RCB RB
GK


---------------------
스쿼드 : 4-3-3(2)
스쿼드 점수 : 25,22,20,15,14,13,7,6,4,3
ST
LF    RF
LCM CM RCM
LB LCB RCB RB
GK

---------------------
스쿼드 : 4-3-2-1
스쿼드 점수 : 25,19,17,16,14,12,7,6,4,3
ST
LAM  RAM
LM  CM  RM
LB LCB RCB RB
GK

---------------------
스쿼드 : 4-3-1-2
스쿼드 점수 : 26,24,18,15,14,13,7,6,4,3
LS  RS
CAM
LCM CM RCM
LB LCB RCB RB
GK

---------------------
스쿼드 : 4-2-4
스쿼드 점수 : 27,26,24,23,15,13,7,6,4,3
LS   RS
LW       RW
LCM  RCM
LB LCB RCB RB
GK

---------------------
스쿼드 : 4-2-3-1
스쿼드 점수 : 25,19,18,17,11,9,7,6,4,3
ST
LAM CAM RAM
LDM  RDM
LB LCB RCB RB
GK

---------------------
스쿼드 : 4-2-2-2
스쿼드 점수 : 26,24,16,12,11,9,7,6,4,3
LS  RS
LM        RM
LDM  RDM
LB LCB  RCB RB
GK

---------------------
스쿼드 : 4-2-2-2(2)
스쿼드 점수 : 26,24,19,17,11,9,7,6,4,3
LS  RS
LAM       RAM
LDM  RDM
LB LCB  RCB RB
GK

---------------------
스쿼드 : 4-2-2-1-1
스쿼드 점수 : 25,18,16,12,11,9,7,6,4,3
ST
CAM
LM        RM
LDM  RDM
LB LCB  RCB RB
GK

---------------------
스쿼드 : 4-2-1-3
스쿼드 점수 : 27,25,23,18,15,13,7,6,4,3
LW   ST   RW
CAM
LCM     RCM
LB LCB  RCB RB
GK

---------------------
스쿼드 : 4-2-1-3(2)
스쿼드 점수 : 27,25,23,15,14,13,7,6,4,3
LW   ST   RW
CM
LCM     RCM
LB LCB  RCB RB
GK

---------------------
스쿼드 : 4-1-4-1
스쿼드 점수 : 25,16,15,13,12,10,7,6,4,3
ST
LM LCM  RCM RM
CDM
LB LCB  RCB RB
GK

---------------------
스쿼드 : 4-1-3-2
스쿼드 점수 : 26,24,16,14,12,10,7,6,4,3
LS  RS
LM    CM    RM
CDM
LB LCB  RCB RB
GK


---------------------
스쿼드 : 4-1-2-3
스쿼드 점수 : 27,25,23,15,13,10,7,6,4,3
LW    ST    RW
LCM   RCM
CDM
LB LCB  RCB RB
GK

---------------------
스쿼드 : 4-1-2-3
스쿼드 점수 : 27,23,21,15,13,10,7,6,4,3
LW    CF    RW
LCM   RCM
CDM
LB LCB  RCB RB
GK

---------------------
스쿼드 : 4-1-2-1-2
스쿼드 점수 : 26,24,18,16,12,10,7,6,4,3
LS    RS
CAM
LM          RM
CDM
LB LCB  RCB RB
GK


---------------------
스쿼드 : 4-1-2-1-2(2)
스쿼드 점수 : 26,24,18,15,13,10,7,6,4,3
LS    RS
CAM
LCM    RCM
CDM
LB LCB  RCB RB
GK

---------------------
스쿼드 : 5-4-1
스쿼드 점수 : 25,16,15,13,12,8,6,5,4,2
ST
LM LCM    RCM RM
LWB LCB CB RCB RWB
GK

---------------------
스쿼드 : 5-3-2
스쿼드 점수 : 26,24,15,14,13,8,6,5,4,2
LS  RS
LCM CM RCM
LWB LCB CB RCB RWB
GK

---------------------
스쿼드 : 5-2-3
스쿼드 점수 : 27,25,23,15,13,8,6,5,4,2
LW     ST     RW
LCM    RCM
LWB LCB CB RCB RWB
GK

---------------------
스쿼드 : 5-2-1-2
스쿼드 점수 : 26,24,18,15,13,8,6,5,4,2
LS   RS
CAM
LCM  RCM
LWB LCB CB RCB RWB
GK

---------------------
스쿼드 : 5-1-2-1-1
스쿼드 점수 : 25,18,16,12,10,8,6,5,4,2
ST
LM     CAM      RM
CDM
LWB LCB CB RCB RWB
GK







*/
