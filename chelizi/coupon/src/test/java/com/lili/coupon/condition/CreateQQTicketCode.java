package com.lili.coupon.condition;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * Created by ZLong on 2016/六月/4.
 */
public class CreateQQTicketCode {
    public static void main(String[] args) throws IOException {

        int[] data = randomCommon(100000, 999999, 200000);

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/Users/Poorzerg/Downloads/code.txt"), "utf-8"));
        for (int i : data) {
            String value = String.valueOf(i);
            bw.write("11" + value.charAt(3) + 0 + value.charAt(1) + value.charAt(4) + 0 + value.charAt(0) + 1 + value.charAt(2) + value.charAt(5) + 0);
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }

    public static int[] randomCommon(int min, int max, int n) {
        if (n > (max - min + 1) || max < min) {
            return null;
        }
        int[] result = new int[n];
        int count = 0;
        while (count < n) {
            int num = (int) (Math.random() * (max - min)) + min;
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                if (num == result[j]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                result[count] = num;
                count++;
            }
        }
        return result;
    }
}
