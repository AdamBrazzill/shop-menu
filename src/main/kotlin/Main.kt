import controllers.ElectronicAPI
import models.Electronics
import persistence.XMLSerializer
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File

/**
 * Represents the main electronic store API.
 */
private val electronicAPI = ElectronicAPI(XMLSerializer(File("electronics.xml")))

/**
 * The main entry point for the electronic store system.
 */
fun main() = runMenu()

/**
 * Runs the main menu loop.
 */
fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> load()
            2 -> addElectronicItem()
            3 -> listAllElectronics()
            4 -> updateElectronic()
            5 -> addPriceForElectronicItem()
            6 -> checkPriceOfElectronicItem()
            7 -> sellElectronicItem()
            8 -> updateTransactionInElectronicItem()
            9 -> markTransactionStatusInElectronicItem()
            10 -> save()
            11 -> searchElectronicsByProductCode()
            12 -> deleteElectronicItem()
            0 -> {
                electronicAPI.store()
                println("Exiting the system. Goodbye!")
                return
            }
            else -> println("Invalid menu choice: $option")
        }
    } while (true)
}

/**
 * Displays the main menu and returns the user's choice.
 * @return The user's menu choice.
 */
fun mainMenu() = readNextInt(
    """ 
         > -----------------------------------------------------  
         > |                  Store System                     |
         > -----------------------------------------------------  
         > | ELECTRONIC MENU                                   |
         > |   1) Load Previous Electronics                    |
         > |   2) Add an Electronic Item to the system         |
         > |   3) List All Electronics                         |
         > |   4) Update info on an Electronic                 |
         > |   5) Add Price to an Electronic Item              |
         > -----------------------------------------------------  
         > | TRANSACTION MENU                                  | 
         > |   6) Check Price of an Item                       |
         > |   7) Sales                                        |
         > |   8) Update Transaction                           |
         > |   9) Mark Transaction                             | 
         > -----------------------------------------------------  
         > | REPORT MENU FOR ELECTRONIC ITEMS                  | 
         > |   10) Save                                        |
         > |   11) Search Item by Code                         |
         > |   12) Delete an Item                              |
         > |   0) Exit                                         |
         > -----------------------------------------------------  
         > ==>> """.trimMargin(">")
)

/**
 * Updates information for an electronic item.
 */
fun updateElectronic() {
    electronicAPI.listAllElectronics()

    if (electronicAPI.numberOfElectronics() > 0) {
        val id = readNextInt("Enter the id of the electronic item to update: ")

        if (electronicAPI.findElectronic(id) != null) {
            val productCode = readNextLine("Enter the updated product code: ")
            val type = readNextLine("Enter the updated type: ")
            val unitCost = readNextInt("Enter the updated unit cost: ")
            val numberInStock = readNextInt("Enter the updated number in stock: ")
            val reorderLevel = readNextInt("Enter the updated reorder level: ")

            val updatedElectronic = Electronics(
                electronicId = id,
                productCode = productCode,
                type = type,
                unitCost = unitCost.toDouble(),
                numberInStock = numberInStock,
                reorderLevel = reorderLevel
            )

            if (electronicAPI.updateElectronic(id, updatedElectronic)) {
                println("Electronic item updated successfully.")
            } else {
                println("Failed to update electronic item.")
            }
        } else {
            println("There is no electronic item with ID $id.")
        }
    } else {
        println("There are no electronic items to update.")
    }
}

/**
 * Adds a new electronic item to the system.
 */
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
        reorderLevel = reorderLevel
    )

    if (electronicAPI.addElectronic(newElectronic)) {
        println("Electronic item added successfully.")
    } else {
        println("Failed to add electronic item.")
    }
}

/**
 * Lists all electronic items in the system.
 */
fun listAllElectronics() {
    println(electronicAPI.listAllElectronics())
}

/**
 * Sells an electronic item.
 */
fun sellElectronicItem() {
    println(electronicAPI.sellElectronicItem())
}

/**
 * Adds a price for an electronic item.
 */
fun addPriceForElectronicItem() {
    println(electronicAPI.addPriceForElectronicItem())
}

/**
 * Checks the price of an electronic item.
 */
fun checkPriceOfElectronicItem() {
    println(electronicAPI.checkPriceOfElectronicItem())
}

/**
 * Deletes an electronic item.
 */
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

/**
 * Updates a transaction in an electronic item.
 */
fun updateTransactionInElectronicItem() {
    electronicAPI.listAllElectronics()
    electronicAPI.updateTransactionInElectronicItem()
}

/**
 * Searches for electronic items by product code.
 */
fun searchElectronicsByProductCode() {
    electronicAPI.listAllElectronics()

    val searchString = readNextLine("Enter the product code to search for: ")
    val result = electronicAPI.searchElectronicsByProductCode(searchString)

    println(result)
}

/**
 * Marks the transaction status in an electronic item.
 */
fun markTransactionStatusInElectronicItem() {
    electronicAPI.listAllElectronics()
    electronicAPI.markTransactionStatus(electronicAPI)
}

/**
 * Saves the electronic store data to a file.
 */
fun save() {
    try {
        electronicAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to a file: $e")
    }
}

/**
 * Loads electronic store data from a file.
 */
fun load() {
    val isLoadSuccessful = electronicAPI.load()

    if (isLoadSuccessful) {
        println("Load successful")
    } else {
        println("Load failed")
    }
}
