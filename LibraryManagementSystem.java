import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

// Abstract class representing a LibraryItem
abstract class LibraryItem {
    protected String title;

    public LibraryItem(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    // Abstract method to be implemented by subclasses
    public abstract String getDetails();
}

// Book class inheriting from LibraryItem
class Book extends LibraryItem {
    private String author;

    public Book(String title, String author) {
        super(title);
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String getDetails() {
        return "Book: " + title + ", Author: " + author;
    }
}

// Exception for handling login errors
class LoginException extends Exception {
    public LoginException(String message) {
        super(message);
    }
}

public class LibraryManagementSystem extends JFrame {
    // Initialize Swing components
    private JFrame loginFrame;
    private JLabel usernameLabel;
    private JTextField usernameField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;

    private JLabel titleLabel;
    private JTextField bookTitleField;
    private JLabel authorLabel;
    private JTextField authorField;
    private JButton addButton;
    private JButton viewButton;
    private JButton logoutButton;

    private boolean isAdminLoggedIn;
    private ArrayList<LibraryItem> libraryItemList;

    public LibraryManagementSystem() {
        // Set up the login JFrame
        loginFrame = new JFrame();
        loginFrame.setTitle("Login");
        loginFrame.setSize(300, 200);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setLayout(new FlowLayout());

        // Create login components
        usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");

        // Add login components to login JFrame
        loginFrame.add(usernameLabel);
        loginFrame.add(usernameField);
        loginFrame.add(passwordLabel);
        loginFrame.add(passwordField);
        loginFrame.add(loginButton);

        // Initialize libraryItemList
        libraryItemList = new ArrayList<>();

        // Add ActionListener to the loginButton
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    login();
                } catch (LoginException loginException) {
                    JOptionPane.showMessageDialog(null, loginException.getMessage());
                }
            }
        });

        // Display the login JFrame
        loginFrame.setVisible(true);
    }

    // Method to handle login and setup main JFrame
    private void login() throws LoginException {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Validate admin credentials
        if (username.equals("admin") && password.equals("admin123")) {
            isAdminLoggedIn = true;
            JOptionPane.showMessageDialog(null, "Admin logged in successfully!");
        } else if (username.equals("user") && password.equals("user123")) {
            isAdminLoggedIn = false;
            JOptionPane.showMessageDialog(null, "User logged in successfully!");
        } else {
            isAdminLoggedIn = false;
            throw new LoginException("Invalid credentials. Please try again.");
        }

        loginFrame.dispose(); // Close the login JFrame

        // Set up the main JFrame
        setTitle("Library Management System");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Create main components
        titleLabel = new JLabel("Enter Book Title:");
        bookTitleField = new JTextField(20);
        authorLabel = new JLabel("Enter Author:");
        authorField = new JTextField(20);
        addButton = new JButton("Add Book");
        viewButton = new JButton("View Books");
        logoutButton = new JButton("Logout");

        // Add main components to main JFrame
        add(titleLabel);
        add(bookTitleField);
        add(authorLabel);
        add(authorField);
        add(addButton);
        add(viewButton);
        add(logoutButton);

        // Add ActionListener to the addButton
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (isAdminLoggedIn) {
                    String bookTitle = bookTitleField.getText();
                    String author = authorField.getText();
                    // Add book to the library
                    libraryItemList.add(new Book(bookTitle, author));
                    JOptionPane.showMessageDialog(null, "Book added successfully!");
                    bookTitleField.setText(""); // Reset the input field
                    authorField.setText(""); // Reset the input field
                } else {
                    JOptionPane.showMessageDialog(null, "Oops! You can not add books. \nPlease log in as admin.");
                }
            }
        });

        // Add ActionListener to the viewButton
        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (libraryItemList.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "No items in the library.");
                } else {
                    StringBuilder items = new StringBuilder();
                    for (LibraryItem item : libraryItemList) {
                        items.append(item.getDetails()).append("\n");
                    }
                    JOptionPane.showMessageDialog(null, items.toString());
                }
            }
        });

        // Add ActionListener to the logoutButton
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isAdminLoggedIn = false;
                JOptionPane.showMessageDialog(null, "Logged out successfully!");
                // Clear input fields
                bookTitleField.setText("");
                authorField.setText("");
                // Remove previous components from the main JFrame
                getContentPane().removeAll();
                // Show login frame
                loginFrame.setVisible(true);
            }
        });

        // Display the main JFrame
        setVisible(true);
    }

    public static void main(String[] args) {
        new LibraryManagementSystem();
    }
}
