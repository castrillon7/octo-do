package View;
import java.awt.EventQueue;
import java.io.*;
import javax.swing.JFrame;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import Control.ControleTarefa;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import java.awt.CardLayout;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import java.awt.Font;

public class Tela_Principal {

  private JFrame frame;
  private ControleTarefa controleTarefa = new ControleTarefa("Listas/default.xml", true);
  private ControleTarefa controleStats = new ControleTarefa("Listas/default.xml", false);

  /**
   * Launch the application.
   */
  public static void main(String[] args) {

    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try 
        {
          Tela_Principal window = new Tela_Principal();
          window.frame.setVisible(true);
        } 
        catch (Exception e) {e.printStackTrace();}
      }
    });
  }

  /**
   * Create the application.
   * @throws IOException 
   */
  public Tela_Principal() throws IOException {
	  initialize();
  }
  
  /**
   * Initialize the contents of the frame.
   * @throws IOException 
   */
  private void initialize() throws IOException {
    //Manually created
    DefaultListModel<String> active_tasks = controleTarefa.readTasks();
    DefaultListModel<String> finished_tasks = new DefaultListModel<String>();
    
    //AUTO-Generated
    frame = new JFrame();
    frame.setResizable(false);
    frame.setBounds(100, 100, 350, 380);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    CardLayout telas = new CardLayout(0, 0);
    frame.getContentPane().setLayout(telas);
    
    //Cria as telas
    JPanel panelTelaPrincipal = new JPanel();
    frame.getContentPane().add(panelTelaPrincipal, "name_128576865755200");
    panelTelaPrincipal.setLayout(null);
    
    //TELA PRINCIPAL
        JList<String> listOfTasks = new JList<String>();
        listOfTasks.setBounds(6, 6, 226, 346);
        panelTelaPrincipal.add(listOfTasks);
        listOfTasks.setModel(active_tasks);
        
        //BUTTONS
        JButton btnNovoEvento = new JButton("Novo Evento");
        btnNovoEvento.setBounds(244, 270, 100, 35);
        panelTelaPrincipal.add(btnNovoEvento);
        
        JButton btnNovaTarefa = new JButton("Nova Tarefa");
        btnNovaTarefa.setBounds(244, 317, 100, 35);
        panelTelaPrincipal.add(btnNovaTarefa);
        
        JButton btnStats = new JButton("Stats");
        btnStats.setBounds(244, 6, 100, 35);
        panelTelaPrincipal.add(btnStats);
        
        JButton btnConcluir = new JButton("Concluir");
        btnConcluir.setBounds(244, 223, 100, 35);
        panelTelaPrincipal.add(btnConcluir);
        
        JButton btnEditar = new JButton("Editar");
        btnEditar.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            int selectedIndex = listOfTasks.getSelectedIndex();
            //PopUp
            final JFrame popUp = new JFrame();
            popUp.pack();
            String edited_task = (JOptionPane.showInputDialog(popUp, "Editar tarefa.", null)).trim();
            
            if(edited_task != null && selectedIndex != -1)
            {
              controleTarefa.editTask(active_tasks.getElementAt(selectedIndex), edited_task);
              active_tasks.set(selectedIndex, edited_task);
              listOfTasks.setModel(active_tasks);
            }
          }
        });
        btnEditar.setBounds(244, 176, 100, 35);
        panelTelaPrincipal.add(btnEditar);
        
        btnConcluir.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            int selectedIndex = listOfTasks.getSelectedIndex();
            if (selectedIndex != -1) 
            {
              String complete_tarefa = active_tasks.elementAt(selectedIndex);
              controleTarefa.completeTask(complete_tarefa);
              active_tasks.remove(selectedIndex);
              finished_tasks.addElement(complete_tarefa);
            }
          }
        });
        
        btnStats.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            telas.next(frame.getContentPane());
          }
        });
        
        btnNovaTarefa.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            final JFrame popUp = new JFrame();
            popUp.pack();
            String new_task = (JOptionPane.showInputDialog(popUp, "Adicionar tarefa.", null)).trim();
            if(new_task != null)
            {
              controleTarefa.writeTask(new_task);
              active_tasks.addElement(new_task);
              listOfTasks.setModel(active_tasks);
            }
          }
        });
        
    //TELA STATS  
    JPanel panelTelaStats = new JPanel();
    panelTelaStats.setLayout(null);
    frame.getContentPane().add(panelTelaStats, "name_128576873157401");
    
    JList<String> listOfStats = new JList<String>();
    listOfStats.setFont(listOfStats.getFont().deriveFont(listOfStats.getFont().getStyle() | Font.ITALIC));
    listOfStats.setBounds(6, 43, 338, 309);
    panelTelaStats.add(listOfStats);
    listOfStats.setModel(finished_tasks);
        
      JComboBox<String> comboBoxTimespan = new JComboBox<String>();
      JComboBox<String> comboBoxType = new JComboBox<String>();
        
        //Handler TIMESPAM CB
        comboBoxTimespan.addItemListener(new ItemListener() {
          public void itemStateChanged(ItemEvent e) {
            int days = 0;
            String tipo = (String) comboBoxType.getSelectedItem();
            String tempo = (String) comboBoxTimespan.getSelectedItem();
            if(tipo.equalsIgnoreCase("Números") || tipo.equalsIgnoreCase("Concluídas"))
            {
              if(tempo.equalsIgnoreCase("1 Semana")) { days = 7; }
              else if(tempo.equalsIgnoreCase("15 Dias")) { days = 15; }
              else if(tempo.equalsIgnoreCase("1 Mês")){ days = 30; }
              finished_tasks.clear();
            }
            DefaultListModel<String> aux_list = controleStats.readTimedTasks(days * 86400);
            for(int i = 0; i < aux_list.size(); i++)
              finished_tasks.addElement(aux_list.getElementAt(i));
            
            listOfStats.setModel(finished_tasks);
          }
        });
        comboBoxTimespan.setModel(new DefaultComboBoxModel<String>(new String[] {"-Tempo-", "1 Semana", "15 Dias", "1 Mês"}));
        comboBoxTimespan.setBounds(229, 6, 115, 33);
        panelTelaStats.add(comboBoxTimespan);
        
        //Handler TYPE CB
        comboBoxType.addItemListener(new ItemListener() {
          public void itemStateChanged(ItemEvent e) {
            int days = 0;
            String tipo = (String) comboBoxType.getSelectedItem();
            String tempo = (String) comboBoxTimespan.getSelectedItem();
            DefaultListModel<String> aux_list = new DefaultListModel<String>();
            
            if(tempo.equalsIgnoreCase("1 Semana")) { days = 7; }
            else if(tempo.equalsIgnoreCase("15 Dias")) { days = 15; }
            else if(tempo.equalsIgnoreCase("1 Mês")){ days = 30; }
            
            if(tipo.equalsIgnoreCase("Concluídas"))
            {
              aux_list = controleStats.readTimedTasks(days * 86400);
              finished_tasks.clear();
              for(int i = aux_list.size() - 1; i >= 0; i--)
                finished_tasks.addElement(aux_list.getElementAt(i));
            }
            else if(tipo.equalsIgnoreCase("Números"))
            {
              aux_list = controleStats.readTimedTasks(days * 86400);
              finished_tasks.clear();
            }
            
            
            listOfStats.setModel(finished_tasks);
          }
        });
        comboBoxType.setModel(new DefaultComboBoxModel<String>(new String[] {"-Tipo-", "Números", "Concluídas"}));
        comboBoxType.setBounds(113, 6, 115, 33);
        panelTelaStats.add(comboBoxType);
        
        JButton btnBack = new JButton("Voltar");
        btnBack.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            telas.previous(frame.getContentPane());;
          }
        });
        btnBack.setBounds(6, 6, 100, 33);
        panelTelaStats.add(btnBack);
  }
}
