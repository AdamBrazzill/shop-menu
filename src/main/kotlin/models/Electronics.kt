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
        private var electronicsList = ArrayList<Electronics>()

        val itemPrices: MutableMap<Int, Int> = mutableMapOf()

        fun addTransaction(
            itemId: Int,
            customerName: String,
            numberBought: Int,
            salesPerson: String
        ): Boolean {
            // Check if the itemId is valid
            val electronicsItem = getElectronicsItem(itemId)
            return if (electronicsItem != null) {
                // Implementation to add a transaction
                val transaction = Transactions(
                    0,
                    numberBought,
                    customerName,
                    salesPerson,
                    false
                )

                // Add the transaction to the list of transactions for the corresponding item
                electronicsItem.transactions.add(transaction)
                true
            } else {
                println("Price not available for itemId $itemId.")
                false
            }
        }

        public fun getElectronicsItem(itemId: Int): Electronics? {

            return electronicsList.find { it.electronicId == itemId }
        }
    }
}
