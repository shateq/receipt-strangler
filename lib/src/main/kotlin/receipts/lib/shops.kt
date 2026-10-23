package com.swieczorek.receipts.lib

enum class Shops {
    Kaufland,
    Lidl,
    Carrefour,
    Biedronka,
    Zabka,
    Unknown;

    // use it like Shops.entries.map
    fun included(string: String) = string.lowercase() == this.name
}