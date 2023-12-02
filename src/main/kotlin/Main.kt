
import controllers.ElectronicAPI
import models.Electronics
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine

val ElectronicAPI = ElectronicAPI()
fun main() = runMenu()

fun runMenu() {
    do {
        when (val option = mainMenu()) {

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
    listOfElectronics()

    if (ElectronicAPI.numberOfElectronics() > 0) {
        val id = readNextInt("Enter the id of the electronic item to update: ")

        if (electronicAPI.findElectronic(id) != null) {
            val productCode = readNextLine("Enter a product code for the electronic item: ")
            val type = readNextLine("Enter the type for the electronic item: ")
            val unitCost = readNextInt("Enter the unit cost for the electronic item: ")
            val numberInStock = readNextInt("Enter the number in stock for the electronic item: ")
            val reorderLevel = readNextInt("Enter the reorder level for the electronic item: ")

            // pass the index of the electronic item and the new details to ElectronicAPI for updating
            if (ElectronicAPI.update(id, Electronics(
                    electronicId = 0,
                    productCode = productCode,
                    type = type,
                    unitCost = unitCost.toDouble(),
                    numberInStock = numberInStock,
                    reorderLevel = reorderLevel,
                    isNoteArchived = false))) {
                println("Update Successful")
            } else {
                println("Update Failed")
            }
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

    if (ElectronicAPI.addElectronic(newElectronic)) {
        println("Electronic item added successfully.")
    } else {
        println("Failed to add electronic item.")
    }
}
