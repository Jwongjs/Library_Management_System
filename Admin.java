import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

public class Admin extends User implements AdminList
{  
    Patron p;

    public Admin()
    {
        super();
        p = new Patron();
    }

    public Admin(ArrayList<Book> bookList, ArrayList<Patron> patronList)
    {        
        super(bookList, patronList);
        p = new Patron(bookList, patronList);
    }

    public void login() //lowercase all method names
    {   boolean login = false;
        int tries = 0;

        System.out.println("\n|ADMIN LOGIN PAGE|"+"\n==================");
        do
        {   
            super.verifyLogin();
            for(int x = 0; x<3; x++)
            {
                if(ID.equals(A_ID[x]) && pw.equals(A_PW[x]))
                {
                    System.out.println("You have successfully logged in!");
                    login = true;
                    Homepage();
                    break;
                }
            }
            if(!login)
            {
                System.out.println("\nInvalid Admin ID or password.(Maximum attempt is 3)|Attempt: "+ (tries+1));
            }
            tries++;
            if(tries == 3)
            {
                System.out.println("\nYou have reached the maximum number of tries. Returning to the System menu.\n");
                break;
            }
        }while(!login);
    }

    public void Homepage()
    {
        int code = 0;

        do
        {
        System.out.println("\n|ADMIN HOMEPAGE|\n"+"================");
        System.out.println("1) Manage Book");
        System.out.println("2) Manage Patron");
        System.out.println("3) Exit");
        System.out.println("Enter the number based on the function you desired to use: ");

        if(sc.hasNextInt())//Check if the next input has integer value before calling nextInt()
            {
                code = sc.nextInt();
                sc.nextLine();
                switch(code)
                {   
                    case 1: 
                    {
                        manageBooks();
                        break;
                    }
                    case 2:                       
                    { 
                        managePatrons();
                        break;
                    }        
                    case 3:
                    {
                        System.out.println("\n--You have exited Admin Homepage. Back to System Menu--");
                        break;
                    }
                    default:
                        System.out.println("Invalid Option.\n");
                }
            }
            else 
            {
                System.out.println("You have entered an invalid value.\n");
                sc.nextLine(); //Consume the newline character
            }
        }
          while(code != 3);
    }

