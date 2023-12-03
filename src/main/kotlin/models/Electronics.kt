package models
data class Electronics(
    var electronicId: Int,
    var productCode: String,
    var type: String,
    var unitCost: Double,
    var numberInStock: Int,
    var reorderLevel: Int,
    var transactions: MutableList<Transactions> = mutableListOf()

) {

    companion object {
        // Map to store itemId and corresponding prices
        val itemPrices: MutableMap<Int, Int> = mutableMapOf()

        fun addTransaction(
            itemId: Int,
            customerName: String,
            numberBought: Int,
            salesPerson: String,
            price: Int
        ) {
            // Implementation to add a transaction
            val transaction = Transactions(
                 0,
                 numberBought,
                 customerName,
                 salesPerson,
                 false,

            )
        }
    }
}
