package models

/**
 * Data class representing a transaction associated with an electronic item.
 *
 * @property transactionId The unique identifier for the transaction.
 * @property numberBought The number of items bought in the transaction.
 * @property customerName The name of the customer associated with the transaction.
 * @property salesPerson The salesperson associated with the transaction.
 * @property isItemComplete Flag indicating whether the transaction is complete.
 */
data class Transactions(
    var transactionId: Int = 0,
    var numberBought: Int,
    var customerName: String,
    var salesPerson: String,
    var isItemComplete: Boolean,
    // var itemType: String // Uncomment if needed in the future
) {

    /**
     * Returns a string representation of the transaction.
     *
     * @return A formatted string containing transaction details.
     */
    override fun toString() =
        if (isItemComplete)
            "$transactionId: Number Bought($numberBought), Customer Name($customerName),  Sales Person($salesPerson) (Complete)"
        else
            "$transactionId: Number Bought($numberBought), Customer Name($customerName),  Sales Person($salesPerson)"
}
