package models

data class Electronics(
    var noteId: Int = 0,
    var electronicId: Int,
    var productCode: String,
    var type: String,
    var unitCost: Double,
    var numberInStock: Int,
    var reorderLevel: Int,
    var isNoteArchived: Boolean = false,
    var items: MutableSet<item> = mutableSetOf()
)
data class item(
    var itemId: Int = 0,
    // other properties of your item
    var isItemComplete: Boolean = false
)

{
    fun markItemStatus(transactionId: Int) {
        val transaction = findTransaction(transactionId)
        transaction?.isItemComplete = true
    }

    fun findTransaction(transactionId: Int): Transactions? {
        return transactions.find { it.transactionId == transactionId }
    }
}