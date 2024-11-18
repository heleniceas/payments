package br.com.caju.payments.domain.balance

enum class BalanceType {
    FOOD,
    MEAL,
    CASH;

    companion object {
        fun fromMcc(mcc: String): String {
            return when (mcc) {
                "5411", "5412" -> FOOD.name
                "5811", "5812" -> MEAL.name
                else -> CASH.name
            }
        }
    }
}