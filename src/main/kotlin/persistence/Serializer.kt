package persistence

/**
 * Serializer interface for reading and writing objects.
 */
interface Serializer {
    /**
     * Write an object to a storage medium.
     *
     * @param obj The object to be written.
     * @throws Exception if any errors occur during the writing process.
     */
    @Throws(Exception::class)
    fun write(obj: Any?)

    /**
     * Read an object from a storage medium.
     *
     * @return The deserialized object read from the storage medium.
     * @throws Exception if any errors occur during the reading process.
     */
    @Throws(Exception::class)
    fun read(): Any?
}
