package BankPackage;

import AccountPackage.*;
import PersonPackage.*;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class Bank implements BankProc {
	private ArrayList<Person> persons;
	private HashMap<Integer, ArrayList<Account>> accounts;
	private int TotalSumInBank;

	public ArrayList<Person> getPersons() {
		return this.persons;
	}

	public Bank() {
		try {
			this.persons = this.deserializePerson();
			this.accounts = this.deserializeAccount();
			this.TotalSumInBank = getTotalSum();
		} catch (Exception e) {
			this.persons = new ArrayList<Person>();
			this.accounts = new HashMap<Integer, ArrayList<Account>>();
			this.TotalSumInBank = 0;
		}

	}

	private boolean isWellFormed() {
		if (TotalSumInBank == getTotalSum())
			return true;
		return false;
	}

	private int getTotalSum() {
		int sum = 0;
		for (Entry<Integer, ArrayList<Account>> e : accounts.entrySet())
			for (Account a : e.getValue())
				sum += a.getSum();
		return sum;
	}

	private int getAccountsCount() {
		int count = 0;
		for (Entry<Integer, ArrayList<Account>> e : accounts.entrySet())
			for (Account a : e.getValue())
				if (!a.getIsDeleted())
					count++;
		return count;
	}

	private int maxID() {
		int max = 0;
		for (Entry<Integer, ArrayList<Account>> e : accounts.entrySet())
			for (Account a : e.getValue())
				if (a.getId() > max)
					max = a.getId();
		return max;
	}

	public ArrayList<AccountForTable> getAccounts() {
		ArrayList<AccountForTable> aft = new ArrayList<AccountForTable>();
		for (Entry<Integer, ArrayList<Account>> e : accounts.entrySet()) {
			for (Account acc : e.getValue()) {
				Person p = findPersonByID(e.getKey().intValue());
				aft.add(new AccountForTable(p, acc));
			}
		}
		return aft;
	}

	public void serialize() {
		try {
			assert (isWellFormed());
			FileOutputStream fileOutPerson = new FileOutputStream("person.ser");
			ObjectOutputStream out1 = new ObjectOutputStream(fileOutPerson);
			out1.writeObject(persons);
			out1.close();
			fileOutPerson.close();
			FileOutputStream fileOutAccount = new FileOutputStream("account.ser");
			ObjectOutputStream out2 = new ObjectOutputStream(fileOutAccount);
			out2.writeObject(accounts);
			out2.close();
			fileOutAccount.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	public ArrayList<Person> deserializePerson() {
		try {
			FileInputStream fileIn = new FileInputStream("person.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			@SuppressWarnings("unchecked")
			ArrayList<Person> persons = (ArrayList<Person>) in.readObject();
			if (persons == null)
				persons = new ArrayList<Person>();
			in.close();
			fileIn.close();
			return persons;
		} catch (IOException i) {
			i.printStackTrace();
			return new ArrayList<Person>();
		} catch (ClassNotFoundException c) {
			System.out.println("Person class not found");
			c.printStackTrace();
			return new ArrayList<Person>();
		}
	}

	public HashMap<Integer, ArrayList<Account>> deserializeAccount() {
		try {
			FileInputStream fileIn = new FileInputStream("account.ser");
			ObjectInputStream in = new ObjectInputStream(fileIn);
			@SuppressWarnings("unchecked")
			HashMap<Integer, ArrayList<Account>> account = (HashMap<Integer, ArrayList<Account>>) in.readObject();

			if (account == null)
				account = new HashMap<Integer, ArrayList<Account>>();
			in.close();
			fileIn.close();
			return account;
		} catch (IOException i) {
			i.printStackTrace();
			return new HashMap<Integer, ArrayList<Account>>();
		} catch (ClassNotFoundException c) {
			System.out.println("Account class not found");
			c.printStackTrace();
			return new HashMap<Integer, ArrayList<Account>>();
		}
	}

	public String addPerson(Person p)
	{
		assert (isWellFormed());// invariant
		assert(p!=null);
		int count = persons.size();
		
		persons.add(p);
		assert (count + 1 == persons.size());// postcondition
		assert (isWellFormed());// invariant
		return "Person Added!";
	}
	
	public String addPerson(String name, String surname) {

		assert (isWellFormed());// invariant
		assert (name != null && surname != null);// precondition
		Person p = new Person(name, surname, false, this.persons.size());
		return addPerson(p);
	}

	public String addAccount(int pid, int sum, int type, int years) {
		assert (isWellFormed());// invariant
		Person p = findPersonByID(pid);
		assert (p != null && sum >= 0 && type % 2 == type && years > 0); //precondition
		int AccountCount = getAccountsCount();
		int id = maxID() + 1;
		if (type == 1) {
			this.TotalSumInBank += sum;
			Account a = new SpendingAccount(sum, id);
			a.addObserver(p);
			if (accounts.get(pid) != null)
				accounts.get(pid).add(a);
			else {
				accounts.put(pid, new ArrayList<Account>());
				accounts.get(pid).add(a);
			}
			assert (isWellFormed());// invariant
			assert (AccountCount + 1 == getAccountsCount());
			return "Account added";
		}
		Account a = new SavingAccount(id, 0.025, 1000, years);
		a.addObserver(p);
		if (accounts.get(pid) != null)
			accounts.get(pid).add(a);
		else {
			accounts.put(pid, new ArrayList<Account>());
			accounts.get(pid).add(a);
		}
		assert (AccountCount + 1 == getAccountsCount());
		assert (isWellFormed());
		return "Account added";
	}

	private Account findAccountByID(int id) {
		for (Entry<Integer, ArrayList<Account>> e : accounts.entrySet())
			for (Account a : e.getValue())
				if (a.getId() == id)
					return a;
		return null;
	}

	private Person findPersonByID(int id) {
		for (Person a : persons)
			if (a.getId() == id)
				return a;
		return null;
	}

	public String editPerson(int id, String name, String surname) {
		assert (isWellFormed());// invariant
		Person p = findPersonByID(id);
		assert (p != null && name!=null && surname!=null);// precondition
		p.setName(name);
		p.setSurname(surname);
		assert (isWellFormed());// invariant
		return "Person edited.";
	}

	public String depositAccount(int sum, int id) {
		assert (isWellFormed());// invariant

		Account c = findAccountByID(id);
		assert (c != null && sum>0);// precondition
		String ret = c.deposit(sum);
		if (ret.contains("has been added"))
			this.TotalSumInBank += sum;
		assert (isWellFormed());// invariant
		return ret;
	}

	public String withdrawAccount(int sum, int id) {
		assert (isWellFormed());// invariant
		Account c = findAccountByID(id);
		assert (c != null && sum > 0);// precondition
		String ret = c.withdraw(sum);
		if (ret.contains("has been withdrawn"))
			this.TotalSumInBank -= sum;
		assert (isWellFormed());// invariant
		return ret;
	}

	public String deletePerson(int id) {
		assert (isWellFormed());// invariant
		assert (id > 0);// precondition
		Person p = findPersonByID(id);
		p.setDeleted(true);
		for (Account a : accounts.get(id))
			deleteAccount(a);
		assert (isWellFormed());// invariant
		return "Person edited.";
	}

	public String deleteAccount(int id) {
		assert (isWellFormed());// invariant
		assert (id > 0);// precondition
		int count = getAccountsCount();
		Account c = findAccountByID(id);
		assert (c != null);// precondition
		String ret = c.delete();
		assert (count - 1 == getAccountsCount());// post
		assert (isWellFormed());// invariant
		return ret;
	}

	public String deleteAccount(Account a) {
		assert (isWellFormed());// invariant
		assert (a != null);// precondition
		String ret = a.delete();
		assert (isWellFormed());// invariant
		return ret;
	}
}
