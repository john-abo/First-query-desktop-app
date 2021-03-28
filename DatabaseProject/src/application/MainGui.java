package application;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.List;

public class MainGui {

	protected Shell shell;
	private Text nameField;
	private Text localeField;
	private Text contactField;
	
	private Label Update;
	private List list;

	private static SQLManagement sqlMang;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		
		sqlMang = new SQLManagement("jdbc:mysql://localhost/contactlist","root","Pound_multiple_demonstration_watching");
		sqlMang.initializeConnection();
		
		try {
			MainGui window = new MainGui();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(450, 500);
		shell.setText("Manage Contact List");
		
		Label lblEnterMemberInfo = new Label(shell, SWT.NONE);
		lblEnterMemberInfo.setBounds(10, 10, 183, 15);
		lblEnterMemberInfo.setText("Enter Member info (from request)");
		
		Label LabelName = new Label(shell, SWT.NONE);
		LabelName.setBounds(20, 31, 55, 15);
		LabelName.setText("Name:");
		
		nameField = new Text(shell, SWT.BORDER);
		nameField.setBounds(30, 52, 394, 21);
		
		Label LabelLocale = new Label(shell, SWT.NONE);
		LabelLocale.setBounds(20, 79, 404, 15);
		LabelLocale.setText("Locale: (Don't make this a drop down, gonna be impossible)");
		
		localeField = new Text(shell, SWT.BORDER);
		localeField.setBounds(30, 100, 394, 21);
		
		Label LabelContactNumber = new Label(shell, SWT.NONE);
		LabelContactNumber.setBounds(20, 127, 404, 15);
		LabelContactNumber.setText("Contact number: (format \"xxx xxx xxx\")");
		
		contactField = new Text(shell, SWT.BORDER);
		contactField.setBounds(30, 148, 394, 21);
		
		Update = new Label(shell, SWT.NONE);
		Update.setBounds(199, 175, 225, 15);
		
		Button EnterData = new Button(shell, SWT.NONE);
		EnterData.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Doing the thing (Enter data)");
				
				System.out.println("Insert params");
				System.out.println("TEST: " + nameField.getText() + ", " + localeField.getText() + ", " + contactField.getText());
				
				System.out.println(sqlMang.insertBreth(nameField.getText(), localeField.getText(), contactField.getText()));
				
				Update.setText("Inserting member");
			}
		});
		EnterData.setBounds(10, 175, 75, 25);
		EnterData.setText("Enter Data");
		
		Button SearchName = new Button(shell, SWT.NONE);
		SearchName.addSelectionListener(new SelectionAdapter() {
			@SuppressWarnings("unused")
			@Override
			public void widgetSelected(SelectionEvent e) {
				ArrayList<String> output = null;
				
				System.out.println("Doing the thing (Search)");
				
				Update.setText("Searching for member");
				list.removeAll();
				
				//Query results gotta be stored in a String ArrayList, then printed. Lets hope this works
				//This is where the meat and potatoes of the project are at
				output = sqlMang.searchBrethName(nameField.getText());
				
				if (output != null && !output.isEmpty()) {
					for (String a : output) {
						list.add(a);
					}
				} else {
					list.add("No members found");
				}
			}
		});
		SearchName.setBounds(91, 175, 102, 25);
		SearchName.setText("Search by name");
		
		Button btnExportDatabase = new Button(shell, SWT.NONE);
		btnExportDatabase.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Doing the thing (Exporting)");
				
				Update.setText("Exporting table (Work in Progress)");
			}
		});
		btnExportDatabase.setBounds(308, 226, 116, 25);
		btnExportDatabase.setText("Export Database");
		
		//the list will be cut off? tho I'm not sure shy I even have a search
		list = new List(shell, SWT.BORDER);
		list.setBounds(10, 257, 414, 194);
		
		Button btnSearchByLocale = new Button(shell, SWT.NONE);
		btnSearchByLocale.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
					ArrayList<String> output = null;
				
				System.out.println("Doing the thing (Search)");
				
				Update.setText("Searching for Locales");
				list.removeAll();
				
				//Query results gotta be stored in a String ArrayList, then printed. Lets hope this works
				//This is where the meat and potatoes of the project are at
				output = sqlMang.searchBrethLocale(localeField.getText());
				
				if (output != null && !output.isEmpty()) {
					for (String a : output) {
						list.add(a);
					}
				} else {
					list.add("No such locale found");
				}
			}
		});
		btnSearchByLocale.setBounds(91, 206, 102, 25);
		btnSearchByLocale.setText("Search by locale");
	}
}
