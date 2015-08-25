
package databeans;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.genericdao.PrimaryKey;

import util.Util;

import com.google.gson.Gson;

@PrimaryKey("id")
public class Customer {
	private int id;
	private String userName = null;
	private String firstName = null;
	private String lastName = null;
	private String addrLine1 = null;

	private String addrLine2 = null;
	private String city = null;
	private String state = null;
	private String zip = null;
	private long amount = 0;

	private String hashedPassword = "*";
	private int salt = 0;

	public String getAddrLine1() {
		return addrLine1;
	}

	public void setAddrLine1(String addrLine1) {
		this.addrLine1 = addrLine1;
	}

	public String getAddrLine2() {
		return addrLine2;
	}

	public void setAddrLine2(String addrLine2) {
		this.addrLine2 = addrLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public boolean checkPassword(String password) {
		return hashedPassword.equals(hash(password));
	}

	public int getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public int getSalt() {
		return salt;
	}

	public int hashCode() {
		return userName.hashCode();
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFirstName(String s) {
		firstName = s;
	}

	public void setLastName(String s) {
		lastName = s;
	}

	public void setHashedPassword(String x) {
		hashedPassword = x;
	}

	public void setPassword(String s) {
		salt = newSalt();
		hashedPassword = hash(s);
	}

	public void setSalt(int x) {
		salt = x;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

	private String hash(String clearPassword) {
		if (salt == 0)
			return null;

		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA1");
		} catch (NoSuchAlgorithmException e) {
			throw new AssertionError(
					"Can't find the SHA1 algorithm in the java.security package");
		}

		String saltString = String.valueOf(salt);

		md.update(saltString.getBytes());
		md.update(clearPassword.getBytes());
		byte[] digestBytes = md.digest();

		String digestStr = bytesToHex(digestBytes);

		return digestStr;
	}

	private String bytesToHex(byte[] digestBytes) {
		// Format the digest as a String
		StringBuffer digestSB = new StringBuffer();
		for (int i = 0; i < digestBytes.length; i++) {
			int lowNibble = digestBytes[i] & 0x0f;
			int highNibble = (digestBytes[i] >> 4) & 0x0f;
			digestSB.append(Integer.toHexString(highNibble));
			digestSB.append(Integer.toHexString(lowNibble));
		}
		String digestStr = digestSB.toString();
		return digestStr;
	}

	private int newSalt() {
		Random random = new Random();
		return random.nextInt(8192) + 1; // salt cannot be zero
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public double getAmountValue() {
		return Util.truncate((double) amount / 100, 100);
	}

}
