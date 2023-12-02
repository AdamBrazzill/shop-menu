package persistence

import com.thoughtworks.xstream.XStream
import com.thoughtworks.xstream.io.xml.DomDriver
import models.Electronics
import java.io.File
import java.io.FileReader
import java.io.FileWriter

/**
 * XMLSerializer class for reading and writing objects to XML files.
 *
 * @param file The File object representing the XML file to read from or write to.
 */
class XMLSerializer(private val file: File) : Serializer {

    /**
     * Read data from the XML file and deserialize it into an object.
     *
     * @return Deserialized object read from the XML file.
     * @throws Exception if any errors occur during the reading process.
     */
    @Throws(Exception::class)
    override fun read(): Any {
        val xStream = XStream(DomDriver())
        xStream.allowTypes(arrayOf(Electronics::class.java))
        val inputStream = xStream.createObjectInputStream(FileReader(file))
        val obj = inputStream.readObject() as Any
        inputStream.close()
        return obj
    }

    /**
     * Write an object to the XML file by serializing it.
     *
     * @param obj The object to be serialized and written to the XML file.
     * @throws Exception if any errors occur during the writing process.
     */
    @Throws(Exception::class)
    override fun write(obj: Any?) {
        val xStream = XStream(DomDriver())
        val outputStream = xStream.createObjectOutputStream(FileWriter(file))
        outputStream.writeObject(obj)
        outputStream.close()
    }
}
