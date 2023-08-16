package com.khs.figle_m.utils

enum class SeasonEnum(val seasonId:Int, val className: String) {
    ICON(101,"icon"),

    NHD(201, "nhd"),
    TKI(202, "tki"),
    TB(206, "tb"),
    TT(207,"tt"),
    GR(210,"gr"),
    TOTY19(211,"19toty"),
    TOTY18(212,"18toty"),
    ManCityICON(213,"mcicon"),
    TC(214,"tc"),
    TOTS19(215,"19tots"),
    HOT(216,"hot"),
    COC(217,"coc"),
    OTW(218,"otw"),
    NG(219,"19ng"),
    TOTY20(220,"20toty"),
    UCL19(221,"19ucl"),
    TOTN20(222,"20totn"),
    LH1(224,"lh"),          //임시 방편 224, 234 뭐가 맞는지 문의필요 // 문의 결과 234가 맞고, 224는 급여이슈로 임시 할당번호
    TKL(225,"tkl"),
    TOTS20(230,"20tots"),
    VTR(231,"vtr"),
    MOG(233,"mog"),
    LH(234,"lh"),
    LA(236,"la"),
    MC(237,"mc"),
    NG20(238,"20ng"),
    KLB20(239,"20klb"),

    //22년 11월 추가
    TOTY21(240,"21toty"),
    TOTN21(241,"21totn"),
    UCL20(242,"20ucl"),
    UP(246,"up"),
    KH2012(247,"2012kh"),
    NGT(249,"ngt"),
    TOTS21(250,"21tots"),
    EBS(251,"ebs"),
    CAP(252,"cap"),
    BOE21(253,"boe21"),
    CFA(254,"cfa"),
    BTB(256,"btb"),
    KLeagueB21(257,"21klb"),
    TOTY22(258,"22toty"),
    TOTN22(259,"22totn"),
    UCL21(260,"21ucl"),
    HR22(261,"22hr"),
    NG21(262,"21ng"),
    FA(264,"fa"),
    LOL(265,"lol"),
    TOTS22(267,"22tots"),
    LN(268,"ln"),
    SPL(270,"spl"),
    RMCF(274,"rmcf"),
    KFA21(294,"21kfa"),

    KFA19(295,"2019kfa"),
    MCFC(297,"mcfc"),
    KFA18(298,"kfa"),

    LIVE21(300,"live"),
    LIVE17(317,"17"),
    LIVE18(318,"18"),
    LIVE19(319,"19"),
    LIVE20(320,"20"),

    PLC_A18(500,"plc"),
    PLC_S18(501,"18pls"),
    PLC_A19(502,"19pla"),
    PLC_S19(503,"19pls"),
    KLeague20(504,"20kl"),
    PLC_A20(506,"20pla"),
    KLeague21(507,"21kl"),
    PLC_A21(508,"21pla"),
    KLeague22(510,"22kl"),
}



