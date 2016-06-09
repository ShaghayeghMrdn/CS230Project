package AutoDaikon;
public class Mock {
    int mock_01(String record) {
        try{ 
            return Integer.parseInt(record);
        } catch (NumberFormatException e) { return -1;}
    }
    int mock_0211(String record) {
        try{ 
            return Integer.parseInt(record);
        } catch (NumberFormatException e) { return -1;}
    }
    int mock_0212(String record) {
        try{ 
            return Integer.parseInt(record);
        } catch (NumberFormatException e) { return -1;}
    }
    void mock_021(String record) {
        String[] parts = record.split("_");
        if(parts.length == 2) {
            if(mock_0211(parts[0]) < 0) System.out.println("Expected to be Int -> "+parts[0]+"[02111]");
            if(mock_0212(parts[1]) < 0) System.out.println("Expected to be Int -> "+parts[1]+"[02121]");
        } else System.out.println("Expected to be in the format of ***_*** -> "+record);
    }
    void mock_02(String record) {
        String[] parts = record.split(",");
        for(String part: parts)
            mock_021(part);
    }
    void mock_0(String record) {
        String[] parts = record.split(":");
        if(parts.length == 2) {
            if(mock_01(parts[0]) < 0) System.out.println("Expected to be Int -> "+parts[0]+"[011]");
            mock_02(parts[1]);
        } else System.out.println("Expected to be in the format of ***:*** -> "+record);
    }
}
