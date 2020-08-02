package ExpenseTracker;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * The account class can store, execute and undo commands
 * @author Daniele Palazzo
 *
 */

public class Account {
	private String user_name;
	private List<ICommand> commands;
	private String prehistory = ""; //previous history (normally from file)
	
	/**
	 * Constructs an Account with a user name
	 * @param user_name The user name of the account's owner. 
	 */
	
	public Account(String user_name) {
		commands = new ArrayList<>();
		this.user_name = user_name;
	}
	
	/**
	 * Tries to execute the command object that is passed in. Failing that, it will print an error message
	 * @param command The command to be executed and stored
	 */
	
	public void execute_command(ICommand command) {
		try {
			command.doit();
			commands.add(command);
		} catch (Wallet_exception e) {
			System.out.println("Error: " + e.getMessage() + "\n");
		}
	}
	
	
	/**
	 * Undoes the last command stored by execute_command
	 */
	public void undo_last() {
		if (commands.size() > 0) {
			commands.get(commands.size()-1).undoit();
			commands.remove(commands.size()-1);
		}
	}
	
	/**
	 * Stores command history to be prepended to what the the print_history method 
	 * will print out
	 * @param s A string assumed to contain previous command history
	 */
	public void prepend_history(String s) {
		prehistory += s;
	}
	
	/**
	 * Returns the user name of the Account owner
	 * @return Returns the user name of the Account owner
	 */
	
	public String get_user_name() {
		return user_name;
	}
	
	/**
	 * Prints the command history (what commands have been executed)
	 * @param os The destination stream where the history will be sent to.
	 * @throws IOException
	 */
	
	public void print_history(OutputStreamWriter os) throws IOException {
		os.write(String.format("%-26s", "Command")+String.format("%-26s", "Amount") + '\n');
		os.write(prehistory);
		for (ICommand c : commands) {
		os.write(String.format("%-26s", c.description())+String.format("%-26s", c.get_amount()) + '\n');
		}
		os.flush();
	}
}