//1,101,ICON(ICON),http://s.nx.com/s2/game/fo4/obt/externalAssets/season/icon.png
//2,201,NHD(NationalHeroDebut),http://s.nx.com/s2/game/fo4/obt/externalAssets/season/nhd.png
//3,202,TKI(TeamKoreaIcon),http://s.nx.com/s2/game/fo4/obt/externalAssets/season/tki.png
//4,206,TB(TournamentBest),http://s.nx.com/s2/game/fo4/obt/externalAssets/season/tb.png
//5,207,TT(TopTransfer),http://s.nx.com/s2/game/fo4/obt/externalAssets/season/tt.png
//6,210,GR(GoldenRookies),http://s.nx.com/s2/game/fo4/obt/externalAssets/season/gr.png
//7,211,19TOTY(19TeamOfTheYear),http://s.nx.com/s2/game/fo4/obt/externalAssets/season/19toty.png
//8,212,18TOTY(18TeamOfTheYear),http://s.nx.com/s2/game/fo4/obt/externalAssets/season/18toty.png
//9,213,ManCityICON(ManCityICON),http://s.nx.com/s2/game/fo4/obt/externalAssets/season/mcicon.png
//10,214,TC(TournamentChampions),http://s.nx.com/s2/game/fo4/obt/externalAssets/season/tc.png
//11,215,19TOTS(19TeamOfTheSeason),http://s.nx.com/s2/game/fo4/obt/externalAssets/season/19tots.png
//12,216,HOT(HeroesOftheTeam),http://s.nx.com/s2/game/fo4/obt/externalAssets/season/hot.png
//13,217,COC(CompetitorOfContinents),http://s.nx.com/s2/game/fo4/obt/externalAssets/season/coc.png
//14,218,OTW(OnesToWatch),http://s.nx.com/s2/game/fo4/obt/externalAssets/season/otw.png
//15,219,NG(NEWGENERATION),http://s.nx.com/s2/game/fo4/obt/externalAssets/season/ng.png
//16,220,20TOTY(20TeamOfTheYear),http://s.nx.com/s2/game/fo4/obt/externalAssets/season/20toty.png
//17,221,19UCL(19UEFAChampionsLeague),http://s.nx.com/s2/game/fo4/obt/externalAssets/season/19ucl.png
//18,222,20TOTN(20TeamOfTheNominated),http://s.nx.com/s2/game/fo4/obt/externalAssets/season/20totn.png
//19,224,LH(LH),http://s.nx.com/s2/game/fo4/obt/externalAssets/season/lh.png
//20,225,TKL(TKL),http://s.nx.com/s2/game/fo4/obt/externalAssets/season/tkl.png
//{
//    "seasonId": 234,
//    "className": "LH (LH)",
//    "seasonImg": "http://s.nx.com/s2/game/fo4/obt/externalAssets/season/lh.png"
//},
////////////////////
//추가 20.10.05
//{
//    "seasonId": 230,
//    "className": "20 TOTS (20 Team Of The Season)",
//    "seasonImg": "http://s.nx.com/s2/game/fo4/obt/externalAssets/season/20tots.png"
//},
//{
//    "seasonId": 231,
//    "className": "VTR (Veteran)",
//    "seasonImg": "http://s.nx.com/s2/game/fo4/obt/externalAssets/season/vtr.png"
//},
//{
//    "seasonId": 233,
//    "className": "MOG (Moments Of Glory)",
//    "seasonImg": "http://s.nx.com/s2/game/fo4/obt/externalAssets/season/mog.png"
//},
//
//{
//    "seasonId": 236,
//    "className": "LA (Liverpool Ambassador)",
//    "seasonImg": "http://s.nx.com/s2/game/fo4/obt/externalAssets/season/la.png"
//}
////////////////////
//12월 18일 추가

//{
//    "seasonId": 237,
//    "className": "MC (Multi-League Champions)",
//    "seasonImg": "http://s.nx.com/s2/game/fo4/obt/externalAssets/season/mc.png"
//},
//{
//    "seasonId": 238,
//    "className": "20NG (20 NEW GENERATION)",
//    "seasonImg": "http://s.nx.com/s2/game/fo4/obt/externalAssets/season/20ng.png"
//}
//{
//    "seasonId": 239,
//    "className": "20KLB (20 K-League Best)",
//    "seasonImg": "http://s.nx.com/s2/game/fo4/obt/externalAssets/season/20klb.png"
//},
//{
//    "seasonId": 319,
//    "className": "19 LIVE (19 LIVE)",
//    "seasonImg": "http://s.nx.com/s2/game/fo4/obt/externalAssets/season/19.png"
//},
//{
//    "seasonId": 506,
//    "className": "20 PLC–A (20 Premium Live Autumn)",
//    "seasonImg": "http://s.nx.com/s2/game/fo4/obt/externalAssets/season/20pla.png"
//}


