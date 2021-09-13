public class Customer{
	String CNIC, Name, Address, PhoneNumber;
	
	public Customer(String cnic, String name, String address, String phno) {
		CNIC = cnic;
		Name = name;
		Address = address;
		PhoneNumber = phno;
	}
	
	public void printCustomer() {
		System.out.println("Name: " + Name);
		System.out.println("Address: " + Address);
		System.out.println("Phone Number: " + PhoneNumber);
	}
}