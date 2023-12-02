package controllers

import models.Electronics

import models.Transactions
import persistence.Serializer
import persistence.XMLSerializer
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File

/**
 * The `ElectronicAPI` class manages electronic items and provides various operations to interact with them.
 *
 * @property serializer The serializer used for data storage.
 */
private val electronicAPI = ElectronicAPI(XMLSerializer(File("electronics.xml")))

class ElectronicAPI(serializerType: Serializer) {
    private var electronicsList = ArrayList<Electronics>()
    private var serializer: Serializer = serializerType

    /**
     * Formats a list of electronics into a string.
     *
     * @param electronicsToFormat The list of electronics to format.
     * @return A string representation of the formatted electronics.
     */
    private fun formatListString(electronicsToFormat: List<Electronics>): String =
        electronicsToFormat
            .joinToString(separator = "\n") { electronic ->
                electronicsList.indexOf(electronic).toString() + ": " + electronic.toString()
            }

    /**
     * Adds an electronic item to the list of electronics.
     *
     * @param electronic The electronic item to add.
     * @return `true` if the electronic item is successfully added, `false` otherwise.
     */
    fun addElectronic(electronic: Electronics): Boolean {
        electronic.electronicId = getId()
        return electronicsList.add(electronic)
    }

    /**
     * Lists all electronics.
     *
     * @return A string representation of all electronics.
     */
    fun listAllElectronics(): String =
        if (electronicsList.isEmpty()) "No electronics stored"
        else formatListString(electronicsList)

    /**
     * Lists active electronics.
     *
     * @return A string representation of active electronics.
     */
    fun listActiveElectronics(): String =
        if (numberOfActiveElectronics() == 0) "No active electronics stored"
        else formatListString(electronicsList.filter { electronic -> !electronic.isElectronicArchived })

    /**
     * Lists archived electronics.
     *
     * @return A string representation of archived electronics.
     */
    fun listArchivedElectronics(): String =
        if (numberOfArchivedElectronics() == 0) "No archived electronics stored"
        else formatListString(electronicsList.filter { electronic -> electronic.isElectronicArchived })

    /**
     * Gets the number of archived electronics.
     *
     * @return The number of archived electronics.
     */
    fun numberOfArchivedElectronics(): Int = electronicsList.count { electronic -> electronic.isElectronicArchived }

    /**
     * Gets the total number of electronics.
     *
     * @return The total number of electronics.
     */
    fun numberOfElectronics(): Int = electronicsList.size

    /**
     * Finds an electronic item by its index.
     *
     * @param index The index of the electronic item to find.
     * @return The found electronic item, or null if the index is invalid.
     */
    fun findElectronic(index: Int): Electronics? {
        return if (isValidListIndex(index, electronicsList)) {
            electronicsList[index]
        } else null
    }

    /**
     * Checks if an index is valid within a list.
     *
     * @param index The index to check.
     * @param list The list to check against.
     * @return `true` if the index is valid, `false` otherwise.
     */
    private fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    /**
     * Gets the number of active electronics.
     *
     * @return The number of active electronics.
     */
    fun numberOfActiveElectronics(): Int = electronicsList.count { electronic -> !electronic.isElectronicArchived }

    /**
     * Deletes an electronic item by its index.
     *
     * @param indexToDelete The index of the electronic item to delete.
     * @return The deleted electronic item, or null if the index is invalid.
     */


    /**
     * Updates an electronic item by its index.
     *
     * @param indexToUpdate The index of the electronic item to update.
     * @param electronic The new electronic item details.
     * @return `true` if the update is successful, `false` otherwise.
     */
    fun updateElectronic(indexToUpdate: Int, electronic: Electronics?): Boolean {
        // Find the electronic item object by the index number
        val foundElectronic = findElectronic(indexToUpdate)
        // If the electronic item exists, use the details passed as parameters to update the found item in the ArrayList.
        if ((foundElectronic != null) && (electronic != null)) {
            foundElectronic.productCode = electronic.productCode
            foundElectronic.type = electronic.type
            foundElectronic.unitCost = electronic.unitCost
            foundElectronic.numberInStock = electronic.numberInStock
            foundElectronic.reorderLevel = electronic.reorderLevel
            foundElectronic.isElectronicArchived = electronic.isElectronicArchived
            return true
        }
        // If the electronic item was not found, return false
        return false
    }

    /**
     * Checks if an index is valid within the list of electronics.
     *
     * @param index The index to check.
     * @return `true` if the index is valid, `false` otherwise.
     */
    fun isValidIndex(index: Int): Boolean {
        return isValidListIndex(index, electronicsList)
    }

    /**
     * Loads electronics from a data source.
     *
     * @return `true` if the load is successful, `false` otherwise.
     */
    @Throws(Exception::class)
    fun load(): Boolean {
        return try {
            electronicsList = serializer.read() as ArrayList<Electronics>
            true // Load successful
        } catch (e: Exception) {
            false // Load failed
        }
    }

    /**
     * Stores electronics using the data source.
     */
    @Throws(Exception::class)
    fun store() {
        serializer.write(electronicsList)
    }

    /**
     * Archives an electronic item by its index.
     *
     * @param indexToArchive The index of the electronic item to archive.
     * @return `true` if the archive is successful, `false` otherwise.
     */
    fun archiveElectronic(indexToArchive: Int): Boolean {
        if (isValidIndex(indexToArchive)) {
            val electronicToArchive = electronicsList[indexToArchive]
            if (!electronicToArchive.isElectronicArchived) {
                electronicToArchive.isElectronicArchived = true
                return true
            }
        }
        return false
    }

