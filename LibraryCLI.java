import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Scanner;
import java.util.function.Predicate;

public class LibraryCLI {
    private LibraryManagementSystem libraryManagementSystem;
    private Scanner scanner;

    public LibraryCLI(LibraryManagementSystem libraryManagementSystem) {
        this.libraryManagementSystem = libraryManagementSystem;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        loadBooksFromCSV("books.csv");

        boolean running = true;

        while (running) {
            System.out.println("\n--- Bücherverwaltungssystem ---");
            System.out.println("1. Buch hinzufügen");
            System.out.println("2. Alle Bücher anzeigen");
            System.out.println("3. Bücher nach Jahr filtern");
            System.out.println("4. Bücher nach Seitenanzahl sortieren");
            System.out.println("5. Gesamtanzahl der Seiten berechnen");
            System.out.println("6. Buch ausleihen");
            System.out.println("7. Buch zurückgeben");
            System.out.println("8. Ausgeliehene Bücher eines Nutzers anzeigen");
            System.out.println("9. Alle ausgeliehenen Bücher anzeigen, sortiert nach Rückgabedatum");
            System.out.println("10. Bücher nach Genre filtern");
            System.out.println("11. Durchschnittliche Bewertung pro Genre berechnen");
            System.out.println("12. Top-bewertete Bücher anzeigen");
            System.out.println("13. Autoren mit den meisten Büchern anzeigen");
            System.out.println("14. Bücher nach Bewertung sortieren");
            System.out.println("15. Gefilterte und sortierte Liste der Bücher anzeigen");
            System.out.println("16. Programm beenden");
            System.out.print("Bitte wählen Sie eine Option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addBook();
                    break;
                case 2:
                    displayAllBooks();
                    break;
                case 3:
                    filterBooksByYear();
                    break;
                case 4:
                    sortBooksByPages();
                    break;
                case 5:
                    calculateTotalPages();
                    break;
                case 6:
                    borrowBook();
                    break;
                case 7:
                    returnBook();
                    break;
                case 8:
                    displayBorrowedBooksByUser();
                    break;
                case 9:
                    displayAllBorrowedBooks();
                    break;
                case 10:
                    filterBooksByGenre();
                    break;
                case 11:
                    calculateAverageRatingPerGenre();
                    break;
                case 12:
                    displayTopRatedBooks();
                    break;
                case 13:
                    displayAuthorsWithMostBooks();
                    break;
                case 14:
                    sortBooksByRating();
                    break;
                case 15:
                    filterAndSortBooks();
                    break;
                case 16:
                    running = false;
                    break;
                default:
                    System.out.println("Ungültige Option. Bitte versuchen Sie es erneut.");
            }
        }
    }

