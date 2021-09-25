public class Customer{
	private String CNIC, Name, Address, PhoneNumber;
	
	public Customer(String cnic, String name, String address, String phno) {
		CNIC = cnic;
		Name = name;
		Address = address;
		PhoneNumber = phno;
	}
	
	public String getName() {
		return Name;
	}
	
	public void setName(String name) {
		Name = name;
	}
	
	public String getCNIC() {
		return CNIC;
	}
	
	public String getAddress() {
		return Address;
	}
	
	public void setAddress(String address) {
		Address = address;
	}
	
	public String getPhoneNumber() {
		return PhoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}
	
	public void printCustomer() {
		System.out.println("Name: " + getName());
		System.out.println("Address: " + getAddress());
		System.out.println("Phone Number: " + getPhoneNumber());
	}
}