import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class RandomAccessFileLab {
    public static void main(String[] args) {
        try {
            RandomAccessFile raf = new RandomAccessFile("sample.dat","rw");
            long stid = 66130500063L;
            String name = "Tom";
            String lastname = "Riddle";
            String address = "Bangkok";
            double gpa = 3.76;
            //8+40+50+20+8
            //write student id
            raf.writeLong(stid);

            //write name
            name += " ".repeat(40);
            byte[] nameInByte = new byte[100];
            nameInByte = name.getBytes();
            raf.write(nameInByte,0,40);

            //write lastname
            lastname += " ".repeat(50);
            nameInByte = lastname.getBytes();
            raf.write(nameInByte,0,50);

            //write address
            address += " ".repeat(20);
            nameInByte = address.getBytes();
            raf.write(nameInByte,0,20);

            //write gpa
            raf.writeDouble(gpa);

            stid = 66130500022L;
            name = "Jennifer";
            lastname = "Saekim";
            address = "Ladprow";
            gpa = 2.5;
            //8+40+50+20+8
            //write student id
            raf.writeLong(stid);

            //write name
            name += " ".repeat(40);
            nameInByte = name.getBytes();
            raf.write(nameInByte,0,40);

            //write lastname
            lastname += " ".repeat(50);
            nameInByte = lastname.getBytes();
            raf.write(nameInByte,0,50);

            //write address
            address += " ".repeat(20);
            nameInByte = address.getBytes();
            raf.write(nameInByte,0,20);

            //write gpa
            raf.writeDouble(gpa);

            raf.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        readRAF("sample.dat");
    }


    public static void readRAF(String filePath) {
        RandomAccessFile fPointer = null;
        try {
            fPointer = new RandomAccessFile(filePath,"r");
            fPointer.seek(8+40+50+20);
            double g = fPointer.readDouble();
            System.out.println(g);
            fPointer.seek(8);
            byte[] name = new byte[200];
            fPointer.read(name,0,40);
            System.out.println(new String(name));

            fPointer.seek(8+40);
            fPointer.read(name,0,50);
            System.out.println(new String(name));

            fPointer.seek(8+40+50);
            fPointer.read(name,0,20);
            System.out.println(new String(name));

            fPointer.seek(8);
            for(int i=0; fPointer.getFilePointer() < fPointer.length();i++){
                fPointer.read(name,0,40);
                System.out.println((i+1)+" : "+new String(name));
                fPointer.seek(126*(i+1)+8);

            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
