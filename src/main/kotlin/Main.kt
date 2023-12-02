
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
            9 -> load()
            1 -> addElectronicItem()
            2 -> listAllElectronics()
            3 -> updateElectronic()
            4 -> deleteElectronicItem()
            5 -> archiveElectronicItem()
            6 -> addTransactionToElectronicItem()
            7 -> updateTransactionInElectronicItem()
            8 -> markTransactionStatusInElectronicItem()
            10 -> recordSaleMenu()
            11 -> save()
            12 -> searchElectronicsByProductCode()

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
fun listAllElectronics() {
    println(electronicAPI.listAllElectronics())
}

fun archiveElectronicItem() {
    electronicAPI.archiveElectronicItem()
}

fun addTransactionToElectronicItem() {
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
fun updateTransactionInElectronicItem() {
    electronicAPI.listAllElectronics()
    electronicAPI.updateTransactionInElectronicItem()
}
fun searchElectronicsByProductCode() {
    electronicAPI.listAllElectronics()

    val searchString = readNextLine("Enter the product code to search for: ")
    val result = electronicAPI.searchElectronicsByProductCode(searchString)

    println(result)
}

fun markTransactionStatusInElectronicItem() {
    electronicAPI.listAllElectronics()

    electronicAPI.markTransactionStatus(electronicAPI)
}

fun save() {
    try {
        electronicAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to a file: $e")
    }
}

/**
 * Loads notes from a file.
 */
fun load() {
    val isLoadSuccessful = electronicAPI.load()

    if (isLoadSuccessful) {
        println("Load successful")
    } else {
        println("Load failed")
    }
}

private fun recordSaleMenu() {
    val staffId = readNextInt("Enter Staff ID: ")
    val customerName = readNextLine("Enter Customer Name: ")
    val itemId = readNextInt("Enter Item ID: ")
    val price = readNextInt("Enter Price of the Item: ")

    if (electronicAPI.recordSale(staffId, customerName, itemId, price)) {
        println("Sale recorded successfully.")
    } else {
        println("Failed to record sale.")
    }
}




