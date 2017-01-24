//package AutoDaikon;
public class Mock {
    int mock_011(String record) {
        try{ 
            return Integer.parseInt(record);
        } catch (NumberFormatException e) { return -1;}
    }
    byte mock_012(String record) {
        try{ 
            return Byte.parseByte(record);
        } catch (NumberFormatException e) { return -1;}
    }
    void mock_01(String record) {
        String[] parts = record.split("_");
        if(parts.length == 2) {
            if(mock_011(parts[0]) < 0) System.out.println("Expected to be Int/Long -> "+parts[0]+"[0111]");
            if(mock_012(parts[1]) < 0) System.out.println("Expected to be Int/Long -> "+parts[1]+"[0121]");
        } else System.out.println("Expected to be in the format of ***_*** -> "+record);
    }
    void mock_0(String record) {
        String[] parts = record.split(",");
        for(String part: parts)
            mock_01(part);
    }
    
}
