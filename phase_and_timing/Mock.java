//data/BigData/ArterialData/phase_and_timing/L50_phase_and_timing.csv
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
	long mock_B(String record) {
        return Long.parseLong(record);
    }
	long mock_C(String record) {
        return Long.parseLong(record);
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
	long mock_G(String record) {
        return Long.parseLong(record);
    }
	long mock_H(String record) {
        return Long.parseLong(record);
    }
	long mock_I(String record) {
        return Long.parseLong(record);
    }
	long mock_J(String record) {
        return Long.parseLong(record);
    }
	long mock_K(String record) {
        return Long.parseLong(record);
    }
    //Date[yyyy-MM-dd HH:mm:ss]
	void mock_L(String record) {
        GregorianCalendar cal = new GregorianCalendar();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
	void mock_0(String record) {
        String[] parts = record.split(",");
        if(parts.length == 12) {			
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
			
            try{
                long x02 = mock_C(parts[2]);
            } catch (NumberFormatException e) {
                System.out.println("Expected to be C:DecimalShort -> "+parts[2]);   
            }
			
            try{
                long x03 = mock_D(parts[3]);
            } catch (NumberFormatException e) {
                System.out.println("Expected to be D:DecimalShort -> "+parts[3]);   
            }
			
            try{
                long x04 = mock_E(parts[4]);
            } catch (NumberFormatException e) {
                System.out.println("Expected to be E:DecimalShort -> "+parts[4]);   
            }
			
            try{
                long x05 = mock_F(parts[5]);
            } catch (NumberFormatException e) {
                System.out.println("Expected to be F:DecimalShort -> "+parts[5]);   
            }
			
            try{
                long x06 = mock_G(parts[6]);
            } catch (NumberFormatException e) {
                System.out.println("Expected to be G:DecimalByte -> "+parts[6]);   
            }
			
            try{
                long x07 = mock_H(parts[7]);
            } catch (NumberFormatException e) {
                System.out.println("Expected to be H:DecimalByte -> "+parts[7]);   
            }
			
            try{
                long x08 = mock_I(parts[8]);
            } catch (NumberFormatException e) {
                System.out.println("Expected to be I:DecimalShort -> "+parts[8]);   
            }
			
            try{
                long x09 = mock_J(parts[9]);
            } catch (NumberFormatException e) {
                System.out.println("Expected to be J:DecimalByte -> "+parts[9]);   
            }
			
            try{
                long x010 = mock_K(parts[10]);
            } catch (NumberFormatException e) {
                System.out.println("Expected to be K:DecimalShort -> "+parts[10]);   
            }
			mock_L(parts[11]);
		} else System.out.println("Expected to be in the format of Tuple12[','] -> "+record);
    }

}
