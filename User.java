import java.util.*;

public class User //superclass
{   
    Scanner sc;
    protected String ID;
    protected String name;
    protected String pw;
    boolean filterSuccess;

    ArrayList<Book> bookList;
    ArrayList<Patron> patronList;

    public User()
    {   
        sc = new Scanner(System.in);
        filterSuccess = false;
    }

    public User(String ID, String name, String pw)
    {
        this.ID = ID;
        this.name = name;
        this.pw = pw;
    }

    public User(ArrayList<Book> bookList, ArrayList<Patron> patronList)
    {
        this();
        this.bookList = bookList;
        this.patronList = patronList;
    }

    public String getID()
    {
        return ID;
    }

    public String getName()
    {
        return name;
    }

    public String getPw()
    {
        return pw;
    }

    public void verifyLogin()
    {   
        System.out.println("Enter ID: ");
        ID = sc.nextLine();
        System.out.println("Enter Password: ");
        pw = sc.nextLine();
    }

    public String searchBooks()
    {
        String search;
        boolean bookFound = false;

        System.out.println("Please enter book name (Press 0 to cancel):");
        do
        {
            search = sc.nextLine();

            if(search.equals("0"))
            {
                System.out.println("Search cancelled.");
                break;
            }
            else  
            {
                for (Book b: bookList) 
                {
                    if (search.equalsIgnoreCase(b.getTitle())) 
                    {   
                        System.out.println(String.format("%-37s%-12s", " ", "BOOK DETAILS"));
                        System.out.println("=======================================================================================\n"
                            + String.format("| %-7s | %-30s | %-20s | %-8s | %-6s |", "ID", "TITLE", "AUTHOR", "QUANTITY", "VACANT")
                                        + "\n=======================================================================================");
                        System.out.println(b);
                        bookFound = true;
                        break;
                    }
                }
            }
            if(!bookFound)
                System.out.println("Book not found. Enter book name again: ");
        }while(!search.equals("0") && !bookFound);
        return search;
    }

    public void viewLibraryBooks()
    {
        libraryListHeader();
        for(Book b: bookList)
        {
            System.out.println(b);
        }
    }

    public void filter() 
    {  
        filterSuccess = false;
        int input = 0;
        do
        { 
          System.out.println("\n|1- Available Books|2- Unavailable Books|3- First Letter of the title|4- Exit|");
          if(sc.hasNextInt())
          {
            input = sc.nextInt();
            sc.nextLine(); //consume newline character

            switch(input)
            {  
                case 1:
              {
                libraryListHeader();
                vacantOrNotVacant(true);
                break;
              } 
              case 2:
              {
                libraryListHeader();
                vacantOrNotVacant(false);
                break;
              } 
              case 3:
              {
                char letter = ' ';
                System.out.println("Enter the first character of the book's title:");
                do 
                {
                  String userInput = sc.nextLine().trim();
                  if (userInput.length() == 1) 
                  {
                      letter = userInput.charAt(0);
                      break;
                  } 
                  else 
                  {
                      System.out.println("Invalid input. Please enter a single character:");
                  }
                } while (true);

                libraryListHeader();
                for (Book b : bookList) 
                {
                    if (Character.toUpperCase(letter) == b.getTitle().charAt(0)) 
                    {
                        System.out.println(b);
                        filterSuccess = true;
                    }
                }

                if (!filterSuccess) 
                {
                    System.out.println("**No books found with the given character.**\n");
                }
              break;
              }
              case 4:
              {
                System.out.println("\n--You have exited the filter menu.--\n");
                break;
              }
              default:
                System.out.println("Invalid option.");
            }
          }  
          else 
          {
            System.out.println("Invalid value.");
            sc.nextLine(); //consume newline character
          }
      }while(input != 4);
    }

    public void vacantOrNotVacant(boolean vacant)
    {
      for (Book b: bookList) 
      {
          if (b.getVacant() == vacant) 
          {
              System.out.println(b);
              filterSuccess = true;
          } 
      }
      if(!filterSuccess)
      {
        System.out.println("All books are available right now.\n");
      }
    }

  public void filterP()
  {   
    filterSuccess = false; 
    int input = 0;

    do
    {
      System.out.println("\n|1- First Letter of patron's name|2- Exit|"); //string format on hold
      if(sc.hasNextInt())
      {
        input = sc.nextInt();
        sc.nextLine(); //consume newline character

        switch(input)
        {  
            case 1:
          {
              char letter = ' ';
              System.out.println("Enter the first letter of the patron's name:");
              do 
              {
                String userInput = sc.nextLine().trim();
                if (userInput.length() == 1) 
                {
                    letter = userInput.charAt(0);
                    break;
                } 
                else 
                {
                    System.out.println("\nInvalid input. Please enter a single character:");
                }
              } while (true);

              patronListHeader();
              for (Patron p : patronList) 
              {
                  if (Character.toUpperCase(letter) == p.getName().charAt(0)) 
                  {
                      System.out.println(p);
                      filterSuccess = true;
                  }
              }

              if (!filterSuccess) 
              {
                  System.out.println("**No patrons found with the given letter.**\n");
              }
              break;
          } 
          case 2:
          {
            System.out.println("\n--You have exited the filter menu.--");
            break;
          } 
          default:
            System.out.println("Invalid option entered.");
        }
      }  
      else 
      {
        System.out.println("Invalid value.");
        sc.nextLine(); //consume newline character
      }
    }while(input != 2);
  }

  public void filterPrompt(boolean libraryFilter)
  {
      int no = 0;                
      do
      { 
          System.out.println("Press 1 to filter the list, 2 to exit:");                       
          if(sc.hasNextInt())
          {
              no = sc.nextInt();
              sc.nextLine(); //consume newline character

              if(no == 1)
              {
                  if(libraryFilter)
                  {
                      filter();
                  }
                  else
                      filterP();
                  break;
              }
              else if(no == 2)
              {
                  System.out.println("\n--You have chosen to exit.--");
              }
              else
                  System.out.println("Invalid integer.");
          }
          else
          {
              System.out.println("Invalid value.");
              sc.nextLine(); //consume newline character
          }
      }while(no != 2);         
  }

    public void libraryListHeader()
   {
     System.out.println("\n=======================================================================================");
     System.out.println(String.format("|%-34s%-17s%-34s|", " ", "LIBRARY BOOK LIST", " "));
     System.out.println("=======================================================================================\n"
             + String.format("| %-7s | %-30s | %-20s | %-8s | %-6s |",
                             "ID", "TITLE", "AUTHOR", "QUANTITY", "VACANT")
             + "\n=======================================================================================");
   }

    public void patronListHeader()
   {  
     System.out.println("\n============================================\n"+
        String.format("|%-16s%-11s%-15s|", " ", "PATRON LIST", " "));
     System.out.println("============================================\n"
     +String.format("| %-7s | %-30s |", "ID", "NAME")
     +"\n============================================");
   }
}
