import java.util.TreeSet;

public class User {
    private String name;
    private String readerID;
    TreeSet<Book> borrowedBooks;

    public User(String name, String readerID) throws ValidatorException {
        this.name = Validator.checkString(name, "username");
        this.readerID = Validator.checkString(readerID, "reader ID");
        //use return date as sorting criteria
        this.borrowedBooks = new TreeSet<>((b1, b2) -> (b1.getReturnDate().compareTo(b2.getReturnDate()))); 
    }

    public String getName() {
        return name;
    }
    public String getReaderID() {
        return readerID;
    }
    public TreeSet<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void addBook(Book book) {
        this.borrowedBooks.add(book);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        User otherUser = (User) other;
        return this.getName().equals(otherUser.getName()) &&
                this.getReaderID().equals(otherUser.getReaderID());
    }

    @Override
    public String toString() {
        return "User" + 
                "{name=" + name +
                ",readerID=" + readerID +
                "}";
    }
}
