package obserpattern;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

// Subject: UserProfile
class UserProfile {
    private String username;
    private String email;
    private List<ProfileObserver> observers = new ArrayList<>();

    // Register observers
    public void addObserver(ProfileObserver observer) {
        observers.add(observer);
    }

    // Remove observers
    public void removeObserver(ProfileObserver observer) {
        observers.remove(observer);
    }

    // Notify all observers of a change in the user profile
    public void notifyObservers() {
        for (ProfileObserver observer : observers) {
            observer.update(this);
        }
    }

    // Getter and Setter for user profile attributes
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
        notifyObservers(); // Notify observers whenever the username changes
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyObservers(); // Notify observers whenever the email changes
    }
}

// Observer Interface
interface ProfileObserver {
    void update(UserProfile userProfile);
}

// Concrete Observer 1: ProfileView
class ProfileView implements ProfileObserver {
    private JTextArea textArea;

    public ProfileView(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void update(UserProfile userProfile) {
        textArea.setText("Profile View Updated:\n");
        textArea.append("Username: " + userProfile.getUsername() + "\n");
        textArea.append("Email: " + userProfile.getEmail() + "\n");
    }
}

// Concrete Observer 2: ProfileDashboard
class ProfileDashboard implements ProfileObserver {
    private JTextArea textArea;

    public ProfileDashboard(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void update(UserProfile userProfile) {
        textArea.setText("Profile Dashboard Updated:\n");
        textArea.append("Username: " + userProfile.getUsername() + "\n");
        textArea.append("Email: " + userProfile.getEmail() + "\n");
    }
}

// Main Class to Test the Observer Pattern with GUI
public class Main {

    public static void main(String[] args) {
        // Create a new UserProfile object
        UserProfile userProfile = new UserProfile();

        // Create JFrame for the GUI
        JFrame frame = new JFrame("User Profile");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.setSize(400, 300);

        // Create text fields for username and email
        JTextField usernameField = new JTextField(20);
        JTextField emailField = new JTextField(20);

        // Create JTextArea to display updates from observers
        JTextArea profileViewArea = new JTextArea(5, 30);
        profileViewArea.setEditable(false);
        JTextArea profileDashboardArea = new JTextArea(5, 30);
        profileDashboardArea.setEditable(false);

        // Create and add observers to the userProfile
        ProfileView profileView = new ProfileView(profileViewArea);
        ProfileDashboard profileDashboard = new ProfileDashboard(profileDashboardArea);

        userProfile.addObserver(profileView);
        userProfile.addObserver(profileDashboard);

        // Add components to the frame
        frame.add(new JLabel("Username:"));
        frame.add(usernameField);
        frame.add(new JLabel("Email:"));
        frame.add(emailField);
        frame.add(new JLabel("Profile View:"));
        frame.add(new JScrollPane(profileViewArea));
        frame.add(new JLabel("Profile Dashboard:"));
        frame.add(new JScrollPane(profileDashboardArea));

        // Button to update user profile
        JButton updateButton = new JButton("Update Profile");

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Update the UserProfile based on the text fields
                userProfile.setUsername(usernameField.getText());
                userProfile.setEmail(emailField.getText());
            }
        });

        frame.add(updateButton);
        frame.setVisible(true);
    }
}
