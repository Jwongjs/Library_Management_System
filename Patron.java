import java.util.*;

public class Patron extends User
{
    private double fineAmount;
    ArrayList <BorrowedBook> borrowedBookList;
    ArrayList <ReservedBook> reservedBookList;

    public Patron()
    {
        super();   
        fineAmount = 0;    
    }

    public Patron(String ID, String name, String pw)
    {
        super(ID, name, pw);
    }

    public Patron(ArrayList<Book> bookList, ArrayList<Patron> patronList)
    {
        super(bookList, patronList);
        initializeBookLists();     
    }

    public void initializeBookLists()
    {
        for(Patron p: patronList)
        {
            p.reservedBookList = new ArrayList<> ();
            p.borrowedBookList = new ArrayList<> ();
        }
    }

    public void setFineAmount(double fineAmount)
    {
        this.fineAmount += fineAmount;
    }

    public void resetFineAmount()
    {
        this.fineAmount = 0;
    }

    public double getFineAmount()
    {
        return fineAmount;
    }

    public void reserveBook(String pName)
    {
        viewLibraryBooks();
        System.out.println("Please enter the title of the book you would like to borrow");
        String borrowTitle = sc.nextLine().trim();
        boolean reserveSuccess = false;
        boolean multipleCopies = false;

        for (Patron p : patronList) 
        {
            if (pName.equals(p.getName()))
            {
                for (int z = 0; z < bookList.size(); z++) 
                {

                    for (int a = 0; a < p.reservedBookList.size(); a++) 
                    {
                        if (borrowTitle.equalsIgnoreCase(p.reservedBookList.get(a).getTitle())) 
                        {
                            System.out.println("You can only borrow one copy of each book");
                            multipleCopies = true;

                        }
                    }
                    if (multipleCopies) break;

                    else if (borrowTitle.equalsIgnoreCase(bookList.get(z).getTitle()) && bookList.get(z).getVacant() && p.reservedBookList.size() < 5 && !multipleCopies)
                    {
                        ReservedBook r = new ReservedBook();
                        r.setID(bookList.get(z).getID());
                        r.setTitle(bookList.get(z).getTitle());
                        r.setAuthor(bookList.get(z).getAuthor());

                        p.reservedBookList.add(r);

                        System.out.println("\n--You have successfully reserved '"+bookList.get(z).getTitle()+"'!--");
                        bookList.get(z).setQuantity(bookList.get(z).getQuantity() - 1);
                        if((bookList).get(z).getQuantity()==0){
                            bookList.get(z).setVacant(false);
                        }
                     
                        reserveSuccess = true;
                        break;
                    } 
                    else if (borrowTitle.equalsIgnoreCase(bookList.get(z).getTitle()) && !bookList.get(z).getVacant())
                    {
                        System.out.println("Book is currently unavailable");
                        break;
                    }
                    else if (p.reservedBookList.size() > 5) {
                        System.out.println("You have exceeded the limit for borrowing any more books");
                        break;
                    } 
                    else if (z == bookList.size() - 1 && !borrowTitle.equalsIgnoreCase(bookList.get(z).getTitle()) && !reserveSuccess && !multipleCopies) 
                    {

                        System.out.println("The book is not available in this library");

                    }
                }

            }
        }
    }

    public boolean viewPatronBorrowedBooks(String pName)
  {
      boolean existingBook = false;
      System.out.println("==============================================================================================\n"+
      String.format("|%-35s%-22s%-35s|"," ", "CURRENT BOOKS BORROWED", " "));
      System.out.println("==============================================================================================\n"+
      String.format("| %-7s | %-30s | %-20s | %-11s | %-10s |", "ID", "TITLE", "AUTHOR", "BORROW DATE", "DUE DATE")+"\n==============================================================================================");
      for (Patron p: patronList)
      {
            if (pName.equalsIgnoreCase(p.getName()))
            {         
                if(!p.borrowedBookList.isEmpty())
                {
                    for (BorrowedBook bb: p.borrowedBookList)
                    {
                        System.out.println(bb);
                    }
                    existingBook = true;
                }
            }
      }
      if(!existingBook)
      {
        System.out.println("**There are currently no borrowed books for the patron.**");
        return existingBook;
      }
      else 
        return existingBook;
  }

    public boolean displayFines(String pName)
    {
        for(Patron p: patronList)
        {
            if(p.getName().equalsIgnoreCase(pName))
            {
                if(p.getFineAmount()>0)
                {        
                    System.out.println("\nTotal fine amount:\nRM"+ String.format("%.2f", p.getFineAmount()));
                    return true;
                }
                else if(p.getFineAmount() == 0)
                {
                    System.out.println("The following patron has no fines."+"\n");
                    break;
                }
            }
        }                   
        return false;
    }