////////////////////
//22년 11월 06일 확인
//{
//    "seasonId": 240,
//    "className": "21 TOTY (21 Team Of The Year)",
//    "seasonImg": "https://ssl.nexon.com/s2/game/fo4/obt/externalAssets/season/21toty.png"
//},
//{
//    "seasonId": 241,
//    "className": "21 TOTN (21 Team Of The Nominated)",
//    "seasonImg": "https://ssl.nexon.com/s2/game/fo4/obt/externalAssets/season/21totn.png"
//},
//{
//    "seasonId": 242,
//    "className": "20 UCL (20 UEFA Champions League)",
//    "seasonImg": "https://ssl.nexon.com/s2/game/fo4/obt/externalAssets/season/20ucl.png"
//},
//{
//    "seasonId": 246,
//    "className": "UP (Unsung Players)",
//    "seasonImg": "https://ssl.nexon.com/s2/game/fo4/obt/externalAssets/season/up.png"
//},
//{
//    "seasonId": 247,
//    "className": "2012 KH (2012 KH)",
//    "seasonImg": "https://ssl.nexon.com/s2/game/fo4/obt/externalAssets/season/2012kh.png"
//},
//{
//    "seasonId": 249,
//    "className": "NTG (Nostalgia)",
//    "seasonImg": "https://ssl.nexon.com/s2/game/fo4/obt/externalAssets/season/ngt.png"
//},
//{
//    "seasonId": 250,
//    "className": "21 TOTS (21 Team Of The Season)",
//    "seasonImg": "https://ssl.nexon.com/s2/game/fo4/obt/externalAssets/season/21tots.png"
//},
//{
//    "seasonId": 251,
//    "className": "EBS (European Best Stars)",
//    "seasonImg": "https://ssl.nexon.com/s2/game/fo4/obt/externalAssets/season/ebs.png"
//},
//{
//    "seasonId": 252,
//    "className": "CAP (Captain)",
//    "seasonImg": "https://ssl.nexon.com/s2/game/fo4/obt/externalAssets/season/cap.png"
//},
//{
//    "seasonId": 253,
//    "className": "BOE21 (Best of Europe 21)",
//    "seasonImg": "https://ssl.nexon.com/s2/game/fo4/obt/externalAssets/season/boe21.png"
//},
//{
//    "seasonId": 254,
//    "className": "CFA (Chelsea FC Ambassador)",
//    "seasonImg": "https://ssl.nexon.com/s2/game/fo4/obt/externalAssets/season/cfa.png"
//},
//{
//    "seasonId": 256,
//    "className": "BTB (Back to Back)",
//    "seasonImg": "https://ssl.nexon.com/s2/game/fo4/obt/externalAssets/season/btb.png"
//},
//{
//    "seasonId": 257,
//    "className": "21KLB (21 K-League Best)",
//    "seasonImg": "https://ssl.nexon.com/s2/game/fo4/obt/externalAssets/season/21klb.png"
//},
//{
//    "seasonId": 258,
//    "className": "22 TOTY (22 Team Of The Year)",
//    "seasonImg": "https://ssl.nexon.com/s2/game/fo4/obt/externalAssets/season/22toty.png"
//},
//{
//    "seasonId": 259,
//    "className": "22 TOTN (22 Team Of The Nominated)",
//    "seasonImg": "https://ssl.nexon.com/s2/game/fo4/obt/externalAssets/season/22totn.png"
//},
//{
//    "seasonId": 260,
//    "className": "21 UCL (21 UEFA Champions League)",
//    "seasonImg": "https://ssl.nexon.com/s2/game/fo4/obt/externalAssets/season/21ucl.png"
//},
//{
//    "seasonId": 261,
//    "className": "22HR (22 Heroes)",
//    "seasonImg": "https://ssl.nexon.com/s2/game/fo4/obt/externalAssets/season/22hr.png"
//},
//{
//    "seasonId": 262,
//    "className": "21NG (21 NEW GENERATION)",
//    "seasonImg": "https://ssl.nexon.com/s2/game/fo4/obt/externalAssets/season/21ng.png"
//},
//{
//    "seasonId": 264,
//    "className": "FA (FREE AGENT)",
//    "seasonImg": "https://ssl.nexon.com/s2/game/fo4/obt/externalAssets/season/fa.png"
//},
//{
//    "seasonId": 265,
//    "className": "LOL (LEGEND OF THE LOAN)",
//    "seasonImg": "https://ssl.nexon.com/s2/game/fo4/obt/externalAssets/season/lol.png"
//},
//{
//    "seasonId": 267,
//    "className": "22 TOTS (22 Team Of The Season)",
//    "seasonImg": "https://ssl.nexon.com/s2/game/fo4/obt/externalAssets/season/22tots.png"
//},
//{
//    "seasonId": 268,
//    "className": "LN (LEGENDARY NUMBERS)",
//    "seasonImg": "https://ssl.nexon.com/s2/game/fo4/obt/externalAssets/season/ln.png"
//},
//{
//    "seasonId": 270,
//    "className": "SPL (Spotlight)",
//    "seasonImg": "https://ssl.nexon.com/s2/game/fo4/obt/externalAssets/season/spl.png"
//},
//{
//    "seasonId": 274,
//    "className": "RMCF (Real Madrid Ambassador)",
//    "seasonImg": "https://ssl.nexon.com/s2/game/fo4/obt/externalAssets/season/rmcf.png"
//},
//{
//    "seasonId": 294,
//    "className": "21 KFA (21 KFA)",
//    "seasonImg": "https://ssl.nexon.com/s2/game/fo4/obt/externalAssets/season/21kfa.png"
//},
//{
//    "seasonId": 507,
//    "className": "21KLeague (21KLeague)",
//    "seasonImg": "https://ssl.nexon.com/s2/game/fo4/obt/externalAssets/season/21kl.png"
//},
//{
//    "seasonId": 508,
//    "className": "21 PL (21 Premium Live)",
//    "seasonImg": "https://ssl.nexon.com/s2/game/fo4/obt/externalAssets/season/21pla.png"
//},
//{
//    "seasonId": 510,
//    "className": "22KLeague (22KLeague)",
//    "seasonImg": "https://ssl.nexon.com/s2/game/fo4/obt/externalAssets/season/22kl.png"
//}

