import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.TreeSet;
import java.util.function.Predicate;

public class LibraryManagementSystem {
    private TreeSet<Book> libraryBooks; //sorted by book name
    private HashMap<String, User> readers;
    private TreeSet<Book> borrowedBooks; //sorted by return date

    public LibraryManagementSystem() {
        libraryBooks = new TreeSet<>();
        readers = new HashMap<>();
        borrowedBooks = new TreeSet<>((b1,b2) -> b1.getReturnDate().compareTo(b2.getReturnDate()));
    }

    public LibraryManagementSystem(Collection<Book> books) throws ValidatorException {
        this();
        Validator.checkObject(books, "books");
        libraryBooks = new TreeSet<>(books);
    }

    public void addBook(Book book) throws LibraryException {
        if (!libraryBooks.add(book)) {
            throw new LibraryException("book not found");
        }
    }

    public void lend(Book book, User user, LocalDate returnDate) throws LibraryException {
        if (!libraryBooks.remove(book)) {
            throw new LibraryException("book not in library");
        }
        book.lendTo(user, returnDate);
    }

    public Collection<Book> getBooksFromUser(String readerID) throws ValidatorException {
        Validator.checkString(readerID, "readerID");
        User user = readers.get(readerID);
        Validator.checkObject(user, "user not found");
        return user.getBorrowedBooks();
    }

    public Collection<Book> getBorrowedBooksByReturnDate() {
        return borrowedBooks;
    }

    public Collection<Book> getAllBooks() {
        return libraryBooks;
    }

    public Collection<Book> getStoredBooksPublishedAfter(int year) throws ValidatorException {
        Validator.checkNumber(year, 0, 9999, "published year");
        return libraryBooks.stream().filter(x -> x.getYear() > year).toList();
    }

    public Collection<Book> getStoredBooksByPages() {
        return libraryBooks
                .stream()
                .sorted((b1, b2) -> Integer.compare(b1.getPages(), b2.getPages()))
                .toList();
    }

    public int sumAllStoredPages() {
        return libraryBooks
                .stream()
                .mapToInt(b -> b.getPages())
                .sum();
    }

    public Collection<Book> filterByGenre(String genre) throws ValidatorException {
        Validator.checkString(genre, "genre");
        return libraryBooks
                .stream()
                .filter(b -> b.getGenre().equals(genre))
                .toList();
    }

    public double getAverageRatingsForGenre(String genre) throws ValidatorException, NoSuchElementException {
        Validator.checkString(genre, "genre");
        return libraryBooks
                .stream()
                .filter(b -> b.getGenre().equals(genre))
                .mapToDouble(b -> b.getRating())
                .average()
                .getAsDouble();
    }

    public HashMap<String, Double> getAverageRatingsByGenre() {
        HashMap<String, Double> genreToSum = new HashMap<>();
        HashMap<String, Integer> genreToCount = new HashMap<>();
        for (Book b : this.libraryBooks) {
            Double currentSum = genreToSum.get(b.getGenre());
            Integer currentCount = genreToCount.get(b.getGenre());
            if (currentSum == null) {
                genreToSum.put(b.getGenre(), b.getRating());
                currentCount = 0;
            }
            genreToCount.put(b.getGenre(), currentCount+1);
        }
        HashMap<String, Double> genreToAverage = new HashMap<>();
        for (String genre : genreToSum.keySet()) {
            double sum = genreToSum.get(genre);
            int count = genreToCount.get(genre);
            genreToAverage.put(genre, sum/count);
        }
        return genreToAverage;
    }

    public Collection<Book> getBestRatedBooks(int count) throws ValidatorException {
        Validator.checkNumber(count, 0, Integer.MAX_VALUE, "count");
        return libraryBooks
                .stream()
                .sorted((b1,b2) -> -Double.compare(b1.getRating(), b2.getRating())) //Note the inversion
                .limit(count)
                .toList();
    }
    
    public Collection<String> getAuthorsWithMostBooks() {
        HashMap<String, Integer> authorToBookCount = new HashMap<>();
        for (Book b : libraryBooks) {
            Integer fetchAttempt = authorToBookCount.get(b.getAuthor());
            int currentCount = 0;
            if (fetchAttempt != null) {
                currentCount = fetchAttempt;
            }
            authorToBookCount.put(b.getAuthor(), currentCount+1);
        }
        return authorToBookCount
                .entrySet()
                .stream()
                .sorted((e1, e2) -> -Integer.compare(e1.getValue(), e2.getValue())) //note inversion
                .map(e -> e.getKey())
                .toList();
    }

    public Collection<Book> getBooksByRating() {
        return libraryBooks
                .stream()
                .sorted((b1,b2) -> -Double.compare(b1.getRating(), b2.getRating())) //note the inversion (sort decreasing)
                .toList();
    }

    public Collection<Book> getBooksByCustom(Predicate<Book> filter, Comparator<Book> comparator) throws ValidatorException {
        Validator.checkObject(filter, "filter");
        Validator.checkObject(comparator, "comparator");
        return libraryBooks
                .stream()
                .filter(filter)
                .sorted(comparator)
                .toList();
    }

}
