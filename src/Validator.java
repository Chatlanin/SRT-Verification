import java.io.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
 
/**
 * Created by Chat on 23.07.2016.
 */

public class Validator {
     // Массив хранит информацию обо всех, прошедших валидацию, субтитрах
    public static ArrayList<Subtitles> subtitlesList = new ArrayList<Subtitles>();

    public static void main(String[] args) throws IOException {
        // Запускаем окно выбора файла
        new FileChooser();
    }

    /**
     * Метод проверяет, является ли переданная строка числом
     * @param testString
     * @return
     */
    public static boolean isDigit(String testString) {
        Pattern isDigit = Pattern.compile("\\d+");
        Matcher m = isDigit.matcher(testString);
        return m.matches();
    }
    /**
     * К сожалению, в методе проверки корректности времени не приумал ничего более оригинального,
     * чем перебор каждого элемента в строке и проверка эл-та на соответствие формату, надеюсь что
     * ничьи глаза от этого не пострадают =)
     */
    public static boolean isCorrectTime(String testString) {
        try {
            /* Разделим всю строку времени на 2 строки и проверим, соответсвие числовому формату и соответствие
            разделительных знаков
             */
            String[] timeStartAndEnd = testString.split(" --> ");
            char[] timeStart = timeStartAndEnd[0].toCharArray();
            char[] timeEnd = timeStartAndEnd[1].toCharArray();
            for (int i = 0; i < 12; i++) {
                if (i == 2 | i == 5 | i == 8)
                {       if (i!=8) {
                            if (timeStart[i] != ':') return false;
                            if (timeEnd[i] != ':') return false;
                        }
                        else
                        {
                            if (timeStart[i] != ',') return false;
                            if (timeEnd[i] != ',') return false;
                         }

                }
                else {
                    if (!(Character.isDigit(timeStart[i]))) return false;
                    if (!(Character.isDigit(timeEnd[i]))) return false;
                }
            }
            /*
            Если первый этап проверки пройден успешно, проверим, соответствуют ли числовые значения
            стандартному формату времени
             */
            String[] timeStartAndAndTwo = testString.split(" --> |,|:");
            for (int i=0;i<8;i++)
            {
                if (i == 0 | i == 4) {
                    if (Integer.parseInt(timeStartAndAndTwo[i])>24) return false;
                }
                if (i == 1 | i == 2 | i == 5 | i == 6) {
                    if (Integer.parseInt(timeStartAndAndTwo[i])>60) return false;
                }
            }
            return true;
            /*
            В случае любого не совпадения формат не является корректным
             */
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Метод читает строки из файла и проверяет их на корректность.
     * В случае успешной проверки создает обьект типа Subtitles и передает в него 4 параметра:
     * номер субтитра, время начала субтитра, время окончания субтитра, содержание субтитра.
     * @param fileName
     */
    public static void validateInputFile(String fileName) {
        String num; // номер субтитра
        String[] timeStartAndEnd; // массив для получения времени начала и окончания сутитра
        String timeStart; // время начала сутитра
        String timeEnd; // время окончания субтитра
        String textLine; // вспомогательная строка
        String text = ""; // содержание субтитра
        Scanner inputFile = null;
        try {
            inputFile = new Scanner(new File(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (inputFile.hasNextLine()) {
            num = inputFile.nextLine();
            //Проверяем корректность номера субтитра
            if (isDigit(num)) {
                timeStart = inputFile.nextLine();
                // Проверяем корректность времени начала и окончания показа субтитров
                if (isCorrectTime(timeStart)) {
                    timeStartAndEnd = timeStart.split(" --> ");
                    timeStart = timeStartAndEnd[0];
                    timeEnd = timeStartAndEnd[1];
                    textLine = inputFile.nextLine();
                    text = "";
                    // Считываем текст субтитра, до тех пор, пока не попадется пустая строка
                    while (!(textLine.isEmpty())) {
                        text = text.concat(textLine);
                        try {
                            textLine = inputFile.nextLine();
                        } catch (NoSuchElementException e) {
                            break;
                        }
                    }
                    subtitlesList.add(new Subtitles(num, timeStart, timeEnd, text));
                }
            }
        }
    }

}

/**
 * Обект, который хранит данные об одном субтитре
 */
class Subtitles implements Serializable{
    // Геттеры для класса MyTableModel (построения табличного отображения)
    public String getNum() {
        return num;
    }
    public String getText() {
        return text;
    }
    public String getTimeEnd() {
        return timeEnd;
    }
    public String getTimeStart() {
        return timeStart;
    }

    String num;
    String timeStart;
    String timeEnd;
    String text;
    Subtitles(String num, String timeStart, String timeEnd, String text){
        this.num = num;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.text = text;
    }
}
