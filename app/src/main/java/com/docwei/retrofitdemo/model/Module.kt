package com.docwei.retrofitdemo.model

data class Module(
    val cardModule: List<CardModule>,
    val configId: String,
    val irregularCardModule: List<IrregularCardModule>,
    val moduleType: Int,
    val more: More,
    val navigationTagModule: List<NavigationTagModule>,
    val normalKingKongModule: List<NormalKingKongModule>,
    val sort: Int,
    val title: String
)