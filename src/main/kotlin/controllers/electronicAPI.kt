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
    fun isValidElectronicId(id: Int): Boolean {
        return electronicsList.any { it.electronicId == id }
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

            return true
        }

        return false
    }

    /**
     * Checks if an index is valid within the list of electronics.
     *
     * @param index The index to check.
     * @return `true` if the index is valid, `false` otherwise.
     */


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


    fun deleteElectronic(id: Int): Boolean {
        val initialSize = electronicsList.size
        electronicsList.removeIf { electronic -> electronic.electronicId == id }
        return electronicsList.size < initialSize
    }

    fun addPriceForElectronicItem() {
        listAllElectronics()

        if (numberOfElectronics() > 0) {
            val electronicId = readNextInt("Enter the ID of the electronic item to add a price to: ")

            if (isValidElectronicId(electronicId)) {
                val price = readNextInt("Enter the price for the electronic item: ")

                val success = Electronics.addTransaction(
                    itemId = electronicId,
                    customerName = "DefaultCustomer", // You can modify this as needed
                    numberBought = 1, // You can modify this as needed
                    salesPerson = "DefaultSalesPerson" // You can modify this as needed
                )

                if (success) {
                    Electronics.itemPrices[electronicId] = price
                    println("Price added successfully.")
                } else {
                    println("Failed to add transaction for electronic item.")
                }
            } else {
                println("Invalid electronic item ID.")
            }
        } else {
            println("There are no electronic items to add a price to.")
        }
    }

    fun checkPriceOfElectronicItem() {
        listAllElectronics()

        if (numberOfElectronics() > 0) {
            val electronicId = readNextInt("Enter the ID of the electronic item to check the price: ")

            if (isValidElectronicId(electronicId)) {
                val price = Electronics.itemPrices[electronicId]
                if (price != null) {
                    println("The price of the electronic item with ID $electronicId is: $price")
                } else {
                    println("Price not available for electronic item with ID $electronicId.")
                }
            } else {
                println("Invalid electronic item ID.")
            }
        } else {
            println("There are no electronic items to check the price for.")
        }
    }


    fun addTransactionToElectronicItem() {
        listAllElectronics()

        if (numberOfElectronics() > 0) {
            val id = readNextInt("Enter the id of the electronic item to add a transaction to: ")

            findElectronic(id)?.let { electronic ->
                // Check if itemPrices map contains the price for the given itemId
                if (Electronics.itemPrices.containsKey(id)) {
                    val unitCost = electronic.unitCost // Get the unit cost from the electronic item


                    val numberBought = readNextInt("Enter the number bought: ")
                    val customerName = readNextLine("Enter the customer name: ")

                    val salesPerson = readNextLine("Enter the sales person: ")

                    val newTransaction = Transactions(
                        transactionId = 0,
                        numberBought = numberBought,
                        customerName = customerName,
                        salesPerson = salesPerson,
                        isItemComplete = false
                    )

                    electronic.transactions.add(newTransaction)
                    println("Transaction added successfully.")
                } else {
                    println("Price not available for itemId $id.")
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
                    val updatedSalesPerson = readNextLine("Enter the updated sales person: ")

                    existingTransaction.apply {
                        numberBought = updatedNumberBought
                        customerName = updatedCustomerName
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



    fun sellElectronicItem() {

        val staffId = readNextInt("Enter the staff ID: ")

        listAllElectronics()

        if (numberOfElectronics() > 0) {
            val electronicId = readNextInt("Enter the ID of the electronic item to sell: ")

            findElectronic(electronicId)?.let { electronic ->
                // Check if itemPrices map contains the price for the given itemId
                if (Electronics.itemPrices.containsKey(electronicId)) {
                    val unitCost = electronic.unitCost // Get the unit cost from the electronic item


                    val numberSold = readNextInt("Enter the number sold: ")
                    val customerName = readNextLine("Enter the customer name: ")

                    val saleTransaction = Transactions(
                        transactionId = 0,
                        numberBought = -numberSold, // Negative to indicate a sale
                        customerName = customerName,
                        salesPerson = "Staff ID: $staffId",
                        isItemComplete = true

                    )

                    electronic.transactions.add(saleTransaction)
                    println("Sale recorded successfully.")

                    // Display sold item details
                    println("Customer: $customerName")
                    println("Item ID: $electronicId")
                    println("Product Code: ${electronic.productCode}")
                    println("Unit Cost: $unitCost")
                    println("Item Type: ${electronic.type}")

                    // Save the updated electronics list to XML
                    store()
                } else {
                    println("Price not available for itemId $electronicId.")
                }
            } ?: println("Electronic item with id $electronicId not found.")
        } else {
            println("There are no electronic items to sell.")
        }
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


