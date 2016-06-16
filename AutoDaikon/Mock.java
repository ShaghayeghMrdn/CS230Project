package AutoDaikon;
public class Mock {
    void mock_01(String record) {
    }
    void mock_0(String record) {
        String[] parts = record.split("_");
        for(String part: parts)
            mock_01(part);
    }
    
}
