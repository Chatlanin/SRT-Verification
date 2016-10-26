import java.io.*;
import java.util.ArrayList;

/**
 * Created by Chat on 24.07.2016. 
 */
public class Serializer {
    /**
     * Т.к. класс Serializer и его методы вызваются из нескольких классов программы (FileChooser, MyFrame)
     * применим паттерн Singleton для того, чтобы не создавать лишних объектов
     */
    private Serializer(){}
    private static final Serializer instance = new Serializer();// Переменная хранит ссылку на обьект класса
    public static String fileNAME; // Переменная содержит путь к файлу, выбранный пользователем

    /**
     * Метод сохраняет набор сутитров, прошедших валидацию в файл  ValidSubtitles.ser, который будет создан
     * в директории, выбранной пользователем при открытии файла.
     * @param subtitlesList
     * @param destination
     * @throws IOException
     */
    public void saveObject(ArrayList<Subtitles> subtitlesList, File destination) throws IOException {
        fileNAME = destination.toString();
        try {

            FileOutputStream fos = new FileOutputStream(fileNAME+"\\ValidSubtitles.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(subtitlesList);
            fos.flush();
            oos.flush();
            oos.close();
            fos.close();
        } catch (Exception ex) {
            System.out.print("Не удалось сохранить(");
        }
    }

    /**
     * Метод возвращает массив субтитров, которые получает из ранее сохраненного файла
     * @return
     */
    public ArrayList<Subtitles> readObject()
    {
        ArrayList<Subtitles> subtitlesList = new ArrayList<Subtitles>();
        try (FileInputStream fin = new FileInputStream(fileNAME+"\\ValidSubtitles.ser")){
            ObjectInputStream ois = new ObjectInputStream(fin);
           subtitlesList = (ArrayList<Subtitles>) ois.readObject();
        }
        catch (Exception e) {
            System.out.print("Что-то пошло не так...");
        }
     return subtitlesList;
    }

    /**
     * Метод возвращает ссылку на объект Serializer
     * @return
     */
    public static Serializer getInstance(){
        return instance;
    }
}
