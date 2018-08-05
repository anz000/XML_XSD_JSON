package anuz.maven.xsd2xml.app;

import java.io.File;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import anuz.maven.xsd2xml.Address;
import anuz.maven.xsd2xml.Customer;
import anuz.maven.xsd2xml.PaymentMethod;

public class App {

	public static void main(String[] args) throws DatatypeConfigurationException, JAXBException {
		marshal();
		unmarshal();
	}

	private static void unmarshal() throws DatatypeConfigurationException, JAXBException {
		File file = new File("/home/anz/eclipse-workspace/xsd2xml/src/main/resources/Customer.xml");
		JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

		Customer Customer = (Customer) jaxbUnmarshaller.unmarshal(file);
		System.out.println(Customer);
		System.out.println(Customer.getName());
	}

	private static void marshal() throws DatatypeConfigurationException {
		try {

			File file = new File("/home/anz/eclipse-workspace/xsd2xml/src/main/resources/Customer.xml");
			JAXBContext jaxbContext = JAXBContext.newInstance(Customer.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			Customer Customer = createCustomer();
			jaxbMarshaller.marshal(Customer, file);
			// jaxbMarshaller.marshal(Customer, System.out);

		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

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
