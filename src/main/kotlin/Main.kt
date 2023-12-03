
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
            1 -> load()
            2 -> addElectronicItem()
            3 -> listAllElectronics()
            4 -> updateElectronic()
            5 -> addPriceForElectronicItem()
            6 -> checkPriceOfElectronicItem()
            //7 -> addTransactionToElectronicItem()
            8 -> updateTransactionInElectronicItem()
            9 -> markTransactionStatusInElectronicItem()
            10 -> sellElectronicItem()
            11 -> save()
            12 -> searchElectronicsByProductCode()
            //13 -> listArchivedElectronics()
            14 -> addPriceForElectronicItem()

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
         > |   1) load                       |
         > |   2) addElectronicItem                        |
         > |   3) listAllElectronics                    |
         > |   4) updateElectronic                    |
         > |   5) addPriceForElectronicItem()                  |
         > -----------------------------------------------------  
         > | TRANSACTION MENU                                  | 
         > |   6) archiveElectronicItem        |
         > |   7) addTransactionToElectronicItem                                         |
         > |   8) updateTransactionInElectronicItem   |
         > |   9) markTransactionStatusInElectronicItem                      | 
         > -----------------------------------------------------  
         > | REPORT MENU FOR ELECTRONIC ITEMS                  | 
         > |   10) sellElectronicItem             |
         > |   11) save                    |
         > 12 -> searchElectronicsByProductCode
         > 13 -> listArchivedElectronics()
         > 14 -> addPriceForElectronicItem()
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
    electronicAPI.listAllElectronics()

    if (electronicAPI.numberOfElectronics() > 0) {
        val id = readNextInt("Enter the id of the electronic item to update: ")

        if (electronicAPI.findElectronic(id) != null) {
            // Get the updated details from the user
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
                reorderLevel = reorderLevel,

            )

            // Update the electronic item
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

fun sellElectronicItem() {
    println(electronicAPI.sellElectronicItem())

}
fun addPriceForElectronicItem() {
    println(electronicAPI.addPriceForElectronicItem())
}

fun checkPriceOfElectronicItem() {
    println(electronicAPI.checkPriceOfElectronicItem())
}

//fun archiveElectronicItem() {
  //  electronicAPI.archiveElectronicItem()
//}
//fun listArchivedElectronics() {
   // electronicAPI.listArchivedElectronics()
//}

//fun addTransactionToElectronicItem() {
   // electronicAPI.listAllElectronics()
   // electronicAPI.addTransactionToElectronicItem()
//}
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

//private fun recordSaleMenu() {
    //val staffId = readNextInt("Enter Staff ID: ")
   // val customerName = readNextLine("Enter Customer Name: ")
    //val itemId = readNextInt("Enter Item ID: ")


    //if (electronicAPI.recordSale(staffId, customerName, itemId)) {
       // println("Sale recorded successfully.")
    //} else {
      //  println("Failed to record sale.")
   // }
//}