    /**
     * Searches for electronics by product code.
     *
     * @param searchString The product code or part of a product code to search for.
     * @return A string representation of matching electronics.
     */
    fun searchElectronicsByProductCode(searchString: String): String =
        formatListString(
            electronicsList.filter { electronic -> electronic.productCode.contains(searchString, ignoreCase = true) }
        )

    /**
     * Lists electronics by their type.
     *
     * @param type The type to filter by.
     * @return A string representation of electronics of the specified type.
     */
    fun listElectronicsByType(type: String): String {
        val filteredElectronics = electronicsList.filter { electronic -> electronic.type.equals(type, ignoreCase = true) }
        return if (filteredElectronics.isEmpty()) {
            "No Electronics of type $type"
        } else {
            val listOfElectronics = formatListString(filteredElectronics)
            "${countElectronicsByType(type)} electronics of type $type: $listOfElectronics"
        }
    }

    fun archiveElectronicItem() {
        listAllElectronics()

        if (numberOfElectronics() > 0) {
            val id = readNextInt("Enter the id of the electronic item to archive: ")

            if (findElectronic(id) != null) {
                if (archiveElectronic(id)) {
                    println("Electronic item archived successfully.")
                } else {
                    println("Failed to archive electronic item.")
                }
            } else {
                println("There are no electronic items for this index number.")
            }
        }
    }
    fun deleteElectronic(id: Int): Boolean {
        val initialSize = electronicsList.size
        electronicsList.removeIf { electronic -> electronic.electronicId == id }
        return electronicsList.size < initialSize
    }

    fun addTransactionToElectronicItem() {
        listAllElectronics()

        if (numberOfElectronics() > 0) {
            val id = readNextInt("Enter the id of the electronic item to add a transaction to: ")

            findElectronic(id)?.let { electronic ->
                val numberBought = readNextInt("Enter the number bought: ")
                val customerName = readNextLine("Enter the customer name: ")
                val date = readNextLine("Enter the date: ")
                val salesPerson = readNextLine("Enter the sales person: ")

                val newTransaction = Transactions(
                    transactionId = 0,
                    numberBought = numberBought,
                    customerName = customerName,
                    date = date,
                    salesPerson = salesPerson
                )

                if (Electronics.addTransaction()) {
                    println("Transaction added successfully.")
                } else {
                    println("Failed to add transaction.")
                }
            } ?: println("There are no electronic items for this index number.")
        }
    }

    fun updateTransactionInElectronicItem() {
        listAllElectronics()

        if (numberOfElectronics() > 0) {
            val electronicId = readNextInt("Enter the id of the electronic item to update a transaction for: ")

            findElectronic(electronicId)?.let { electronic ->
                val transactionId = readNextInt("Enter the id of the transaction to update: ")

                val existingTransaction = electronic.transactions.find { it.transactionId == transactionId }

                if (existingTransaction != null) {
                    val updatedNumberBought = readNextInt("Enter the updated number bought: ")
                    val updatedCustomerName = readNextLine("Enter the updated customer name: ")
                    val updatedDate = readNextLine("Enter the updated date: ")
                    val updatedSalesPerson = readNextLine("Enter the updated sales person: ")

                    existingTransaction.apply {
                        numberBought = updatedNumberBought
                        customerName = updatedCustomerName
                        date = updatedDate
                        salesPerson = updatedSalesPerson
                    }

                    println("Transaction updated successfully.")
                } else {
                    println("Transaction with id $transactionId not found in the electronic item.")
                }
            } ?: println("Electronic item with id $electronicId not found.")
        }
    }
    fun markTransactionStatus(electronicAPI: ElectronicAPI) {
        electronicAPI.listAllElectronics()

        if (electronicAPI.numberOfElectronics() > 0) {
            val electronicId = readNextInt("Enter the id of the electronic item to mark transaction status: ")

            electronicAPI.findElectronic(electronicId)?.let { electronic ->
                val transactionId = readNextInt("Enter the id of the transaction to mark status: ")

                val existingTransaction = electronic.transactions.find { it.transactionId == transactionId }

                if (existingTransaction != null) {
                    val isComplete = readNextLine("Mark transaction as complete? (yes/no)").equals("yes", ignoreCase = true)

                    existingTransaction.isItemComplete = isComplete

                    println("Transaction status marked successfully.")
                } else {
                    println("Transaction with id $transactionId not found in the electronic item.")
                }
            } ?: println("Electronic item with id $electronicId not found.")
        }
    }

    fun recordSale(staffId: Int, customerName: String, itemId: Int): Boolean {
        try {
            val electronic = findElectronic(itemId)

            if (electronic != null) {
                val price = electronic.price

                if (price > 0) {
                    val saleTransaction = Transactions(
                        transactionId = getId(),
                        numberBought = 1,
                        customerName = customerName,
                        date = getCurrentDate(),
                        salesPerson = "Staff ID: $staffId",
                        isItemComplete = true
                    )

                    electronic.transactions.add(saleTransaction)
                    println("Sale recorded successfully. Price: $price")
                    return true
                } else {
                    println("Error: Item with ID $itemId doesn't have a price.")
                }
            } else {
                println("Error: Electronic item with ID $itemId not found.")
            }
        } catch (e: Exception) {
            println("Failed to record sale. Error: ${e.message}")
        }
        return false
    }



    private fun getCurrentDate(): String {
        // Implement your logic to get the current date as a string
        return "2023-12-01" // Placeholder date, replace it with actual logic
    }









    /**
     * Counts the number of electronics of the specified type.
     *
     * @param type The type to count electronics for.
     * @return The number of electronics of the specified type.
     */
    fun countElectronicsByType(type: String): Int =
        electronicsList.count { electronic -> electronic.type.equals(type, ignoreCase = true) }

    /**
     * Gets the next available ID for electronics.
     *
     * @return The next available ID.
     */
    private fun getId(): Int = electronicsList.size


}


