

/*
class ElectronicAPITest {
    private lateinit var electronic1: Electronics
    private lateinit var electronic2: Electronics
    private lateinit var electronic3: Electronics
    private lateinit var electronicAPI: ElectronicAPI

    @BeforeEach
    fun setUp() {
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
        assertEquals(1, electronicAPI.numberOfElectronics())
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
        assertFalse(electronicAPI.isValidElectronicId(1))
    }

    @Test
    fun testListAllElectronics() {
        assertEquals("No electronics stored", electronicAPI.listAllElectronics())

        val electronic = Electronics(
            electronicId = 0,
            productCode = "TEST123",
            type = "Test Type",
            unitCost = 50.0,
            numberInStock = 10,
            reorderLevel = 5
        )

        electronicAPI.addElectronic(electronic)

        val expectedOutput = "0: $electronic"
        assertEquals(expectedOutput, electronicAPI.listAllElectronics())
    }


}
*/

