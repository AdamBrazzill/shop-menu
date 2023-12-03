package models

data class Transactions(
    var transactionId: Int = 0,
    var numberBought: Int,
    var customerName: String,
    var salesPerson: String,
    var isItemComplete: Boolean = false,
    var price: Int = 0 // Add the new property for the price){}
) {
    override fun toString() =
        if (isItemComplete)
            "$transactionId: Number Bought($numberBought), Customer Name($customerName),  Sales Person($salesPerson) (Complete)"
        else
            "$transactionId: Number Bought($numberBought), Customer Name($customerName),  Sales Person($salesPerson)"
}