    private void loadBooksFromCSV(String filePath) {
        ArrayList<Book> books = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Book b = new Book(values[0], values[1], Integer.parseInt(values[2]), Integer.parseInt(values[3]), values[4], Double.parseDouble(values[5]), false, null);
                books.add(b);
            }
            System.out.println("Bücher aus CSV-Datei geladen.");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        } catch (ValidatorException e) {
            e.printStackTrace();
            System.out.println("Invalid CSV content");
        }
        try {
            this.libraryManagementSystem = new LibraryManagementSystem(books);    
        } catch (ValidatorException e) {
            System.out.println("could not create library: " + e.getMessage());
        }
    }

    private void addBook() {
        System.out.print("Titel: ");
        String title = scanner.nextLine();
        System.out.print("Autor: ");
        String author = scanner.nextLine();
        System.out.print("Veröffentlichungsjahr: ");
        int year = scanner.nextInt();
        System.out.print("Anzahl der Seiten: ");
        int pages = scanner.nextInt();
        System.out.print("Genre: ");
        String genre = scanner.next();
        System.out.print("Bewertung: ");
        double rating = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        Book book = null;
        try {
            book = new Book(title, author, year, pages, genre, rating, false, null);
            libraryManagementSystem.addBook(book);
            System.out.println("Buch hinzugefügt!");
        } catch (LibraryException e) {
            System.out.println("Kann nicht Buch hinzufügen: " + e.getMessage());    
        }
    }

    private void displayAllBooks() {
        System.out.println("Stored books:");
        for (Book b : libraryManagementSystem.getAllBooks()) {
            System.out.println(b);
        }
    }

    private void filterBooksByYear() {
        System.out.println("Stored books by year:");
        try {
            for (Book b : libraryManagementSystem.getBooksByCustom(x -> true, (b1,b2)->Integer.compare(b1.getYear(), b2.getYear()))) {
                System.out.println(b);
            }    
        } catch (ValidatorException e) {
            System.out.println("uh oh, this should never happen");
        }
        
    }

    private void sortBooksByPages() {
    }

    private void calculateTotalPages() {
    }

    private void borrowBook() {
    }

    private void returnBook() {
    }

    private void displayBorrowedBooksByUser() {
    }

    private void displayAllBorrowedBooks() {
    }

    private void filterBooksByGenre() {
    }

    private void calculateAverageRatingPerGenre() {
    }

    private void displayTopRatedBooks() {
    }

    private void displayAuthorsWithMostBooks() {
    }

    private void sortBooksByRating() {
    }

    private void filterAndSortBooks() {
        System.out.println("Filtern nach benutzerdefinierten Kriterien:");
        System.out.println("1. Nach Jahr");
        System.out.println("2. Nach Seitenanzahl");
        System.out.println("3. Nach Bewertung");
        System.out.print("Wählen Sie ein Filterkriterium: ");
        int filterChoice = scanner.nextInt();
        System.out.print("Wählen Sie 1 für > oder 2 für <: ");
        int comparison = scanner.nextInt();
        System.out.print("Geben Sie den Wert ein: ");
        double filterValue = scanner.nextDouble();
        scanner.nextLine();  // Consume newline

        Predicate<Book> filter;
        switch (filterChoice) {
            case 1:
                filter = (b -> b.getYear()>filterValue);
                break;
            case 2:
                filter = (b -> b.getPages()>filterValue);
                break;
            case 3:
                filter = (b -> b.getRating()>filterValue);
                break;
            default:
                System.out.println("Ungültige Auswahl.");
                return;
        }
        Predicate<Book> endPred = filter;
        if (comparison == 2) { //if less than, invert the criterion
            endPred = x -> !filter.test(x);
        }


        System.out.println("Sortieren nach benutzerdefinierten Kriterien:");
        System.out.println("1. Nach Titel");
        System.out.println("2. Nach Jahr");
        System.out.println("3. Nach Seitenanzahl");
        System.out.println("4. Nach Bewertung");
        System.out.print("Wählen Sie ein Sortierkriterium: ");
        int sortChoice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        Comparator<Book> sorter = null;
        switch (sortChoice) {
            case 1:
                sorter = (b1,b2) -> b1.getTitle().compareTo(b2.getTitle());
                break;
            case 2:
                sorter = (b1,b2) -> Integer.compare(b1.getYear(), b2.getYear());
                break;
            case 3:
                sorter = (b1,b2) -> Integer.compare(b1.getPages(), b2.getPages());
                break;
            case 4:
                sorter = (b1,b2) -> Double.compare(b1.getRating(), b2.getRating());
                break;
            default:
                System.out.println("Ungültige Auswahl.");
                return;
        }

        try {
            Collection<Book> result = libraryManagementSystem.getBooksByCustom(endPred, sorter);
            result.forEach(System.out::println);   
        } catch (ValidatorException e) {
            System.out.println("uh oh, this shouldn't be happening either");
            return;
        }
    }

    public static void main(String[] args) {
        LibraryManagementSystem libraryManagementSystem = new LibraryManagementSystem();
        LibraryCLI libraryCLI = new LibraryCLI(libraryManagementSystem);
        libraryCLI.run();
    }
}