  public void Homepage(String pName)
  {
      int code = 0;

      do
      {
          System.out.println("\n|PATRON HOMEPAGE|\n"+"================");
          System.out.println("1) View Library Books");
          System.out.println("2) Search Books");
          System.out.println("3) Display Fines");
          System.out.println("4) View Borrowed Books");
          System.out.println("5) Reserve Book");
          System.out.println("6) Exit");
          System.out.println("Enter the number based on the function you desired to use: ");

          if(sc.hasNextInt())//Check if the next input has integer value before calling nextInt()
          {
              code = sc.nextInt();
              sc.nextLine();
              switch(code)
              {
                  case 1:
                  {
                        boolean libraryFilter = true;
                        viewLibraryBooks();
                        filterPrompt(libraryFilter);
                        break;
                  }
                  case 2:
                  {
                      searchBooks();
                      break;
                  }
                  case 3:
                  {
                      displayFines(pName);
                      break;
                  }
                  case 4:
                  {
                      viewPatronBorrowedBooks(pName);
                      break;
                  }
                  case 5:
                  {
                      reserveBook(pName);
                      break;
                  }
                  case 6:
                  {
                      System.out.println("\n--You have exited Patron Homepage. Back to System Menu--");
                      break;
                  }
                  default:
                      System.out.println("Invalid Option.");
              }
          }
          else
          {
              System.out.println("You have entered an invalid value.");
              sc.nextLine(); //Consume the newline character
          }
      }
      while(code != 6);
  }

    public void login()
  {   
      int confirm = 0;
      int input = 0;
      boolean existingUser = false;
    
      do {
            System.out.println("\n==============================\n"+
                                "|1- Login|2- Register|3- Exit|\n"+
                                "==============================");

            if(sc.hasNextInt())
            {
                input = sc.nextInt();
                sc.nextLine();

                switch(input)
                {
                    case 1:
                    {
                        boolean loginP=false;
                        int pTries=0;
                        String pName;
                        System.out.println("\n|PATRON LOGIN PAGE|"+"\n===================");
                        do
                        {
                            super.verifyLogin();
                            for(int b=0;b<patronList.size(); b++)
                            {
                                if(ID.equals(patronList.get(b).getID()) && pw.equals(patronList.get(b).getPw()))
                                {
                                    pName = patronList.get(b).getName();
                                    System.out.println("You have successfully logged in!");
                                    loginP = true;
                                    Homepage(pName);
                                    break;
                                }
                            }
                            if(!loginP)
                            {
                                System.out.println("Invalid patron ID or password.(Maximum attempt is 3) | Attempt: "+ (pTries + 1) +"\n");
                            }
                            pTries++;
                            if(pTries == 3)
                            {
                                System.out.println("You have reached the maximum number of tries. Returning to the System menu.\n");
                                break;
                            }
                        }
                        while(!loginP);
                        break;
                    }
                    case 2:
                    {

                      do{
                        System.out.println("\n|REGISTRATION PAGE|"+"\n===================");
                        System.out.println("Enter new ID: ");
                        ID = sc.nextLine();
                        existingUser=false;
                        while(ID.contains(" ") || ID.length() != 7)
                        {
                            System.out.println("Invalid value. ID cannot contain spaces or have more than 7 characters.\n");
                            System.out.println("Enter again:");
                            ID = sc.nextLine();
                        }

                        System.out.println("Enter new name: ");
                        name = sc.nextLine();
                                 //trim() is to delete leading and trailing space of string
                        while (name.trim().isEmpty()) {
                            System.out.println("Name cannot be empty. Enter name again: ");
                            name = sc.nextLine();
                        }
                        for (Patron p : patronList) {
                            if (name.equalsIgnoreCase(p.getName()) && ID.equalsIgnoreCase(p.getID()))
                            {
                                System.out.println("Error, both name and ID already exists in the system as a registered user, please try again.\n ");
                                existingUser = true;
                                break;
                            }

                            else if (name.equalsIgnoreCase(p.getName()))
                            {
                                System.out.println("Error, name already exists in the system as a registered user, please try again.\n ");
                                existingUser = true;
                                break;
                            }

                             if (ID.equalsIgnoreCase(p.getID()))
                            {
                                System.out.println("Error, ID already exists in the system as a registered user, please try again.\n ");
                                existingUser = true;
                                break;
                            }
                        }

                        if(!existingUser){
                            break;
                        }
                      }while(existingUser);

                        System.out.println("Enter new password: ");
                        pw = sc.nextLine();
                        while(pw.contains(" "))
                        {
                            System.out.println("Invalid value. Password cannot contain spaces.\n");
                            System.out.println("Enter password:");
                            pw = sc.nextLine();
                        }
                      do{
                      System.out.println("Confirm to register as a new patron? \n");
                      System.out.println("|1-yes|2-no|");

                      if(sc.hasNextInt())
                      {
                        confirm =sc.nextInt();
                        sc.nextLine();
                        
                        if(confirm==1){
                          patronList.add(new Patron(ID, name, pw));
                           System.out.println("Success! \n");
                          break;}
                        else if(confirm == 2){
                            System.out.println("You have abort the registration process.");
                          }
                        else System.out.println("Inavlid integer.");
                      }
                      else
                        System.out.println("Invalid value.");
                        sc.nextLine();
                      }while(confirm != 2);
                      break;
                    }
                    case 3:
                    {
                      System.out.println("You have exited login and registration page.\n");
                      break;
                    }
                    default:
                    System.out.println("Invalid integer.");
                }
            }
            else
            {
                System.out.println("Invalid value.");
                sc.nextLine();
            }
          }while(input !=3);
    }

  public String toString()
  {
      return String.format("| %-7s | %-30s |", getID(), getName())+"\n--------------------------------------------";
  }
}