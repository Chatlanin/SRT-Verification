import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
 
/**
 * Created by Chat on 24.07.2016.
 */
public  class MyFrame extends JFrame {
    public MyFrame() {
        super("Субтитры, прошедшие валидацию");
        createTable();
    }

    /**
     * Метод отображает в виде таблицы параметры, которые былы сериализованы в методе
     * chooseButton класса FileChooser
     */
    public void createTable()
    {
        // Получаем данные от класса Serializer
        TableModel model = new MyTableModel(Serializer.getInstance().readObject());
        // Создаем таблицу и оределяем основные параметры отображения
        JTable table = new JTable(model);
        JLabel verifyLabel = new JLabel("Валидация завершена успешно. "+
                "Данные сохранены в директории: "+Serializer.fileNAME+"\\ValidSubtitles.ser");

        JFrame.setDefaultLookAndFeelDecorated(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(verifyLabel, BorderLayout.SOUTH);
        getContentPane().add(new JScrollPane(table));
        setPreferredSize(new Dimension(260, 220));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Т.к. субтитры у нас хранятся в виде набора обьектов, которые имеют свои поля,
     * необходимо изменить некоторые методы TableModel для корректного отображения данных
     */
    public class MyTableModel implements TableModel {

        private Set<TableModelListener> listeners = new HashSet<TableModelListener>();

        private List<Subtitles> list;

        public MyTableModel(List<Subtitles> beans) {
            this.list = beans;
        }
        // Стандартный метод добавления
        public void addTableModelListener(TableModelListener listener) {
            listeners.add(listener);
        }
        // Метод возвращает тип поля объекта Subtitles (все поля строковые)
        public Class<?> getColumnClass(int columnIndex) {
            return String.class;
        }
        //  Метод возвращает кол-во столбцов
        public int getColumnCount() {
            return 4;
        }
        // Метод возвращает название столбцов по индексу
        public String getColumnName(int columnIndex) {
            switch (columnIndex) {
                case 0:
                    return "№";
                case 1:
                    return "TimeStart";
                case 2:
                    return "TimeEnd";
                case 3:
                    return "Text";
            }
            return "";
        }
        // Метод  возвращает количество строк, которое будет отображаться в таблице
        public int getRowCount() {
            return list.size();
        }
        // Метод  отвечает за то, какие данные в каких ячейках таблицы будут отображаться
        public Object getValueAt(int rowIndex, int columnIndex) {
            Subtitles bean = list.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return bean.getNum();
                case 1:
                    return bean.getTimeStart();
                case 2:
                    return bean.getTimeEnd();
                case 3:
                    return bean.getText();
            }
            return "";
        }
        // Метод определяет, может ли пользователь редактировать ячейку
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
        // Стандартный метод удаления
        public void removeTableModelListener(TableModelListener listener) {
            listeners.remove(listener);
        }
        // Метод записывает отредактированное пользователем значение в таблицу
        public void setValueAt(Object value, int rowIndex, int columnIndex) {

        }

    }
}