package ExpenseTracker;

import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * The Wallet class is a "state machine" that stores the user's current balance, savings and debt.
 * It changes state and behaviour based on those variables. 
 * @author Daniele Palazzo
 *
 */

public class Wallet {
	private final State has_balance;
	private final State low_balance;
	private final State has_saving_no_balance;
	private final State no_saving_no_balance;
	
	private State state;
	
	public double balance;
	public double savings;
	public double debt;
	
	/**
	 * Construct a Wallet object.
	 * @param balance Amount of money that is available to spend.
	 * @param savings Amount of money that is put away in savings.
	 * @param debt Amount of money owed (e.g. loans, credit etc.)
	 */
	public Wallet(double balance, double savings, double debt) {
		has_balance = new HasBalance(this);
		low_balance = new LowBalance(this);
		has_saving_no_balance = new HasSavingNoBalance(this);
		no_saving_no_balance = new NoSavingNoBalance(this);
		
		this.balance = balance;
		this.savings = savings;
		this.debt = debt;
		
		//set the state
		if (balance > 80) state = has_balance;
		else if (balance <= 80 && balance > 0) state = low_balance;
		else if (balance == 0 && savings > 0) state = has_saving_no_balance;
		else state = no_saving_no_balance;
	}
	
	/**
	 * Add money to the balance.
	 * @param amount The amount to add to the balance.
	 * @throws Invalid_amount_exception This is thrown if the amount is not right. (negative value)
	 */
	public void add_money(double amount) throws Invalid_amount_exception {
		state.add_money(amount);
	}
	
	/**
	 * Spend money from the wallet (decrease balance and/or savings or increase debt)
	 * This will never throw an Insufficient funds exception because the debt can always be increased.
	 * @param amount Amount to spend from the Wallet.
	 * @throws Invalid_amount_exception This is thrown if the amount is not right. (negative value)
	 */
	public void spend_money(double amount) throws Invalid_amount_exception {
		state.spend_money(amount);
	}
	
	/**
	 * Transfer money from the balance to the savings within the Wallet.  
	 * @param amount The amount to transfer. 
	 * @throws Insufficient_funds_exception This is thrown if the amount is greater than the available balance.
	 * @throws Invalid_amount_exception This is thrown if the amount is not right. (negative value)
	 */
	public void transfer_to_savings(double amount) throws Insufficient_funds_exception, Invalid_amount_exception {
		state.transfer_to_savings(amount);
	}
	/**
	 * Decrease debt using the Wallet balance.
	 * @param amount Amount to decrease debt by (from the balance).
	 * @throws No_debt_exception This is thrown if there is no debt to pay off
	 * @throws Invalid_amount_exception This is thrown if the amount is not right. 
	 * (e.g. amount is greater than debt)
	 * @throws Insufficient_funds_exception This is thrown if the amount is greater than the Wallet balance
	 */
	public void pay_off_debt_with_balance(double amount) throws No_debt_exception, Invalid_amount_exception, Insufficient_funds_exception {
		state.pay_off_debt_with_balance(amount);
	}
	
	/**
	 * Decrease debt using the Wallet savings.
	 * @param amount Amount to decrease debt by (from the savings).
	 * @throws No_debt_exception This is thrown if there is no debt to pay off.
	 * @throws Invalid_amount_exception This is thrown if the amount is not right. 
	 * (e.g. amount is greater than debt)
	 * @throws Insufficient_funds_exception This is thrown if the amount is greater than the Wallet savings
	 */
	public void pay_off_debt_with_savings(double amount) throws No_debt_exception, Invalid_amount_exception, Insufficient_funds_exception {
		state.pay_off_debt_with_savings(amount);
	}
	
	/**
	 * Get the current state.
	 * @return Return the current state.
	 */
	public State get_state() {
		return state;
	}
	/**
	 * Set the current state
	 * @param state State to set the Wallet to.
	 */
	public void set_state(State state) {
		this.state = state;
	}
	
	/**
	 * Get the has_balance state which is a possible internal Wallet state.
	 * @return Return the has_balance state
	 */
	public State has_balance_state() {
		return has_balance;
	}
	/**
	 * Get the low_balance state which is a possible internal Wallet state.
	 * @return Return the low_balance state
	 */
	public State low_balance_state() {
		return low_balance;
	}
	/**
	 * Get the has_saving_no_balance state which is a possible internal Wallet state.
	 * @return Return the has_saving_no_balance state
	 */
	public State has_saving_no_balance_state() {
		return has_saving_no_balance;
	}
	/**
	 * Get the no_saving_no_balance state which is a possible internal Wallet state.
	 * @return Return the no_saving_no_balance state
	 */
	public State no_saving_no_balance_state() {
		return no_saving_no_balance;
	}
	/**
	 * Print out the current Wallet information ( current balance, savings and debt)
	 * @param os The stream to print the information to
	 * @throws IOException
	 */
	public void print_info(OutputStreamWriter os) throws IOException {
		os.write("Your balance: " + this.balance + '\n' +
				 "Your savings: " + this.savings + '\n' +
				 "Your debt: " + this.debt + '\n');
		os.flush();
	}
}
