import java.time.LocalDate;

public class BorrowedBook extends Book
{
    public BorrowedBook()
    {

    }

    public BorrowedBook(String ID, String title, String author, LocalDate borrowDate, LocalDate dueDate)
    {
        super(ID, title, author, borrowDate, dueDate);
    }

    public String toString()
    {
        return String.format("| %-7s | %-30s | %-20s | %-11s | %-10s |", getID(), getTitle(), getAuthor(), getBorrowDate(), getDueDate())+"\n----------------------------------------------------------------------------------------------"; //94
    }
}
