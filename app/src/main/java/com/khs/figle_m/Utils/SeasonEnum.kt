package com.khs.figle_m.Utils

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
    KFA19(295,"2019kfa"),
    MCFC(297,"mcfc"),
    KFA18(298,"kfa"),
    LIVE20(300,"live"),
    LIVE17(317,"17"),
    LIVE18(318,"18"),
    LIVE19(319,"19"),
    PLC_A18(500,"plc"),
    PLC_S18(501,"18pls"),
    PLC_A19(502,"19pla"),
    PLC_S19(503,"19pls"),
    KLeague20(504,"20kl"),
    PLC_A20(506,"20pla")
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