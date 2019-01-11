//data/BigData/ArterialData/raw_detector/L50_raw_detector.csv
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Mock {
    int mock_year(int x) {return x;}
    int mock_month(int x) {return x;}
    int mock_day(int x) {return x;}
    int mock_hour(int x) {return x;}
    int mock_minute(int x) {return x;}
    int mock_second(int x) {return x;}
    int mock_milli(int x) {return x;}

	long mock_A(String record) {
        return Long.parseLong(record);
    }
    //Date[yyyy-MM-dd HH:mm:ssX]
	void mock_B(String record) {
        GregorianCalendar cal = new GregorianCalendar();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssX");
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
	int mock_C(String record) {
        return record.length();
    }
	long mock_D(String record) {
        return Long.parseLong(record);
    }
	long mock_E(String record) {
        return Long.parseLong(record);
    }
	long mock_F(String record) {
        return Long.parseLong(record);
    }
	int mock_G(String record) {
        return record.length();
    }
	int mock_H(String record) {
        return record.length();
    }
	int mock_I(String record) {
        return record.length();
    }
	int mock_J(String record) {
        return record.length();
    }
	int mock_K(String record) {
        return record.length();
    }
	void mock_0(String record) {
        String[] parts = record.split(",");
        if(parts.length == 11) {			
            try{
                long x00 = mock_A(parts[0]);
            } catch (NumberFormatException e) {
                System.out.println("Expected to be A:DecimalShort -> "+parts[0]);   
            }
			mock_B(parts[1]);
			mock_C(parts[2]);
			
            try{
                long x03 = mock_D(parts[3]);
            } catch (NumberFormatException e) {
                System.out.println("Expected to be D:DecimalByte -> "+parts[3]);   
            }
			
            try{
                long x04 = mock_E(parts[4]);
            } catch (NumberFormatException e) {
                System.out.println("Expected to be E:DecimalByte -> "+parts[4]);   
            }
			
            try{
                long x05 = mock_F(parts[5]);
            } catch (NumberFormatException e) {
                System.out.println("Expected to be F:DecimalShort -> "+parts[5]);   
            }
			mock_G(parts[6]);
			mock_H(parts[7]);
			mock_I(parts[8]);
			mock_J(parts[9]);
			mock_K(parts[10]);
		} else System.out.println("Expected to be in the format of Tuple11[','] -> "+record);
    }

}
