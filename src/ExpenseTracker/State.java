package ExpenseTracker;

/**
 * Wallet state
 * @author Daniele Palazoo
 *
 */
public interface State {
	/**
	 * Add money to the Wallet (balance)
	 * @param amount The amount to be added to the Wallet
	 * @throws Invalid_amount_exception This is thrown if the amount is not right (e.g. negative value)
	 */
	void add_money(double amount) throws Invalid_amount_exception;
	/**
	 * Spend money from the Wallet
	 * @param amount The amount to take away from the Wallet
	 * @throws Invalid_amount_exception This is thrown if the amount is not right (e.g. negative value)
	 */
	void spend_money(double amount) throws Invalid_amount_exception;
	/**
	 * Transfer money from the Wallet balance to the Wallet savings
	 * @param amount The amount to transfer
	 * @throws Insufficient_funds_exception This is thrown if the amount is greater than the Wallet balance
	 * @throws Invalid_amount_exception This is thrown if the amount is not right (e.g. negative value)
	 */
	void transfer_to_savings(double amount) throws Insufficient_funds_exception, Invalid_amount_exception;
	/**
	 * Reduce Wallet debt using the Wallet balance
	 * @param amount The amount to reduce debt by
	 * @throws No_debt_exception There is no debt to reduce. Debt=0
	 * @throws Invalid_amount_exception The amount is not right (e.g. the amount is greater than the debt)
	 * @throws Insufficient_funds_exception The amount is greater than the Wallet balance
	 */
	void pay_off_debt_with_balance(double amount) throws No_debt_exception, Invalid_amount_exception, Insufficient_funds_exception;
	/**
	 * Reduce Wallet debt using the Wallet savings
	 * @param amount The amount to reduce debt by
	 * @throws No_debt_exception There is no debt to reduce. Debt=0
	 * @throws Invalid_amount_exception The amount is not right (e.g. the amount is greater than the debt)
	 * @throws Insufficient_funds_exception The amount is greater than the Wallet savings
	 */
	void pay_off_debt_with_savings(double amount) throws No_debt_exception, Invalid_amount_exception, Insufficient_funds_exception;
}
