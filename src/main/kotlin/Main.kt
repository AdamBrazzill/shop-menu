
import controllers.ElectronicAPI
import models.Electronics
import persistence.XMLSerializer
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File

private val electronicAPI = ElectronicAPI(XMLSerializer(File("electronics.xml")))
fun main() = runMenu()

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> addElectronicItem()
            2 -> listAllElectronics(electronicAPI)
            3 -> updateElectronic()
            4 -> deleteElectronicItem()
            5 -> archiveElectronicItem(electronicAPI)
            6 -> addTransactionToElectronicItem(electronicAPI)
            7 -> updateTransactionInElectronicItem(electronicAPI)
            // Add other options here
            0 -> {
                // Save data before exiting
                electronicAPI.store()
                println("Exiting the system. Goodbye!")
                return
            }
            else -> println("Invalid menu choice: $option")
        }
    } while (true)
}

fun mainMenu() = readNextInt(
    """ 
         > -----------------------------------------------------  
         > |                  Store System                     |
         > -----------------------------------------------------  
         > | ELECTRONIC MENU                                   |
         > |   1) Add an electronic item                       |
         > |   2) List electronic items                        |
         > |   3) Update an electronic item                    |
         > |   4) Delete an electronic item                    |
         > |   5) Archive an electronic item                   |
         > -----------------------------------------------------  
         > | TRANSACTION MENU                                  | 
         > |   6) Add transaction to an electronic item        |
         > |   7) Update transaction details in an electronic  |
         > |      item                                         |
         > |   8) Delete transaction from an electronic item   |
         > |   9) Mark transaction status                      | 
         > -----------------------------------------------------  
         > | REPORT MENU FOR ELECTRONIC ITEMS                  | 
         > |   10) Search for all electronic items             |
         > |   11) Change a electronic name                    |
         > -----------------------------------------------------  
         > | REPORT MENU FOR TRANSACTIONS                      |                                
         > |   15) Search for all transactions                 |
         > -----------------------------------------------------  
         > |   0) Exit                                         |
         > -----------------------------------------------------  
         > ==>> """.trimMargin(">")
)

//------------------------------------
//ELECTRONIC MENU
//------------------------------------

fun updateElectronic() {
    electronicAPI.listAllElectronics()  // Use listAllElectronics() to display the list

    if (electronicAPI.numberOfElectronics() > 0) {
        val id = readNextInt("Enter the id of the electronic item to update: ")

        if (electronicAPI.findElectronic(id) != null) {
            // ... (rest of the code)
        } else {
            println("There are no electronic items for this index number")
        }
    }
}
fun addElectronicItem() {
    val productCode = readNextLine("Enter a product code for the electronic item: ")
    val type = readNextLine("Enter the type for the electronic item: ")
    val unitCost = readNextInt("Enter the unit cost for the electronic item: ")
    val numberInStock = readNextInt("Enter the number in stock for the electronic item: ")
    val reorderLevel = readNextInt("Enter the reorder level for the electronic item: ")

    val newElectronic = Electronics(
        electronicId = 0,
        productCode = productCode,
        type = type,
        unitCost = unitCost.toDouble(),
        numberInStock = numberInStock,
        reorderLevel = reorderLevel,
        isNoteArchived = false
    )

    if (electronicAPI.addElectronic(newElectronic)) {
        println("Electronic item added successfully.")
    } else {
        println("Failed to add electronic item.")
    }
}
fun listAllElectronics(ElectronicAPI: ElectronicAPI) {
    println(electronicAPI.listAllElectronics())
}

fun archiveElectronicItem(ElectronicAPI: ElectronicAPI) {
    electronicAPI.archiveElectronicItem()
}

fun addTransactionToElectronicItem(electronicAPI: ElectronicAPI) {
    electronicAPI.addTransactionToElectronicItem()
}
fun deleteElectronicItem() {
    electronicAPI.listAllElectronics()

    if (electronicAPI.numberOfElectronics() > 0) {
        val id = readNextInt("Enter the id of the electronic item to delete: ")

        if (electronicAPI.deleteElectronic(id)) {
            println("Electronic item deleted successfully.")
        } else {
            println("Failed to delete electronic item.")
        }
    }
}
fun updateTransactionInElectronicItem(electronicAPI: ElectronicAPI) {
    electronicAPI.listAllElectronics()

    if (electronicAPI.numberOfElectronics() > 0) {
        val electronicId = readNextInt("Enter the id of the electronic item to update a transaction for: ")

        electronicAPI.findElectronic(electronicId)?.let { electronic ->
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