    public void manageBooks()
    {           
        int code = 0;
        boolean removeSuccess = false;

        do
        {
        System.out.println("\n|MANAGE LIBRARY BOOKS|\n======================");
        System.out.println("1) View Library Books");
        System.out.println("2) Search Book");
        System.out.println("3) Add books");
        System.out.println("4) Exit");
        System.out.println("Enter the number of the desired function: ");

        if(sc.hasNextInt())//Check if the next input has integer value before calling nextInt()
            {
                code = sc.nextInt();
                sc.nextLine(); //consume the newline character
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
                        int no = 0;
                        String bName;

                        bName = searchBooks();
                        do
                        {
                            System.out.println(String.format("\n%-17s%-41s"," ","|1- Edit Quantity|2- Remove Book|3- Exit|"));
                            if(sc.hasNextInt())
                            {
                                no = sc.nextInt();
                                sc.nextLine(); //consume the newline characters
                                switch(no)
                                {
                                    case 1: 
                                    {
                                        editQuantity(bName);
                                        break;
                                    }
                                    case 2:
                                    {
                                        removeSuccess = removeBook(bName);
                                        break;
                                    }
                                    case 3:
                                    {
                                        System.out.println("\n--Going back to Manage Library Books--");
                                        break;
                                    }
                                    default:
                                        System.out.println("Invalid input.\n");
                                }
                            }
                            else
                            {
                                System.out.println("Please type in an integer value.\n");
                                sc.nextLine(); //Consume the newline character
                            }
                        }while(no != 3 && !removeSuccess);
                        break;
                    }       
                    case 3: 
                    {
                        addBook();
                        break;
                    }
                    case 4:
                    {
                        System.out.println("\n--You have exited the Manage Library Books page. Back to Admin Homepage--");
                        break;
                    }
                    default:
                        System.out.println("Invalid Option.\n");
                }
            }
            else 
            {
                System.out.println("You have entered an invalid value.\n");
                sc.nextLine(); //Consume the newline character
            }
        }while(code != 4);
    }

  public void addBook()
  {
      int code = 0, i = 0;
      String ID, title, author;
      int quantity = 0;
      boolean existingBook = false;

      do
      {   
          do
          {
              System.out.println("\nPlease enter the details (Book no. " + (i+1) +"):");
              System.out.println("ID (6 characters): ");
              ID = sc.nextLine();
              while(ID.contains(" ") || ID.length() != 6 || ID.trim().isEmpty())
              {
                  System.out.println("ID is either empty, contain spaces or not having exactly 6 characters. Enter again: ");
                  ID = sc.nextLine();
              }

              System.out.println("\nTitle: ");
              title = sc.nextLine();
              while (title.trim().isEmpty()) {
                  System.out.println("Title name cannot be empty. Enter again: ");
                  title = sc.nextLine();
              }

              System.out.println("\nAuthor: ");
              author = sc.nextLine();
              while (author.trim().isEmpty()) {
                  System.out.println("Author name cannot be empty. Enter again: ");
                  author = sc.nextLine();
              }
              for (Book b : bookList) {
                  if (title.equalsIgnoreCase(b.getTitle()) && ID.equalsIgnoreCase(b.getID()))
                  {
                      System.out.println("A book with the following ID and title has already existed, please try again.\n ");
                      existingBook = true;
                      break;
                  }

                  else if (title.equalsIgnoreCase(b.getTitle()))
                  {
                      System.out.println("A book with the following title has already existed, please try again.\n ");
                      existingBook = true;
                      break;
                  }

                   if (ID.equalsIgnoreCase(b.getID()))
                  {
                      System.out.println("A book with the following ID has already existed, please try again.\n ");
                      existingBook = true;
                      break;
                  }
              }

              if(!existingBook)
              {
                  break;
              }
          }while(existingBook);
          System.out.println("\nQuantity: ");
          do 
          {
              if(sc.hasNextInt()) 
              {
                  quantity = sc.nextInt();
                  if(quantity <= 0)
                  {
                      System.out.println("The amount did not meet the minimum requirements. Addition of the following Book has been terminated.\n");
                      break;
                  }
                  else if(quantity > 5)
                  {
                      System.out.println("The amount has exceeded the maximum quantity. Addition of the following book has been terminated.\n");
                      break;
                  }
                  else
                  {
                      bookList.add(new Book(ID, title, author, quantity, true));
                      i++;
                      System.out.println("Book '"+title+"' has been successfully added!\n");
                      viewLibraryBooks();
                      break;
                  }   
              }
              else
              {
                  System.out.println("Invalid input. Please enter again:");
                  sc.nextLine(); // Consume the invalid input
              }
          } while (quantity != sc.nextInt());

          System.out.println("\nEnter 1 to continue and 0 to stop: ");
          do
          {
              while(!sc.hasNextInt())
              {
                  System.out.println("Invalid input. Please try again: ");
                  sc.nextLine();
              }
              code = sc.nextInt();
          }while(code != 0 && code != 1);

          sc.nextLine(); //consume the newline character
          if(code == 0)
          {
              System.out.println(i+" separate books has been added to the library.");
          }
      }while(code != 0);  
  }

  public void editQuantity(String bName) {
      int quan = 0;
      boolean editSuccess = false;

      System.out.println("Edit the quantity of the following book (Minimum: 1 | Maximum: 5):");
      do {
          if (sc.hasNextInt()) 
          {
              quan = sc.nextInt();
              sc.nextLine();
              if (quan <= 0) 
              {
                  System.out.println("Minimum number has not been met. Please try again:");
              } 
              else if (quan > 5) 
              {
                  System.out.println("Maximum number has been exceeded. Please try again:");
              } 
              else 
              {
                  for (Book b : bookList) 
                  {
                      if (b.getTitle().equalsIgnoreCase(bName)) 
                      {
                          b.setQuantity(quan);
                          editSuccess = true;
                          break;
                      }
                  }
                  viewLibraryBooks();
                  System.out.println("The quantity of the following book has been edited to " + quan + ".\n");
                  break;
              }
          } 
          else 
          {
              System.out.println("Invalid value. Enter again:");
              sc.nextLine(); // consume the newline character
          }
      } while (!editSuccess);
  }

    public boolean removeBook(String bName)
    {
        String ID;
        boolean deleteSuccess = false;
        System.out.println("Please enter the ID of the following book to confirm deletion (Press -999 to cancel)):");
        while(!deleteSuccess)
        {
            ID = sc.next();

            if(ID.equals("-999"))
            {
                System.out.println("You have aborted the process of delete.\n");
                break;
            }

            for(int i = 0; i<bookList.size(); i++)
            {
                if(bookList.get(i).getTitle().equalsIgnoreCase(bName) && bookList.get(i).getID().equals(ID))
                {
                        bookList.remove(i);
                        System.out.println("You have successfully deleted the following book, "+bName+" from the library.\n");
                        viewLibraryBooks();
                        deleteSuccess = true;
                        break;
                }
            }
            if(!deleteSuccess)
                System.out.println("The ID Doesn't match the following book. Please enter again:");
        }
        return deleteSuccess;
    }

    public void managePatrons()
    {
        int code = 0; 
        do
        {
            System.out.println("\n|MANAGE PATRON|\n===============");
            System.out.println("1) View Patron List");
            System.out.println("2) Search Patron");
            System.out.println("3) View fine list");
            System.out.println("4) Manage Book Reserves");
            System.out.println("5) Exit");
            System.out.println("Enter the number of the desired function: ");

            if(sc.hasNextInt())
            {
                code = sc.nextInt();
                sc.nextLine();  //consume the newline characters

                switch(code)
                {
                    case 1: //View Patron List
                    {
                        boolean libraryFilter = false;
                        viewPatronList();
                        filterPrompt(libraryFilter);
                        break;
                    }
                    case 2: //Search Patron
                    {
                        String pName;
                        int no = 0;
                        pName = searchPatron();
                        if(pName != "0")
                        {
                            do
                            {
                                System.out.println("\n|1- View Patron's Borrowed Books|2- Display Patron's Fine record|3- Exit|"); 
                                if(sc.hasNextInt())
                                {
                                    no = sc.nextInt();
                                    sc.nextLine(); //consume the newline characters
                                    switch(no)
                                    {
                                        case 1: 
                                        {
                                            int x = 0;
                                            boolean existingBooks;

                                            do
                                            {   
                                                existingBooks = p.viewPatronBorrowedBooks(pName);
                                                if(existingBooks)
                                                {
                                                    System.out.println("Press 1 to retrieve a book, press 2 to exit:"); 
                                                    if(sc.hasNextInt())
                                                    {
                                                        x = sc.nextInt();
                                                        sc.nextLine();
                                                        // CONFIRM THE RETURN OF THE BOOK
                                                        switch(x)
                                                        {
                                                            case 1: confirmBookReturn(pName); break;
                                                            case 2: System.out.println("Exit."); break;
                                                            default: System.out.println("Invalid integer.");
                                                        }
                                                    }
                                                    else
                                                    {
                                                        System.out.println("Invalid value.");
                                                        sc.nextLine();
                                                    }
                                                }
                                            }while(x != 2 && existingBooks);
                                            break;
                                        }
                                        case 2:
                                        {
                                            int y = 0;
                                            boolean existingFines;
                                            existingFines = p.displayFines(pName);
                                            if(existingFines)
                                            {
                                                do
                                                {
                                                    System.out.println("\nPress 1 to overturn the fine, press 2 to exit:");
                                                    if(sc.hasNextInt())
                                                    {
                                                        y = sc.nextInt();
                                                        sc.nextLine();
                                                        if(y == 1)
                                                        {
                                                            updateFine(pName); break;
                                                        }
                                                        else if(y == 2)
                                                        {
                                                            System.out.println("Update fines is not chosen."); break;
                                                        }
                                                        else
                                                        {
                                                            System.out.println("Invalid integer.");
                                                        }                               
                                                    }
                                                    else
                                                    {
                                                        System.out.println("Invalid value.");
                                                        sc.nextLine();
                                                    }
                                                }while(y != 2); 
                                            }
                                            break;
                                        }
                                        case 3:
                                        {
                                            System.out.println("\n--Going back to Manage Patron Page--");
                                            break;
                                        }
                                        default:
                                            System.out.println("Invalid input.\n");
                                    }
                                }
                                else
                                {
                                    System.out.println("Please type in an integer value.\n");
                                    sc.nextLine(); //Consume the newline character
                                }
                            }while(no != 3);
                        }
                        break;
                    }
                    case 3: //View fine list
                    {
                        viewPatronsFineList();
                        break;
                    }
                    case 4: //Pending Book Reserves
                    { 
                        boolean existingBookReserves = false;
                        existingBookReserves = pendingBookReserves();
                        if(existingBookReserves)
                        {
                            turnReserveToBorrow();
                        }
                        break;
                    }
                    case 5: //exit
                    {   
                        System.out.println("\n--You have exited the Manage Patron page. Back to Patron Homepage--");
                        break;
                    }
                    default:
                        System.out.println("Invalid integer.\n");
                }
            }
            else
            {
                System.out.println("Invalid value.\n");
                sc.nextLine();
            }
        }while(code != 5);
    }

    public void viewPatronList()
    {
        patronListHeader();
        for(Patron p: patronList)
        {
            System.out.println(p);
        }
    }

    public String searchPatron()
    {
        String search;
        boolean patronFound = false;

        System.out.println("Please enter patron name (Press 0 to cancel):");
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
                for (Patron p: patronList) 
                {
                    if (search.equalsIgnoreCase(p.getName())) 
                    {   
                        System.out.println(String.format("\n%-15s%-11s", " ", "PATRON DETAILS"));
                        System.out.println("============================================\n"
                            +String.format("| %-7s | %-30s |", "ID", "NAME")
                                        +"\n============================================");
                        System.out.println(p);
                        patronFound = true;
                        break;
                    }
                }
            }
            if(!patronFound)
                System.out.println("Patron not found. Please enter patron name again: ");
        }while(!search.equals("0") && !patronFound);
        return search;
    }

    public void confirmBookReturn(String pName)
    {
        String returnTitle;

        LocalDate borrowDate = null;
        LocalDate returnDate = null;
        LocalDate dueDate = null;
        String returnDateInput = null;

        boolean titleFound = false;
        boolean listUpdate=false;

        for (Patron p : patronList) {

            if (p.getName().equalsIgnoreCase(pName)) {
                System.out.println("\nPlease enter the title of the book to be returned:");
                returnTitle = sc.nextLine();

                for (int i = 0; i<p.borrowedBookList.size(); i++) {
                    if (returnTitle.equalsIgnoreCase(p.borrowedBookList.get(i).getTitle())) {
                        titleFound=true;                                
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        borrowDate = LocalDate.parse(p.borrowedBookList.get(i).getBorrowDate(), formatter);
                        dueDate = LocalDate.parse(p.borrowedBookList.get(i).getDueDate(), formatter);

                        System.out.println("\nPlease enter the return date of the book [yyyy-MM-dd] (Press 0 to terminate the process):");
                        while (!(returnDateInput = sc.nextLine()).equals("0")) {
                            try {
                                returnDate = LocalDate.parse(returnDateInput);

                                if(returnDate.isBefore(borrowDate)) //make sure the return date is not before the borrow date
                                {
                                    System.out.println("The return date has to be after the borrow date.\n");
                                    System.out.println("Please enter the return date of the book [yyyy-MM-dd] (Press 0 to terminate the process):");
                                    continue;
                                }

                                if (returnDate.isBefore(dueDate)) {
                                    System.out.println("\n'"+p.borrowedBookList.get(i).getTitle()+"' has been returned successfully back to the library.");

                                    for (Book b : bookList) {
                                        if (b.getTitle().equalsIgnoreCase(p.borrowedBookList.get(i).getTitle())) {
                                            listUpdate = true;
                                            b.setQuantity(b.getQuantity() + 1);
                                            if (!b.getVacant()) {
                                                b.setVacant(true);
                                            }
                                        }
                                    }
                                    if(!listUpdate) System.out.println ("Warning, book list has not been updated!");
                                    p.borrowedBookList.remove(i);
                                    break;
                                }
                                else if (returnDate.isAfter(dueDate)) {
                                System.out.println("\nThe book has been returned past it's due date. A fine of RM" + String.format("%.2f",calculateFine(dueDate, returnDate)) + " has to be paid");
                                p.setFineAmount(calculateFine(dueDate, returnDate));
                                    for (Book b : bookList) {
                                        if (b.getTitle().equalsIgnoreCase(p.borrowedBookList.get(i).getTitle())) {
                                            listUpdate = true;
                                            b.setQuantity(b.getQuantity() + 1);
                                            if (!b.getVacant()) {
                                                b.setVacant(true);
                                            }
                                        }
                                    }
                                    if(!listUpdate) {System.out.println ("Warning, book list has not been updated!");}
                                    p.borrowedBookList.remove(i);
                                    break;
                                }
                         } catch (DateTimeParseException e) {
                                System.out.println("Invalid date format. Please enter the correct date format [yyyy-MM-dd] or press 0 to terminate:");
                            }
                        }

                        if (returnDateInput.equals("0")) {
                            System.out.println("You have terminated the process.");
                            break;
                        }
                    }
                    if (!titleFound) {
                        System.out.println("The title of the book is not found under the list of borrowed books of the patron");
                        break;
                    }
                }

            }
        }
    }

    public void updateFine(String pName)
    {
        for(Patron p: patronList)
        {
            if(p.getName().equalsIgnoreCase(pName))
            {
                p.resetFineAmount();
                System.out.println("\nThe fines from patron "+p.getName()+" has been paid.");
                break;
            }
        }
    }

    public double calculateFine(LocalDate dueDate, LocalDate returnDate)
    {
        long daysDifferenceL = ChronoUnit.DAYS.between(dueDate, returnDate);
        double daysDifference = (double) daysDifferenceL; //convert long to int

        return daysDifference*0.5; 
        //amount += calculateFine(dueDate, returnDate);
    }

    public void viewPatronsFineList()
    {
        System.out.println("================================================================================\n"+
        String.format("|%-30s%-18s%-30s|"," ", "PATRONS' FINE LIST", " "));
        System.out.println("================================================================================");
        for(Patron p: patronList)
        {
            System.out.println(p.getName()+"'s fine record:"+"\n--------------------------------------");
            if(p.getFineAmount()>0)
            {        
                System.out.println("Total fine amount is\nRM"+ String.format("%.2f", p.getFineAmount()));
            }
        }
    }

    public boolean pendingBookReserves()
    {   
        Boolean bookReserved = false;
        System.out.println("===================================================================\n"+
        String.format("|%-22s%-21s%-22s|", " ", "PENDING BOOK RESERVES", " "));
        System.out.println("===================================================================\n"+String.format("| %-7s | %-30s | %-20s |", "ID", "TITLE", "AUTHOR")+"\n===================================================================");
        for(Patron p: patronList)
        {
            if(!p.reservedBookList.isEmpty())
            {
                System.out.println("\n"+p.getName()+"'s book reserves record: "+
            "\n===================================================================");
                for(ReservedBook rb : p.reservedBookList)
                {
                    System.out.println(rb);
                    bookReserved = true;
                }
            }   
        }     
        if(!bookReserved)
        {
            System.out.println("**There are currently zero book reserves from any patron.**");
            return bookReserved;
        }         
        else
            return bookReserved;
    }

    public void turnReserveToBorrow()//NOTE: WE NEED TO REMOVE FROM THE RESERVEDBOOKLIST AFTER ADDING TO THE BORROWEDBOOKLIST
    {   
        String pName = " ";
        String bName = " ";
        int no = 0;
        Boolean patronFound = false, reservedBookFound = false;

        do
        {
            System.out.println("Press 1 to confirm the borrowing of a book, press 2 to exit: ");
            if(sc.hasNextInt())
            {
                no = sc.nextInt();
                sc.nextLine();
                if(no == 1)
                {
                    System.out.println("\nEnter the Patron's name that you want to confirm the borrowing of the book from: ");
                    pName = sc.nextLine();
                    for(Patron p: patronList)
                    {
                        if(p.getName().equalsIgnoreCase(pName))
                        {
                            patronFound = true;
                            if(!p.reservedBookList.isEmpty())
                            {
                                System.out.println("\nEnter the title of the book to confirm the borrowing of the book: ");
                                bName = sc.nextLine();
                                for(int i = 0; i<p.reservedBookList.size(); i++)
                                {
                                    if(p.reservedBookList.get(i).getTitle().equalsIgnoreCase(bName))
                                    {            
                                        reservedBookFound = true;                                      
                                        String dueDateInput;
                                        LocalDate dueDate = null;                                  
                                        LocalDate borrowDate = LocalDate.now();

                                        System.out.println("\nEnter the due Date [yyyy-MM-dd] (Press 0 to terminate the process): ");
                                        while(!(dueDateInput = sc.nextLine()).equals("0"))
                                        {
                                            try
                                            {
                                                dueDate = LocalDate.parse(dueDateInput);
                                                if(dueDate.isBefore(borrowDate))
                                                {
                                                    System.out.println("The due date should be after the borrow date. Please enter again:");
                                                }
                                                else 
                                                {
                                                    break;
                                                }
                                            }
                                            catch (DateTimeParseException e)
                                            {
                                                System.out.println("Invalid date format. Please enter the correct date format [yyyy-MM-dd] or press 0 to terminate:");
                                            }
                                        }

                                        if(dueDateInput.equals("0"))
                                        {
                                            System.out.println("You have terminated the process.");
                                            break;
                                        }

                                        p.borrowedBookList.add(new BorrowedBook(p.reservedBookList.get(i).getID(), p.reservedBookList.get(i).getTitle(), p.reservedBookList.get(i).getAuthor(), borrowDate, dueDate));
                                        System.out.println("--Patron "+p.getName()+" has successfully borrowed the book, "+p.reservedBookList.get(i).getTitle()+".--"); 
                                        p.reservedBookList.remove(i);                        
                                    }

                                }
                            }
                            else
                            {
                                System.out.println("**Patron "+p.getName()+" has not reserve any books.**");
                                break;
                            }
                        }
                    }    
                    if(!patronFound)
                    {
                        System.out.println("Patron is not found.\n");
                    }
                    else if(!reservedBookFound)
                    {
                        System.out.println("Patron "+pName+" has not reserve the book with the following title.\n");
                    }    
                    if(!pendingBookReserves())
                    {
                        break;
                    }  
                }
                else if(no == 2)
                {
                    System.out.println("You have chosen to exit.");
                }
                else 
                    System.out.println("Invalid integer.");
            }
            else
            {
                System.out.println("Invalid value.");
                sc.nextLine();
            } 
        }while(no != 2);
    }
}