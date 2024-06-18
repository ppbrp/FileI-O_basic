import java.io.*;
import java.util.StringTokenizer;

public class SequentialFileExample {
    public static void main(String[] args) {
//        readFile("int103.txt");
        System.out.println("=================");
//        readFileByToken("int103.txt");
        readBigFile("big.txt");
    }

    public static void readBigFile(String filePath) {
        int count = 0;
        try {
            FileReader file = new FileReader(filePath);
            BufferedReader fileRead = new BufferedReader(file);
            StreamTokenizer token = new StreamTokenizer(fileRead);
            while (token.ttype != StreamTokenizer.TT_EOF) {
                if (token.ttype == StreamTokenizer.TT_WORD
                        && token.sval.equalsIgnoreCase("Sherlock")) {
                    token.nextToken();
                    if(token.ttype == StreamTokenizer.TT_WORD
                            && token.sval.equalsIgnoreCase("Holmes")) {
                        count++;
                    }
                }
                token.nextToken();
            }
            System.out.println("Sherlock Holmes: "+count);
            file.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FileWriter fw = null;
        try {
            int sherlock = count;
            fw = new FileWriter("lab2_66130500063.txt");
            BufferedWriter writer = new BufferedWriter(fw);
            writer.write("Pongboripat Jangnok, 66130500063 \n");
            writer.write("There are " + sherlock + " times of Sherlock Holmes.");
            writer.flush();
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                fw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void readFileByToken(String filePath) {
        try {
            FileReader f = new FileReader(filePath);
            BufferedReader fileRead = new BufferedReader(f);
            StreamTokenizer token = new StreamTokenizer(fileRead);
            int type;
            type = token.nextToken();
            for (int i = 0; i < 10; ) {
                if (token.ttype == StreamTokenizer.TT_WORD) {
                    i++;
                    System.out.println(i + " " + token.sval + " ");
                }
                token.nextToken();
            }
            while (token.ttype != StreamTokenizer.TT_EOF) {
                if (token.ttype == StreamTokenizer.TT_WORD
                        && token.sval.contains("ID")) {
                    while (token.ttype != StreamTokenizer.TT_NUMBER) {
                        token.nextToken();
                    }
                    System.out.println(String.format("%.0f", token.nval));
                    break;
                }
                token.nextToken();
            }

            f.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public static void readFile(String filePath) {
        try {
            FileReader file = new FileReader(filePath);
            BufferedReader fileRead = new BufferedReader(file);
            String content = "";
            while ((content = fileRead.readLine()) != null) {
                if (content.contains("ID")) {
                    String[] words = content.split(" ");
                    for (int i = 0; i < words.length; i++) {
                        if (words[i].startsWith("66")) {
                            System.out.println(words[i]);
                        }
                    }
                }
            }
            file.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
