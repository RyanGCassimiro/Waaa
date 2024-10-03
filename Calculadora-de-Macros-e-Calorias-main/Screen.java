import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;

public class Screen extends JFrame {

    private JTextField pesoField, alturaField, idadeField;
    private JComboBox<String> sexoCombo, atividadeCombo, objetivoCombo;
    private JButton calcularButton;
    private JLabel resultadoLabel;

    public Screen() {
        setTitle("Calculadora de Macros");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Adicionar o título
        JLabel tituloLabel = new JLabel("Calculadora de Macronutrientes");
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 18));
        tituloLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(tituloLabel, gbc);

        // Resetar o gridwidth para os outros componentes
        gbc.gridwidth = 1;

        // Adicionar os campos da interface
        addComponent(new JLabel("Peso (kg):"), 0, 1, gbc);
        pesoField = new JTextField(10);
        setCharacterLimit(pesoField, 5); // Limitar a 5 caracteres
        addComponent(pesoField, 1, 1, gbc);

        addComponent(new JLabel("Altura (cm):"), 0, 2, gbc);
        alturaField = new JTextField(10);
        setCharacterLimit(alturaField, 5); // Limitar a 5 caracteres
        addComponent(alturaField, 1, 2, gbc);

        addComponent(new JLabel("Idade:"), 0, 3, gbc);
        idadeField = new JTextField(10);
        setCharacterLimit(idadeField, 3); // Limitar a 3 caracteres
        addComponent(idadeField, 1, 3, gbc);

        addComponent(new JLabel("Sexo:"), 0, 4, gbc);
        sexoCombo = new JComboBox<>(new String[]{"Masculino", "Feminino"});
        addComponent(sexoCombo, 1, 4, gbc);

        addComponent(new JLabel("Nível de Atividade:"), 0, 5, gbc);
        atividadeCombo = new JComboBox<>(new String[]{"Sedentário", "Levemente Ativo", "Moderadamente Ativo", "Muito Ativo", "Extremamente Ativo"});
        addComponent(atividadeCombo, 1, 5, gbc);

        addComponent(new JLabel("Objetivo:"), 0, 6, gbc);
        objetivoCombo = new JComboBox<>(new String[]{"Perder Peso", "Manter Peso", "Ganhar Peso"});
        addComponent(objetivoCombo, 1, 6, gbc);

        calcularButton = new JButton("Calcular");
        gbc.gridwidth = 2;
        addComponent(calcularButton, 0, 7, gbc);

        resultadoLabel = new JLabel("Resultado aparecerá aqui");
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        addComponent(resultadoLabel, 0, 8, gbc);

        // Adicionar ação ao botão
        calcularButton.addActionListener(e -> calcular());
    }

    private void calcular() {
        try {
            // Coletar os dados da interface
            double peso = Double.parseDouble(pesoField.getText());
            int altura = Integer.parseInt(alturaField.getText());
            int idade = Integer.parseInt(idadeField.getText());
            char genero = sexoCombo.getSelectedItem().equals("Masculino") ? 'M' : 'F';
            String atividade = (String) atividadeCombo.getSelectedItem();
            String objetivo = (String) objetivoCombo.getSelectedItem();

            // Chamar a função de cálculo no ServicoCalculo
            String resultado = ServicoCalculo.calcularResultado(peso, altura, idade, genero, atividade, objetivo);

            // Exibir o resultado
            resultadoLabel.setText(resultado);

        } catch (NumberFormatException ex) {
            resultadoLabel.setText("<html>"
                    + "Por favor, insira valores válidos. <br>"
                    + "Não use vírgula (,), utilize um ponto (.). <br>"
                    + "Além disso, não use valores negativos. <br>"
                    + "</html>");
        }
    }

    // Método para aplicar o filtro de limite de caracteres
    private static void setCharacterLimit(JTextField textField, int limit) {
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
                if (fb.getDocument().getLength() + string.length() <= limit) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (fb.getDocument().getLength() - length + text.length() <= limit) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }

    private void addComponent(Component component, int x, int y, GridBagConstraints gbc) {
        gbc.gridx = x;
        gbc.gridy = y;
        add(component, gbc);
    }

}
