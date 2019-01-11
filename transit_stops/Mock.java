//data/BigData/TransitBusData/TransitBusData/transit_stops/L50_transit_stops.csv
public class Mock {
	long mock_A(String record) {
        return Long.parseLong(record);
    }
	int mock_B(String record) {
        return record.length();
    }

    void mock_01(String record) {
        String[] parts = record.split(" ");
        for(String part: parts) {
            mock_B(part);
        }
    }
	float mock_C(String record) {
        return Float.parseFloat(record);
    }
	float mock_D(String record) {
        return Float.parseFloat(record);
    }
	float mock_E(String record) {
        return Float.parseFloat(record);
    }
	float mock_F(String record) {
        return Float.parseFloat(record);
    }
	void mock_0(String record) {
        String[] parts = record.split(",");
        if(parts.length == 6) {			
            try{
                long x00 = mock_A(parts[0]);
            } catch (NumberFormatException e) {
                System.out.println("Expected to be A:DecimalShort -> "+parts[0]);   
            }
			mock_01(parts[1]);
			
            try{
                float x02 = mock_C(parts[2]);
            } catch (NumberFormatException e) {
                System.out.println("Expected to be C:DecimalFloat -> "+parts[2]);   
            }
			
            try{
                float x03 = mock_D(parts[3]);
            } catch (NumberFormatException e) {
                System.out.println("Expected to be D:DecimalFloat -> "+parts[3]);   
            }
			
            try{
                float x04 = mock_E(parts[4]);
            } catch (NumberFormatException e) {
                System.out.println("Expected to be E:DecimalFloat -> "+parts[4]);   
            }
			
            try{
                float x05 = mock_F(parts[5]);
            } catch (NumberFormatException e) {
                System.out.println("Expected to be F:DecimalFloat -> "+parts[5]);   
            }
		} else System.out.println("Expected to be in the format of Tuple6[','] -> "+record);
    }

}
