import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Book {
    private String ID;
    private String title;
    private String author; 
    private int quantity;   
    private boolean vacant;
    private LocalDate borrowDate, returnDate, dueDate; 
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy"); //format three different dates

    public Book()
    {   
        this(" ", " ", " ", 0, true);
        borrowDate = LocalDate.now();
        returnDate = LocalDate.now();
        dueDate = LocalDate.now();
    }

    public Book(String a, String b, String c, int e, Boolean f)
    {   
        this.ID = a;
        this.title = b;
        this.author = c;
        setQuantity(e);
        setAvailability(f);
    }

    public Book(String ID, String title, LocalDate returnDate, LocalDate dueDate) //Fine Class
    {
        this.ID = ID;
        this.title = title;
        this.returnDate = returnDate;
        this.dueDate = dueDate;
    }

    public Book(String ID, String title, String author) //Reserved Book Class
    {
        this.ID = ID;
        this.title = title;
        this.author = author;
    }

    public Book(String ID, String title, String author, LocalDate borrowDate, LocalDate dueDate) //Borrowed Book Class
    {
        this.ID = ID;
        this.title = title;
        this.author = author;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
    }

    public void setQuantity(int x)
    {
        quantity = x;
    }

    public void setAvailability(Boolean y)
    {
        vacant = y;
    }

    public void setID (String ID)
    {
        this.ID=ID;
    }

    public void setTitle (String title)
    {
        this.title=title;
    }

    public void setAuthor (String author)
    {
        this.author=author;
    }

    public void setVacant(Boolean vacant){
        this.vacant=vacant;
    }

    public String getID()
    {
        return ID;
    }

    public String getTitle()
    {
        return title;
    }

    public String getAuthor()
    {
        return author;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public boolean getVacant()
    {
      return vacant;
    }

    public String getBorrowDate()
    {
        return borrowDate.format(dateFormat);
    }

    public String getReturnDate()
    {
        return returnDate.format(dateFormat);
    }

    public String getDueDate()
    {
        return dueDate.format(dateFormat);
    }

    public String toString()
    {
       return String.format("| %-7s | %-30s | %-20s | %-8s | %-6s |", getID(), getTitle(), getAuthor(), getQuantity(), getVacant()) 
        + "\n---------------------------------------------------------------------------------------";
    }
}

