import java.util.*;

public class Main 
{        

    public static void main(String[] args)
    {   
        ArrayList<Book> bookList = new ArrayList<>();
        ArrayList<Patron> patronList = new ArrayList <>();
        int code = 0;   
        Scanner x = new Scanner(System.in);

        bookList.add(new Book("112394", "C++", "Sam Pong", 2 ,true));
        bookList.add(new Book("619817", "Java", "Max Jones", 3 ,true));
        bookList.add(new Book("312423", "Maths", "Tom", 1 ,true));
        bookList.add(new Book("923482", "Science", "Abu Ali", 2 , true));
        bookList.add(new Book("749823", "English", "Daniel", 2 , true));
        bookList.add(new Book("823401", "DSA", "Bob", 1 , true));
        bookList.add(new Book("395494", "OOSAD", "James Wong", 3 , true));
        bookList.add(new Book("492282", "Add maths", "Piper", 2 , true));
        bookList.add(new Book("237491", "Psychology", "Jordan Peterson", 2 , true));
        bookList.add(new Book("102342", "Operating System", "Khoo", 2 , true));

        patronList.add(new Patron("0133519", "Justin Wong Junn Sheng", "password"));
        patronList.add(new Patron("0134337", "Kaaviraaj A/L Saravanan", "qwerty"));
        patronList.add(new Patron("0136136", "Pravin Ganesh A/L Thinaharan", "yoooo"));
        patronList.add(new Patron("0136672", "Rachel Khoo Jia Min", "abc123")); 

        Admin a = new Admin(bookList, patronList); //overload Admin a = new Admin(patronList, bookList);
        Patron p = new Patron(bookList, patronList); 
        do
        {
            System.out.println("\n|LIBRARY MANAGEMENT SYSTEM|"+
                            "\n===========================");
            System.out.println("1) Administrator\n"+"2) Patron\n"+"3) Exit");
            System.out.println("Please enter the number according to your role:");

            if(x.hasNextInt())//Check if the next input has integer value before calling nextInt()
            {
                code = x.nextInt();
                switch(code)
                {   
                    case 1: 
                    {
                        a.login(); 
                        break;
                    }
                    case 2:                       
                    { 
                        p.login(); 
                        break;
                    }                   
                    case 3:
                    {
                        System.out.println("\n--You have exited the program.--");
                        break;
                    }
                    default:
                        System.out.println("Invalid Option.\n");
                }
            }
            else 
            {
                System.out.println("You have entered an invalid value.\n");
            }
        }while(code != 3);
      x.close();
    }
}
