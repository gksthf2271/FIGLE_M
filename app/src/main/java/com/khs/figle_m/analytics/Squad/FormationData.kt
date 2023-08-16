package com.khs.figle_m.analytics.Squad

import com.khs.figle_m.utils.FormationEnum

data class FormationData(var formationEnum: FormationEnum, var positionList: List<PlayerXY>)

data class PlayerXY(var biasX: Int, var biasY: Int)
