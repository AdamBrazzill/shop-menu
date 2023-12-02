package models

data class Transactions(
    var transactionId: Int = 0,
    var numberBought: Int,
    var customerName: String,
    var date: String,
    var salesPerson: String,
    var isItemComplete: Boolean = false
) {
    override fun toString() =
        if (isItemComplete)
            "$transactionId: Number Bought($numberBought), Customer Name($customerName), Date($date), Sales Person($salesPerson) (Complete)"
        else
            "$transactionId: Number Bought($numberBought), Customer Name($customerName), Date($date), Sales Person($salesPerson)"
}
