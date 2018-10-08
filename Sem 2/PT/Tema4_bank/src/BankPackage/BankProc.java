package BankPackage;


public interface BankProc {
	/*
	 *@invariant the sum of all accounts must be equal to the sum in the bank
	 **/
	/*
	 * @pre name and surname must not be null
	 * @post number of persons must be bigger than the previous number of persons
	 * */
	public String addPerson(String name, String surname);

	/*
	 * @pre the person with pid must exist
	 * @post there has to be one more acccount in the hashmap
	 * */
	public String addAccount(int pid, int sum, int type, int years);
	
	/*
	 * @pre the person with the specified id must exist
	 * */
	public String editPerson(int id, String name, String surname);

	/*
	 * @pre the account with the specified id must exist
	 * */
	public String depositAccount(int sum, int id);
	
	/*
	 * @pre the account with the specified id must exist
	 * */
	public String withdrawAccount(int sum, int id);

	/*
	 * @pre the person with the specified id must exist
	 * @post there must be one less person in the list
	 * */
	public String deletePerson(int id);

	/*
	 * @pre the account with the specified id must exist
	 * @post there must be one less account in the HashMap
	 * */
	public String deleteAccount(int id);

}
