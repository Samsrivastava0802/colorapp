package com.samridhi.colorapp.data.network.model

data class ColorInfo(
    val data: AllInfo
)

data class AllInfo(
    val colorName: List<ColorName>? = null,
    val dateInfo: DateInfo? = null
)

data class ColorName(
    val colorCode: String
)

data class DateInfo(
    val createdDate: String
)


data class Fan(
    val colorCode: String,
    val date: String
)


fun getDemoData(): List<Fan> {
    return listOf(
        Fan(
            colorCode = "#FFAABB",
            date = "12/05/2023"
        ),
        Fan(
            colorCode = "#D7415F",
            date = "06/05/2023"
        ),
        Fan(
            colorCode = "#FFAABB",
            date = "12/05/2023"
        ),
        Fan(
            colorCode = "#FFAABB",
            date = "12/05/2023"
        ),
        Fan(
            colorCode = "#FFAABB",
            date = "12/05/2023"
        ),
        Fan(
            colorCode = "#FFAABB",
            date = "12/05/2023"
        ),
    )
}
