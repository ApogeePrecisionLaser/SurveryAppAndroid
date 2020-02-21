package com.apogee.fleetsurvey.utility;

public class FLETCHERALGORITHM {
    public static String fletcheralgoname="FLETCHER'S ALGORITHM";

    public String checksum(String command) {
        String[] commandPair = new String[(command.length() / 2) + 1];
        int j = 0;
        int size = command.length();
        for (int i = 0; i < size; i += 2) {
            commandPair[j] = command.substring(i, i + 2);
            j++;
        }
        String ch_A = "0";
        String ch_B = "0";
        int length = commandPair.length - 1;
        for (int i = 0; i < length; i++) {
            ch_A = addCheckSum(ch_A, commandPair[i]);
            ch_B = addCheckSum(ch_B, ch_A);
        }
        ch_A = Integer.toHexString(Integer.parseInt(ch_A, 16) & 0xFF).toUpperCase();
        ch_B = Integer.toHexString(Integer.parseInt(ch_B, 16) & 0xFF).toUpperCase();
        if (ch_A.length() == 1) {
            ch_A = "0" + ch_A;
        }
        if (ch_B.length() == 1) {
            ch_B = "0" + ch_B;
        }
        return ch_A + ch_B;
    }

    public String addCheckSum(String ch_A, String ch_B) {
        int A = Integer.parseInt(ch_A, 16);
        int B = Integer.parseInt(ch_B, 16);
        int sum = A + B;
        return Integer.toHexString(sum);

    }
}
