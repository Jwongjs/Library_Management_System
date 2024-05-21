public class ReservedBook extends Book
{

     public ReservedBook()
     {

     }
     
     public ReservedBook(String ID, String title, String author)
     {
          super(ID, title, author);
     }

     public String toString()
     {
          return String.format("| %-7s | %-30s | %-20s |", getID(), getTitle(), getAuthor())+"\n-------------------------------------------------------------------"; //67
     }
}
