import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main{
    private static final String SAMPLE_CSV_FILE_PATH = "C:/Users/aadinkar/IdeaProjects/Milestone1/src/netflix_titles.csv";

    public static void display(ArrayList<String> rows){
        /*
        * This function will be print all the records
        * */
        for(String str:rows){
            System.out.print(str + "\t\t");
        }
        System.out.println();
    }

    public static boolean displayType(ArrayList<String> rows, boolean range){
        /*
         * This function will be print all the records for type == TV show
         * */
        String type_val = new String();
        try{
            type_val = rows.get(1);
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("Invalid value or empty value in the record");
            System.exit(0);
        }
        if (type_val.equals("TV Show") && range){
            return true;
        }
        return false;
    }

    public static Boolean checkwithinrange(ArrayList<String> rows, String startDate, String endDate) throws ParseException, ArrayIndexOutOfBoundsException {
        /*
         * This function will be checking date range for question 4
         * */
        String get_year = new String();
        try{
            get_year = rows.get(7);
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("Invalid value or empty value in the record");
            System.exit(0);
        }
        try{
                Date start_date = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
                Date end_date = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
                String[] date_array = rows.get(6).split("-");
                String date_str = String.join("/",Arrays.copyOfRange(date_array, 0, 2)) + "/" + get_year;
                Date date = new SimpleDateFormat("dd/MMM/yyyy", Locale.ENGLISH).parse(date_str);
            if (date.after(start_date) && date.before(end_date)){
                return true;
            }
            return false;
        }
        catch (ParseException e){
            System.out.println("getting exception while parsing date");
            System.exit(0);
            return false;
        }
    }

    public static boolean displayListedin(ArrayList<String> rows, boolean range){
        /*
         * This function will be print all the records for listedin == horror movies
         * */
        String movie_type = new String();
        try {
            movie_type = rows.get(10);
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("Invalid value or empty value in the record");
            System.exit(0);
        }
        String[] movieValues = movie_type.split(",");
        Boolean listedin_val = Arrays.asList(movieValues).contains("Horror Movies");
        if (listedin_val && range){
            return true;
        }
        return false;
    }

    public static boolean displayCountry(ArrayList<String> rows, boolean range) throws ParseException {
        /*
         * This function will be print all the records country == india and type == movie
         * */
        String type_val = new String();
        String country_val = new String();
        try {
            type_val = rows.get(1);
            country_val = rows.get(5);
        }
        catch (IndexOutOfBoundsException e){
            System.out.println("Invalid value or empty value in the record");
            System.exit(0);
        }

        if (type_val.equals("Movie") && country_val.equals("India") && range){
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws IOException {
        /*
         * This function is the main function
         * */
        try (Scanner scanner = new Scanner(new File(SAMPLE_CSV_FILE_PATH))){
            Scanner sc= new Scanner(System.in);
            System.out.print("Enter n (number of records to be printed): ");
            Integer n= sc.nextInt();
            Scanner sc_sd= new Scanner(System.in);
            System.out.print("Enter start date (format -> dd/mm/yyyy): ");
            String start_date= sc_sd.nextLine();
            Scanner sc_ed= new Scanner(System.in);
            System.out.print("Enter end date (format -> dd/mm/yyyy): ");
            String end_date = sc_ed.nextLine();
            Integer i = 0;
            Integer count = 0;
            while (scanner.hasNextLine()) {
                String[] lst = scanner.nextLine().split(",");
                ArrayList<String> res = new ArrayList<String>();
                ArrayList<String> temp = new ArrayList<String>();
                for (String s : lst) {
                    if (s.contains("\"")) {
                        i += 1;
                        temp.add(s);
                        if (i == 2) {
                            res.add(String.join(",", temp));
                            i = 0;
                            temp = new ArrayList<String>();
                        }
                    } else if (i != 1) {
                        res.add(s);
                    } else temp.add(s);
                }
                long startTime = System.nanoTime();
                boolean range = checkwithinrange(res, start_date, end_date);
                if (displayListedin(res, range) && range) {
                    count += 1;
                    display(res);
                }
                if (count == n){
                    count = 0;
                    long endTime = System.nanoTime();
                    System.out.println("Took "+(endTime - startTime) + " ns");
                    break;
                }
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}