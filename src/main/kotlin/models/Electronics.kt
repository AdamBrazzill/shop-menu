package models

data class Electronics(
    var itemId: Int = 0,
    var electronicId: Int,
    var productCode: String,
    var type: String,
    var unitCost: Double,
    var numberInStock: Int,
    var reorderLevel: Int,
    var isNoteArchived: Boolean = false,
    var transactions: MutableList<Transactions> = mutableListOf(),
    var price: Int = 0 // Add a new property for the price
) {
    companion object {
        fun addTransaction(newTransaction: Transactions): Boolean {
            TODO("Not yet implemented")
        }
    }
}

data class Item(
    var itemId: Int = 0,
    // other properties of your item
    var isItemComplete: Boolean = false,
    var transactions: MutableList<Transactions> = mutableListOf()
) //{
    //fun addTransaction(newTransaction: Transactions): Boolean {
       // return transactions.add(Transaction)
    //}
//}