////////////////////
//21,295,19KFA(19KFA),http://s.nx.com/s2/game/fo4/obt/externalAssets/season/2019kfa.png
//22,297,MCFC(17-18ManCityChampions),http://s.nx.com/s2/game/fo4/obt/externalAssets/season/mcfc.png
//23,298,18KFA(18KFA),http://s.nx.com/s2/game/fo4/obt/externalAssets/season/kfa.png
//24,300,19LIVE(19LIVE),http://s.nx.com/s2/game/fo4/obt/externalAssets/season/live.png
//25,317,17LIVE(17LIVE),http://s.nx.com/s2/game/fo4/obt/externalAssets/season/17.png
//26,318,18LIVE(18LIVE),http://s.nx.com/s2/game/fo4/obt/externalAssets/season/18.png
//27,500,18PLC–A(18PremiumLiveAutumn),http://s.nx.com/s2/game/fo4/obt/externalAssets/season/plc.png
//28,501,18PLC–S(18PremiumLiveSpring),http://s.nx.com/s2/game/fo4/obt/externalAssets/season/18pls.png
//29,502,19PLC–A(19PremiumLiveAutumn),http://s.nx.com/s2/game/fo4/obt/externalAssets/season/19pla.png
//30,503,19PLC–S(19PremiumLiveSpring),http://s.nx.com/s2/game/fo4/obt/externalAssets/season/19pls.png
//31,504,20KLeague(20KLeague),http://s.nx.com/s2/game/fo4/obt/externalAssets/season/20kl.png