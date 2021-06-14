package maharsh.server.app.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class IpUtils {

    // Converts IP address to the binary form
    public static int[] getBinaryFromIp(String[] str)
    {
        int[] re = new int[32];
        int a, b, c, d, i, rem;
        a = b = c = d = 1;
        Stack<Integer> st = new Stack<>();

        // Separate each number of the IP address
        if (str != null)
        {
            a = Integer.parseInt(str[0]);
            b = Integer.parseInt(str[1]);
            c = Integer.parseInt(str[2]);
            d = Integer.parseInt(str[3]);
        }

        // convert first number to binary
        for (i = 0; i <= 7; i++)
        {
            rem = a % 2;
            st.push(rem);
            a = a / 2;
        }

        // Obtain First octet
        for (i = 0; i <= 7; i++) {
            re[i] = st.pop();
        }

        // convert second number to binary
        for (i = 8; i <= 15; i++) {
            rem = b % 2;
            st.push(rem);
            b = b / 2;
        }

        // Obtain Second octet
        for (i = 8; i <= 15; i++) {
            re[i] = st.pop();
        }

        // convert Third number to binary
        for (i = 16; i <= 23; i++) {
            rem = c % 2;
            st.push(rem);
            c = c / 2;
        }

        // Obtain Third octet
        for (i = 16; i <= 23; i++) {
            re[i] = st.pop();
        }

        // convert fourth number to binary
        for (i = 24; i <= 31; i++) {
            rem = d % 2;
            st.push(rem);
            d = d / 2;
        }

        // Obtain Fourth octet
        for (i = 24; i <= 31; i++) {
            re[i] = st.pop();
        }

        return (re);
    }

    // Converts IP address
    // from binary to decimal form
    public static int[] deci(int[] bi)
    {

        int[] arr = new int[4];
        int a, b, c, d, i, j;
        a = b = c = d = 0;
        j = 7;

        for (i = 0; i < 8; i++) {

            a = a + (int)(Math.pow(2, j)) * bi[i];
            j--;
        }

        j = 7;
        for (i = 8; i < 16; i++) {

            b = b + bi[i] * (int)(Math.pow(2, j));
            j--;
        }

        j = 7;
        for (i = 16; i < 24; i++) {

            c = c + bi[i] * (int)(Math.pow(2, j));
            j--;
        }

        j = 7;
        for (i = 24; i < 32; i++) {

            d = d + bi[i] * (int)(Math.pow(2, j));
            j--;
        }

        arr[0] = a;
        arr[1] = b;
        arr[2] = c;
        arr[3] = d;
        return arr;
    }

    public static String getBroadcastAddress(String ipr)
    {

        int i;
        String[] str;

        // Separate IP address and n
        String[] str1 = ipr.split("/");

        // IP address
        String tr = str1[0];

        // Split IP address into 4 sub parts x, y, z, t
        str = tr.split("\\.");

        int[] b;

        // Convert IP address to binary form
        b = getBinaryFromIp(str);

        int n = Integer.parseInt(str1[1]);
        int[] brd = new int[32];
        int t = 32 - n;

        // Obtaining network address
        for (i = 0; i <= (31 - t); i++) {
            brd[i] = b[i];
        }

        // Obtaining Broadcast address
        // by setting 32-n bits to 1
        for (i = 31; i > (31 - t); i--) {
            brd[i] = 1;
        }

        // Converting broadcast address to decimal
        int[] br = deci(brd);
        return br[0] + "." + br[1] + "." + br[2] + "." + br[3];
    }

    public static int toIntMask(int mask) {
        if (mask == 0) {
            return 0;
        }
        int allOne = -1;    // '255.255.255.255'
        //return shifted
        return allOne << (Integer.SIZE - mask);
    }

    public static String convertIpToQuartet(int ipAddress) {
        int octet1 = (ipAddress >> 24) & 255;
        int octet2 = (ipAddress >> 16) & 255;
        int octet3 = (ipAddress >> 8) & 255;
        int octet4 = ipAddress & 255;

        return octet1 + "." + octet2 + "." + octet3 + "." + octet4;
    }

    static String toDecMask(int mask) {
        return convertIpToQuartet(toIntMask(mask));
    }

    public static Integer getCIDRFromSubnet( String subnet){
        Map<String,Integer> subnetCIDRMap = new HashMap<>();

        for (int mask =0; mask<33; mask++){
            subnetCIDRMap.put(toDecMask(mask),mask);
        }
        return subnetCIDRMap.get(subnet);
    }
}

