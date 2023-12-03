package models

/**
 * Data class representing an electronic item.
 *
 * @property electronicId The unique identifier for the electronic item.
 * @property productCode The product code of the electronic item.
 * @property type The type of the electronic item.
 * @property unitCost The unit cost of the electronic item.
 * @property numberInStock The current stock level of the electronic item.
 * @property reorderLevel The reorder level for the electronic item.
 * @property transactions The list of transactions associated with the electronic item.
 */
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

        /**
         * Map to store item prices with electronic item IDs as keys.
         */
        val itemPrices: MutableMap<Int, Int> = mutableMapOf()

        /**
         * Adds a transaction for an electronic item.
         *
         * @param itemId The ID of the electronic item.
         * @param customerName The name of the customer.
         * @param numberBought The number of items bought in the transaction.
         * @param salesPerson The salesperson associated with the transaction.
         * @return `true` if the transaction is successfully added, `false` otherwise.
         */
        fun addTransaction(
            itemId: Int,
            customerName: String,
            numberBought: Int,
            salesPerson: String
        ): Boolean {
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

        /**
         * Retrieves an electronic item by its ID.
         *
         * @param itemId The ID of the electronic item.
         * @return The electronic item with the specified ID, or `null` if not found.
         */
        private fun getElectronicsItem(itemId: Int): Electronics? {
            return electronicsList.find { it.electronicId == itemId }
        }
    }
}
