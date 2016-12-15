package Control;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

import Model.ArquivoXML;
import Model.Tarefa;

public class ControleTarefa {
  private Boolean type;
	private ArquivoXML<Tarefa> tarefaBD;
	
	public ControleTarefa(String path, Boolean type)
	{
	  super();
    this.type = type;
		tarefaBD = new ArquivoXML<Tarefa>(path);
	}

  public DefaultListModel<String> readTasks()
	{
	  tarefaBD.leXMLCliente();
	  DefaultListModel<String> list_tasks = new DefaultListModel<String>();
	  ArrayList<Tarefa> lista = tarefaBD.getLista();
	  int count = lista.size();
	  Tarefa t_aux = null;
	  
	  if(count > 0);
	    for(int i = 0; i < count; i++)
	    {
	      t_aux = lista.get(i);
	      if(t_aux.getStatus() == type)
	        list_tasks.addElement(t_aux.getNome());
	    }
	  return list_tasks;
	}
  
  public DefaultListModel<String> readTimedTasks(int time)
  {
    tarefaBD.leXMLCliente();
    DefaultListModel<String> list_tasks = new DefaultListModel<String>();
    ArrayList<Tarefa> lista = tarefaBD.getLista();
    int count = lista.size();
    Tarefa t_aux = null;
    
    if(count > 0);
      for(int i = 0; i < count; i++)
      {
        long aux = System.currentTimeMillis()/1000 - time;
        t_aux = lista.get(i);
        if(t_aux.getStatus() == type && t_aux.getData() > aux)
          list_tasks.addElement(t_aux.getNome());
      }
    return list_tasks;
  }
	
  public void completeTask(String task)
  {
    ArrayList<Tarefa> lista = tarefaBD.getLista();
    int count = lista.size();
    Tarefa t = null;
    if(count > 0);
      for(int i = 0; i < count; i++)
      {
        t = lista.get(i);
        if(task.equals(t.getNome())) 
        {
          tarefaBD.substituiTarefa(i, new Tarefa(t.getNome(), false , System.currentTimeMillis()/1000 ) );
          break;
        }
      }
    tarefaBD.escreveXMLCliente();
  }
  
  public void editTask(String task, String new_task)
  {
    ArrayList<Tarefa> lista = tarefaBD.getLista();
    int count = lista.size();
    Tarefa t = null;
    if(count > 0);
      for(int i = 0; i < count; i++)
      {
        t = lista.get(i);
        if(task.equals(t.getNome())) 
        {
          tarefaBD.substituiTarefa(i, new Tarefa(new_task, t.getStatus() , t.getData() ) );
          break;
        }
      }
    tarefaBD.escreveXMLCliente();
  }
	
	public void writeTask(String task)
	{
		Tarefa t = new Tarefa(task, true, System.currentTimeMillis()/1000);
		tarefaBD.adiciona(t);		
		tarefaBD.escreveXMLCliente();
	}

}
