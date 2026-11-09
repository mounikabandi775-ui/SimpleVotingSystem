import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;

public class Main {
    static ArrayList<Voter> voters = new ArrayList<>();
    static ArrayList<Candidate> candidates = new ArrayList<>();
    static HashSet<String> voted = new HashSet<>();
    static JFrame frame;
    static JPanel root, center;

    static class Voter {
        String name, id, password;
        Voter(String n, String i, String p) {
            name = n;
            id = i;
            password = p;
        }
    }

    static class Candidate {
        String name, party;
        int votes = 0;
        Candidate(String n, String p) {
            name = n;
            party = p;
        }
    }

    public static void main(String[] args) {
        candidates.add(new Candidate("Ravi Kumar", "People Party"));
        candidates.add(new Candidate("Priya", "Unity Party"));
        candidates.add(new Candidate("Suresh", "National Party"));
        SwingUtilities.invokeLater(() -> createWindow());
    }

    static void createWindow() {
        frame = new JFrame("Simple Voting System");
        frame.setSize(1000, 680);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        root = new GradientPanel();
        root.setLayout(new BorderLayout(20, 20));
        root.setBorder(new EmptyBorder(20, 35, 20, 35));

        JLabel heading = new JLabel("SIMPLE VOTING SYSTEM");
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        heading.setForeground(Color.WHITE);

        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        header.setOpaque(false);
        header.add(heading);
        center = new JPanel(new BorderLayout());
        center.setOpaque(false);
        root.add(header, BorderLayout.NORTH);
        root.add(center, BorderLayout.CENTER);
        frame.add(root);
        showHome();
        frame.setVisible(true);
    }

    static class GradientPanel extends JPanel {
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setPaint(new GradientPaint(
                0, 0, new Color(20, 35, 75),
                getWidth(), getHeight(), new Color(90, 55, 145)
            ));
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    static JButton button(String name) {
        JButton b = new JButton(name);
        b.setFont(new Font("Arial", Font.BOLD, 15));
        b.setForeground(Color.WHITE);
        b.setBackground(new Color(55, 75, 160));
        b.setFocusPainted(false);
        b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        b.setPreferredSize(new Dimension(230, 45));
        return b;
    }

    static JPanel makeCard(String title, String subtitle) {
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(35, 45, 35, 45));

        JLabel t = new JLabel(title);
        t.setFont(new Font("Arial", Font.BOLD, 27));
        t.setForeground(new Color(30, 45, 90));
        t.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel s = new JLabel(subtitle);
        s.setFont(new Font("Arial", Font.PLAIN, 15));
        s.setForeground(Color.GRAY);
        s.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(t);
        card.add(Box.createVerticalStrut(12));
        card.add(s);
        card.add(Box.createVerticalStrut(30));
        return card;
    }

    static void showPage(JPanel card) {
        center.removeAll();
        JPanel holder = new JPanel(new GridBagLayout());
        holder.setOpaque(false);
        holder.add(card);
        center.add(holder, BorderLayout.CENTER);
        center.revalidate();
        center.repaint();
    }

    static void showHome() {
        JPanel card = makeCard(
            "WELCOME TO VOTING SYSTEM",
            "Your Vote • Your Voice • Your Future"
        );
        JButton admin = button("ADMIN LOGIN");
        JButton register = button("VOTER REGISTRATION");
        JButton login = button("VOTER LOGIN");
        JButton exit = button("EXIT");

        addButton(card, admin);
        addButton(card, register);
        addButton(card, login);
        addButton(card, exit);

        admin.addActionListener(e -> adminLogin());
        register.addActionListener(e -> registerVoter());
        login.addActionListener(e -> voterLogin());
        exit.addActionListener(e -> System.exit(0));
        showPage(card);
    }

    static void addButton(JPanel panel, JButton b) {
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(b);
        panel.add(Box.createVerticalStrut(13));
    }

    static void adminLogin() {
        String user = JOptionPane.showInputDialog(frame, "Admin Username:");
        if (user == null) return;

        JPasswordField pf = new JPasswordField();
        int option = JOptionPane.showConfirmDialog(
            frame, pf, "Admin Password",
            JOptionPane.OK_CANCEL_OPTION
        );
        if (option != JOptionPane.OK_OPTION) return;

        String pass = new String(pf.getPassword());
        if (user.equals("admin") && pass.equals("admin123")) {
            adminDashboard();
        } else {
            JOptionPane.showMessageDialog(frame, "Invalid Admin Details!");
        }
    }

    static void adminDashboard() {
        JPanel card = makeCard(
            "ADMIN DASHBOARD",
            "Manage candidates and election results"
        );
        JButton add = button("ADD CANDIDATE");
        JButton view = button("VIEW CANDIDATES");
        JButton results = button("VIEW RESULTS");
        JButton back = button("BACK TO HOME");

        addButton(card, add);
        addButton(card, view);
        addButton(card, results);
        addButton(card, back);

        add.addActionListener(e -> addCandidate());
        view.addActionListener(e -> showCandidates());
        results.addActionListener(e -> showResults());
        back.addActionListener(e -> showHome());
        showPage(card);
    }

