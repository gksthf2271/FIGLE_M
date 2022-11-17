package com.khs.figle_m.Analytics.Squad

import com.khs.figle_m.Utils.FormationEnum

data class FormationData(var formationEnum: FormationEnum, var positionList: List<PlayerXY>)

data class PlayerXY(var biasX: Int, var biasY: Int)
