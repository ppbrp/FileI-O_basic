import java.io.*;

public class RandomAccessFileCity {
    /**
     * read city, latitude, longitude, country, population from csv
     * write into a random access file
     * city[50], double, double, country[50], int
     * write 2 method
     * 1. search city return latitude,longitude,population
     * 2. find total population of specific country
     **/

    public static void main(String[] args) {
//        genRandomAccessFile("worldcities.csv", "city.dat");
        System.out.println(search("city.dat", "Tokyo"));

        System.out.println(findPopulation("city.dat", "India"));

        System.out.println(searchByRunningNumber("city.dat", 10));
    }

    public static void genRandomAccessFile(String textFilePath, String rafPath) {
        try {
            FileReader f = new FileReader(textFilePath);
            BufferedReader fRead = new BufferedReader(f);
            String record = fRead.readLine();
            record = fRead.readLine();
            String[] data = new String[11];
            RandomAccessFile fPointer = new RandomAccessFile(rafPath, "rw");
            int runningNumber = 1;
            byte[] strBytes = new byte[50];
            double lat, lng;
            int population;
            while (record != null) { //4+50+8+8+50+4 = 124
                data = record.split(",");
                fPointer.writeInt(runningNumber++);
                strBytes = (data[0] + " ".repeat(50)).getBytes();
                fPointer.write(strBytes, 0, 50);
                lat = Double.parseDouble(data[2]);
                fPointer.writeDouble(lat);

                lng = Double.parseDouble(data[3]);
                fPointer.writeDouble(lng);

                strBytes = (data[4] + " ".repeat(50)).getBytes();
                fPointer.write(strBytes, 0, 50);
                if (data[9].isEmpty()) {
                    population = 0;
                } else {
                    population = Integer.parseInt(data[9]);
                }
                fPointer.writeInt(population);
                record = fRead.readLine();
            }
            fPointer.close();
            fRead.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //search city return latitude,longitude,population
    public static String search(String rafFilePath, String city) {
        byte[] strByte = new byte[50];
        StringBuilder sb = new StringBuilder();
        int recordSize = 124;
        try {
            RandomAccessFile raf = new RandomAccessFile(rafFilePath, "r");
            for (int i = 0; raf.getFilePointer() < raf.length(); i++) {
                raf.seek(4+ (recordSize*i));
                raf.read(strByte, 0, 50);
                String get = new String(strByte).trim();
                if (get.equals(city)) {
                    raf.seek(54+(recordSize*i));
                    sb.append("Latitude: " + raf.readDouble() + "\n");
                    sb.append("Longitude: " + raf.readDouble() + "\n");
                    raf.seek(120+(recordSize*i));
                    sb.append("Population: " + raf.readInt() + "\n");
                }
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int findPopulation(String rafFilePath, String nation) {
        byte[] strByte = new byte[50];
        int recordSize = 124;
        int temp = 0;
        try {
            RandomAccessFile raf = new RandomAccessFile(rafFilePath, "r");
            for (int i = 0; raf.getFilePointer() < raf.length(); i++) {
                raf.seek(70+ (recordSize*i));
                raf.read(strByte, 0, 50);
                String get = new String(strByte).trim();
                if (get.equals(nation)) {
                    raf.seek(120+(recordSize*i));
                    temp += raf.readInt();
                }
            }
            return temp;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String searchByRunningNumber(String rafFilePath, int cityNumber) {
        StringBuilder sb = new StringBuilder();
        byte[] strBytes = new byte[50];
        int recordSize = 124 * (cityNumber-1);
        try {
            RandomAccessFile raf = new RandomAccessFile(rafFilePath, "r");
            raf.seek(4+recordSize);
            raf.read(strBytes, 0, 50);
            sb.append("City: " + new String(strBytes) + "\n");
            raf.seek(70+recordSize);
            raf.read(strBytes, 0, 50);
            sb.append("Country: " + new String(strBytes) + "\n");
            sb.append("Population: " + raf.readInt() + "\n");
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
