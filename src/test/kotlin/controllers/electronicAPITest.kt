
import controllers.ElectronicAPI
import models.Electronics
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import persistence.XMLSerializer
import java.io.File


class ElectronicAPITest {
    private lateinit var electronic1: Electronics
    private lateinit var electronic2: Electronics
    private lateinit var electronic3: Electronics
    private lateinit var electronicAPI: ElectronicAPI

    @BeforeEach
    fun setUp() {
        electronicAPI = ElectronicAPI(XMLSerializer(File("electronics.xml")))
        // Initialize the Electronics and ElectronicAPI before each test
        electronic1 = Electronics(
            electronicId = 0,
            productCode = "ELECTRONIC1",
            type = "Type1",
            unitCost = 100.0,
            numberInStock = 10,
            reorderLevel = 5
        )

        electronic2 = Electronics(
            electronicId = 1,
            productCode = "ELECTRONIC2",
            type = "Type2",
            unitCost = 150.0,
            numberInStock = 8,
            reorderLevel = 3
        )

        electronic3 = Electronics(
            electronicId = 2,
            productCode = "ELECTRONIC3",
            type = "Type1",
            unitCost = 120.0,
            numberInStock = 12,
            reorderLevel = 6
        )

        electronicAPI.addElectronic(electronic1)
        electronicAPI.addElectronic(electronic2)
        electronicAPI.addElectronic(electronic3)
    }

    @Test
    fun testAddElectronic() {
        val electronic = Electronics(
            electronicId = 0,
            productCode = "TEST123",
            type = "Test Type",
            unitCost = 50.0,
            numberInStock = 10,
            reorderLevel = 5
        )

        assertTrue(electronicAPI.addElectronic(electronic))
        assertEquals(4, electronicAPI.numberOfElectronics())
    }

    @Test
    fun `validating if electronic ID is valid`() {

        val electronic = Electronics(
            electronicId = 0,
            productCode = "TEST123",
            type = "Test Type",
            unitCost = 50.0,
            numberInStock = 10,
            reorderLevel = 5
        )

        electronicAPI.addElectronic(electronic)
        assertTrue(electronicAPI.isValidElectronicId(0))
        assertTrue(electronicAPI.isValidElectronicId(1))
    }

    @Test
    fun testListAllElectronics() {
        val expectedOutput = """
        |0: Electronics(electronicId=0, productCode=ELECTRONIC1, type=Type1, unitCost=100.0, numberInStock=10, reorderLevel=5, transactions=[])
        |1: Electronics(electronicId=1, productCode=ELECTRONIC2, type=Type2, unitCost=150.0, numberInStock=8, reorderLevel=3, transactions=[])
        |2: Electronics(electronicId=2, productCode=ELECTRONIC3, type=Type1, unitCost=120.0, numberInStock=12, reorderLevel=6, transactions=[])
    """.trimMargin()

        assertEquals(expectedOutput, electronicAPI.listAllElectronics())

        val electronic = Electronics(
            electronicId = 3,
            productCode = "TEST123",
            type = "Test Type",
            unitCost = 50.0,
            numberInStock = 10,
            reorderLevel = 5
        )

        electronicAPI.addElectronic(electronic)

        val updatedExpectedOutput = """
        |0: Electronics(electronicId=0, productCode=ELECTRONIC1, type=Type1, unitCost=100.0, numberInStock=10, reorderLevel=5, transactions=[])
        |1: Electronics(electronicId=1, productCode=ELECTRONIC2, type=Type2, unitCost=150.0, numberInStock=8, reorderLevel=3, transactions=[])
        |2: Electronics(electronicId=2, productCode=ELECTRONIC3, type=Type1, unitCost=120.0, numberInStock=12, reorderLevel=6, transactions=[])
        |3: Electronics(electronicId=3, productCode=TEST123, type=Test Type, unitCost=50.0, numberInStock=10, reorderLevel=5, transactions=[])
    """.trimMargin()

        assertEquals(updatedExpectedOutput, electronicAPI.listAllElectronics())
    }


}


