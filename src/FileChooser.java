import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * Created by Chat on 24.07.2016.
 */
public class FileChooser extends JFrame {
    /**
     * Создаем окно, в котором добавляем кнопку выбора файла и устанавливаем
     * основные параметры отображения
     */
    public FileChooser() {
        super("Окно выбора");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(Box.createVerticalGlue());
        // Добавляем текстовую "подсказку"
        final JLabel label = new JLabel("Пожалуйста, выберите файл с субтитрами");
        label.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(label);
        // Добавляем кнопку
        JButton button = chooseButton(label);
        button.setAlignmentX(CENTER_ALIGNMENT);
        panel.add(button);
        // Устанавливаем размер и расположение окна
        panel.add(Box.createVerticalGlue());
        getContentPane().add(panel);
        setPreferredSize(new Dimension(260, 220));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    /**
     * Метод позволяет выбрать нужный нам файл для валидации.
     * В случае успеха метод сериализует массив субтитров в файл и отображает
     * субтитры в виде таблицы
     * @param label
     * @return
     */
    public JButton chooseButton(final JLabel label)
    {
        JButton button = new JButton("Выбрать файл");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileOpen = new JFileChooser();
                int ret = fileOpen.showDialog(null, "Открыть файл");
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileOpen.getSelectedFile();
                    Validator.validateInputFile(file.getAbsolutePath());
                    if (Validator.subtitlesList.size()==0)
                    {
                        label.setText("К сожалению, в выбранном файле субтитов не найдено.");
                    }
                    else
                    {
                        label.setText("Валидация пройдена успешно!");
                        try {
                            Serializer.getInstance().saveObject(Validator.subtitlesList, file.getParentFile());
                            Validator.subtitlesList.clear();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        new MyFrame();
                    }
                }
            }
        });
        return button;
    }
}

