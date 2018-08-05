package anuz.maven.json;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import anuz.maven.xsd2xml.Address;
import anuz.maven.xsd2xml.Customer;
import anuz.maven.xsd2xml.PaymentMethod;

/**
 * main Application - for serialization and deserialization of Customer Data
 * 
 * @author anz
 *
 */
public class Application {

	// path of the json file that is to be written and then read
	private static String FILE_PATH = "src/main/resources/Customer.json";

	/**
	 * Main Method
	 * 
	 * @param args
	 * @throws IOException
	 * @throws DatatypeConfigurationException
	 */
	public static void main(String[] args) throws IOException, DatatypeConfigurationException {
		// serializing data
		serialize();

		// deserializing the data
		deserialize();
	}

	/**
	 * Serialization - Customer object is created inside - file path is pulled from static member
	 * @throws JsonGenerationException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws DatatypeConfigurationException
	 */
	private static void serialize()
			throws JsonGenerationException, JsonMappingException, IOException, DatatypeConfigurationException {
		ObjectMapper objectMapper = new ObjectMapper();
		// convert Object to json string
		Customer cust = createCustomer();

		// configure Object mapper for pretty print
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

		System.out.println(" *** Printing data to Screen and saving to File *** ");
		objectMapper.writeValue(new PrintWriter(FILE_PATH), cust);
		
		objectMapper.writeValue(System.out, cust);	
	}

	/**
	 * Deserialization - The file is the previously serialized file
	 * @throws IOException
	 */
	public static void deserialize() throws IOException {

		// read json file data to String
		byte[] jsonData = Files.readAllBytes(Paths.get(FILE_PATH));

		// create ObjectMapper instance
		ObjectMapper objectMapper = new ObjectMapper();

		// convert json string to object
		Customer cust = objectMapper.readValue(jsonData, Customer.class);

		System.out.println(" *** Deserialized data from File *** ");
		System.out.println("Customer Object\n" + cust);
	}

	/**
	 * Creating a sample customer to be serialized
	 * @return
	 * @throws DatatypeConfigurationException
	 */
	private static Customer createCustomer() throws DatatypeConfigurationException {
		Customer customer = new Customer();
		customer.setCustomerID(1);
		customer.setName("Anuj");
		customer.setAnnualSalary(20000.00);

		GregorianCalendar c = new GregorianCalendar();
		c.setTime(new Date());
		XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);

		customer.setDateOfBirth(date2);

		Address address = new Address("McArthur", "Dallas", "TX", "USA");
		customer.getAddress().add(address);
		Address address2 = new Address("FargoDome", "Fargo", "ND", "USA");
		customer.getAddress().add(address2);

		PaymentMethod payM1 = new PaymentMethod(12345, "Anuj Shrestha", date2, date2, "CREDIT");
		PaymentMethod payM2 = new PaymentMethod(56789, "Anuj Shrestha", date2, date2, "DEBIT");
		customer.getPaymentMethod().add(payM1);
		customer.getPaymentMethod().add(payM2);

		return customer;
	}
}