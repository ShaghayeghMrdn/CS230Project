//data/BigData/ArterialData/traveltimes/L50_traveltimes.csv
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Mock {
	long mock_A(String record) {
        return Long.parseLong(record);
    }
	long mock_B(String record) {
        return Long.parseLong(record);
    }

    int mock_year(int x) {return x;}
    int mock_month(int x) {return x;}
    int mock_day(int x) {return x;}
    int mock_hour(int x) {return x;}
    int mock_minute(int x) {return x;}
    int mock_second(int x) {return x;}
    int mock_milli(int x) {return x;}

    //Date[M/dd/yyyy H:mm]
	void mock_C(String record) {
        GregorianCalendar cal = new GregorianCalendar();
        SimpleDateFormat formatter = new SimpleDateFormat("M/dd/yyyy H:mm");
        try{
            Date date = formatter.parse(record);
            if(date != null) {
                cal.setTime(date);
                mock_year(cal.get(Calendar.YEAR));
                mock_month(cal.get(Calendar.MONTH)+1);      //starts from 0
                mock_day(cal.get(Calendar.DAY_OF_MONTH));
                mock_hour(cal.get(Calendar.HOUR_OF_DAY));  //0-23
                mock_minute(cal.get(Calendar.MINUTE));
                mock_second(cal.get(Calendar.SECOND));
                mock_milli(cal.get(Calendar.MILLISECOND));
            }
        } catch(ParseException e) {
            System.out.println("Expected to be C:DateLiteral -> "+record);    
        }
    }
	long mock_D(String record) {
        return Long.parseLong(record);
    }
	int mock_E(String record) {
        return record.length();
    }
	void mock_0(String record) {
        String[] parts = record.split(",");
        if(parts.length == 5) {			
            try{
                long x00 = mock_A(parts[0]);
            } catch (NumberFormatException e) {
                System.out.println("Expected to be A:DecimalShort -> "+parts[0]);   
            }
			
            try{
                long x01 = mock_B(parts[1]);
            } catch (NumberFormatException e) {
                System.out.println("Expected to be B:DecimalShort -> "+parts[1]);   
            }
			mock_C(parts[2]);
			
            try{
                long x03 = mock_D(parts[3]);
            } catch (NumberFormatException e) {
                System.out.println("Expected to be D:DecimalShort -> "+parts[3]);   
            }
			mock_E(parts[4]);
		} else System.out.println("Expected to be in the format of Tuple5[','] -> "+record);
    }

}
