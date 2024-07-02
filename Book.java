import java.time.LocalDate;

public class Book implements Comparable<Book>{
    private String title;
    private String author;
    private int year;
    private int pages;
    private String genre;
    private double rating;
    private boolean borrowed;
    private LocalDate returnDate;

    public Book(String title, String author, int year, int pages, String genre,
            double rating, boolean borrowed, LocalDate returnDate) throws ValidatorException {
        this.title = Validator.checkString(title, "title");
        this.author = Validator.checkString(author, "author");
        this.year = Validator.checkNumber(year, 0, 9999, "year");
        this.pages = Validator.checkNumber(pages, 0, 99999, "pages");
        this.genre = Validator.checkString(genre, "genre");
        this.rating = Validator.checkNumber(rating, 1.0, 5.0, "rating");
        this.borrowed = borrowed;
        this.returnDate = returnDate;
        if (borrowed) {
            Validator.checkObject(returnDate, "returnDate missing when book is borrowed");
        }
    }

    public void lendTo(User user, LocalDate returnDate) throws ValidatorException {
        Validator.checkObject(user, "user");
        Validator.checkObject(returnDate, "return date");
        if (borrowed) {
            throw new ValidatorException("can't lend a borrowed book");
        }
        this.borrowed = true;
        this.returnDate = returnDate;
        user.addBook(this);
    }

    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public int getYear() {
        return year;
    }
    public int getPages() {
        return pages;
    }
    public String getGenre() {
        return genre;
    }
    public double getRating() {
        return rating;
    }
    public boolean isBorrowed() {
        return borrowed;
    }
    public LocalDate getReturnDate() {
        return returnDate;
    }

    @Override
    public int compareTo(Book other) {
        return this.title.compareTo(other.title);
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        Book otherBook = (Book) other;
        return this.getAuthor().equals(otherBook.getAuthor()) &&
                this.getGenre().equals(otherBook.getGenre()) &&
                this.getPages() == otherBook.getPages() &&
                this.getRating() == otherBook.getRating() &&
                this.getReturnDate() == otherBook.getReturnDate() &&
                this.getTitle() == otherBook.getTitle() &&
                this.getYear() == otherBook.getYear();
    }

    @Override
    public String toString() {
        return "Book" + 
                "{author=" + author +
                ", genre=" + genre +
                ", pages=" + pages +
                ", rating=" + rating  + 
                ", returnDate=" + returnDate +
                ", title=" + title + 
                ", year=" + year
                + "}";
    }
}