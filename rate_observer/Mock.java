//data/rate_observer/L50_rate_observer_mod.txt
public class Mock {
    void pair_int(int x_0, int x_1) {}
    void pair_float(float x_0, float x_1) {}
	float mock_A(String record) {
        return Float.parseFloat(record);
    }
	int mock_B(String record) {
        return record.length();
    }
	int mock_C(String record) {
        return record.length();
    }
	void mock_01(String record) {
        String[] parts = record.split("/");
        if(parts.length == 2) {
            int b = mock_B(parts[0]);
			int c = mock_C(parts[1]);
            pair_int(b, c);
		} else System.out.println("Expected to be in the format of Tuple2['/'] -> "+record);
    }
	float mock_D(String record) {
        return Float.parseFloat(record);
    }
	float mock_E(String record) {
        return Float.parseFloat(record);
    }
	void mock_02(String record) {
        String[] parts = record.split(":");
        if(parts.length == 2) {
            float d = mock_D(parts[0]);
            float e = mock_E(parts[1]);
            pair_float(d, e);

		} else System.out.println("Expected to be in the format of Tuple2[':'] -> "+record);
    }
	int mock_F(String record) {
        return record.length();
    }
    void mock_03(String record) {
        String[] parts = record.split(" ");
        for(String part: parts) {
            mock_F(part);
        }
    }
	void mock_0(String record) {
        String[] parts = record.split(",");
        try{
            float x00 = mock_A(parts[0]);
        } catch (NumberFormatException e) {
            System.out.println("Expected to be A:DecimalFloat -> "+parts[0]);   
        }
		mock_01(parts[1]);
		mock_02(parts[2]);
		if(parts.length == 4) {
            mock_03(parts[3]);
        } else {
            mock_03("");
        }
		
    }

}