    static void addCandidate() {
        JTextField name = new JTextField();
        JTextField party = new JTextField();
        Object[] fields = {
            "Candidate Name:", name,
            "Party Name:", party
        };

        int result = JOptionPane.showConfirmDialog(
            frame, fields, "Add Candidate",
            JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {
            if (name.getText().trim().isEmpty()
                    || party.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Enter all details!");
                return;
            }
            candidates.add(new Candidate(
                name.getText().trim(), party.getText().trim()
            ));
            JOptionPane.showMessageDialog(
                frame, "Candidate Added Successfully!"
            );
        }
    }

    static void showCandidates() {
        JPanel card = makeCard(
            "CANDIDATE DETAILS",
            "Registered election candidates"
        );

        for (Candidate c : candidates) {
            JLabel item = new JLabel(
                "● " + c.party.substring(0, 1).toUpperCase()
                + "   " + c.name + " - " + c.party
            );
            item.setFont(new Font("Arial", Font.BOLD, 16));
            item.setForeground(new Color(40, 55, 100));
            item.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(item);
            card.add(Box.createVerticalStrut(18));
        }

        JButton back = button("BACK");
        addButton(card, back);
        back.addActionListener(e -> adminDashboard());
        showPage(card);
    }

    static void registerVoter() {
        JTextField name = new JTextField();
        JTextField id = new JTextField();
        JPasswordField pass = new JPasswordField();

        Object[] fields = {
            "Voter Name:", name,
            "Voter ID:", id,
            "Create Password:", pass
        };

        int result = JOptionPane.showConfirmDialog(
            frame, fields, "Voter Registration",
            JOptionPane.OK_CANCEL_OPTION
        );
        if (result != JOptionPane.OK_OPTION) return;

        String voterId = id.getText().trim();
        if (name.getText().trim().isEmpty()
                || voterId.isEmpty()
                || pass.getPassword().length == 0) {
            JOptionPane.showMessageDialog(frame, "Fill all details!");
            return;
        }

        for (Voter v : voters) {
            if (v.id.equalsIgnoreCase(voterId)) {
                JOptionPane.showMessageDialog(
                    frame, "Voter ID Already Exists!"
                );
                return;
            }
        }

        voters.add(new Voter(
            name.getText().trim(),
            voterId,
            new String(pass.getPassword())
        ));
        JOptionPane.showMessageDialog(
            frame, "Registration Successful!"
        );
    }

    static void voterLogin() {
        JTextField id = new JTextField();
        JPasswordField pass = new JPasswordField();
        Object[] fields = {
            "Voter ID:", id,
            "Password:", pass
        };

        int result = JOptionPane.showConfirmDialog(
            frame, fields, "Voter Login",
            JOptionPane.OK_CANCEL_OPTION
        );
        if (result != JOptionPane.OK_OPTION) return;

        for (Voter v : voters) {
            if (v.id.equalsIgnoreCase(id.getText().trim())
                    && v.password.equals(new String(pass.getPassword()))) {
                if (voted.contains(v.id)) {
                    JOptionPane.showMessageDialog(
                        frame, "You have already voted!"
                    );
                } else {
                    votingPage(v);
                }
                return;
            }
        }
        JOptionPane.showMessageDialog(frame, "Invalid Voter Details!");
    }

    static void votingPage(Voter voter) {
        JPanel card = makeCard(
            "CAST YOUR VOTE",
            "Welcome " + voter.name + " - Select one candidate"
        );

        for (Candidate c : candidates) {
            JButton vote = button(
                "● " + c.party.substring(0, 1).toUpperCase()
                + "   " + c.name + " - " + c.party
            );
            addButton(card, vote);

            vote.addActionListener(e -> {
                int confirm = JOptionPane.showConfirmDialog(
                    frame, "Confirm your vote for " + c.name + "?",
                    "Confirm Vote", JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    c.votes++;
                    voted.add(voter.id);
                    JOptionPane.showMessageDialog(
                        frame, "Your vote submitted successfully!"
                    );
                    showHome();
                }
            });
        }

        JButton back = button("BACK");
        addButton(card, back);
        back.addActionListener(e -> showHome());
        showPage(card);
    }

    static void showResults() {
        JPanel card = makeCard(
            "ELECTION RESULTS",
            "Vote count and winner details"
        );
        int highest = 0;
        int total = 0;

        for (Candidate c : candidates) {
            highest = Math.max(highest, c.votes);
            total += c.votes;

            JLabel item = new JLabel(
                c.name + " (" + c.party + ") : " + c.votes + " votes"
            );
            item.setFont(new Font("Arial", Font.BOLD, 16));
            item.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(item);
            card.add(Box.createVerticalStrut(12));
        }

        JLabel count = new JLabel("Total Votes: " + total);
        count.setFont(new Font("Arial", Font.BOLD, 17));
        count.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(count);

        String winner = "No votes cast yet.";
        if (total > 0) {
            ArrayList<String> names = new ArrayList<>();
            for (Candidate c : candidates) {
                if (c.votes == highest) names.add(c.name);
            }
            winner = names.size() == 1
                ? "Winner: " + names.get(0)
                : "Tie: " + String.join(", ", names);
        }

        JLabel win = new JLabel(winner);
        win.setFont(new Font("Arial", Font.BOLD, 19));
        win.setForeground(new Color(35, 130, 75));
        win.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(Box.createVerticalStrut(20));
        card.add(win);

        JButton back = button("BACK TO DASHBOARD");
        addButton(card, back);
        back.addActionListener(e -> adminDashboard());
        showPage(card);
    }
}