package company.project.utils;

import java.util.Random;

public class ValidCreditCardNumberGenerator {
	
	public static String getValidVisaCreditCardNumber() {

        Random random = new Random();
        int pos = 9;
        int len = 16;
        int t = 0;
        int sum = 0;
        int finalDigit = 0;
 
        // generate first 8 credit card digits
        StringBuilder cc= new StringBuilder();
        cc.append("49166490");

        // fill all the remaining cc numbers except for the last one with random values
        while (pos < len - 1) {
        	cc.append(random.nextInt(10));
        	pos++;
        }
 
        // calculate the Luhn check sum of the values so far
        int len_offset = (len + 1) % 2;

        for (pos = 0; pos < len - 1; pos++) {
        	if ((pos + len_offset) % 2 == 0){
        		t = cc.charAt(pos) * 2;
        			if (t > 9) {
        				t -= 9;
        			}
        			sum += t;
        	} else {
        		sum += cc.charAt(pos);
        	}

        }

        // choose the last digit so that the entire string passes the checksum
        finalDigit = (10 - (sum % 10)) % 10;
        cc.append(finalDigit);

        return cc.toString();
        
  }
}
