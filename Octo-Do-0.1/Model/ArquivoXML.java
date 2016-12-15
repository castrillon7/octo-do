package Model;

import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Classe gen�rica para ser utilizada na realiza��o da persist�ncia do projeto.
 * Permite a cria��o de DAOs (Data Access Objects). Deve-se instanciar o par�metro
 * de tipo de acordo com a classe do modelo sendo persistida.
 */
public class ArquivoXML<Tipo>
{
  private ArrayList<Tipo> lista = new ArrayList<>();
  private String nomeArquivo;

  public ArquivoXML(String nomeArquivo)
  {
    this.nomeArquivo = nomeArquivo;
  }

  public ArrayList<Tipo> getLista() {
    return lista;
  }

  public void adiciona(Tipo tipo)
  {
    lista.add(tipo);
  }

  public void remove(Tipo tipo)
  {
    lista.remove(tipo);
  }

  public void escreveXMLCliente()
  {
    try {
      XMLEncoder encoder = null;

      try {
        encoder = new XMLEncoder(new FileOutputStream(nomeArquivo));
        encoder.writeObject(lista);

      } finally {
        if(encoder != null)
          encoder.close();
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  @SuppressWarnings("unchecked")
  public void leXMLCliente() {

    try {
      XMLDecoder decoder = null;

      try {
        decoder = new XMLDecoder(new FileInputStream(nomeArquivo));
        lista = (ArrayList<Tipo>) decoder.readObject();
      
      } finally {
        if(decoder != null)
          decoder.close();
      }
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
  
  @SuppressWarnings("unchecked")
  public void substituiTarefa(int index, Tipo t) {

    try {
      XMLDecoder decoder = null;

      try 
      {
        decoder = new XMLDecoder(new FileInputStream(nomeArquivo));
        lista = (ArrayList<Tipo>) decoder.readObject();
        lista.set(index, t);
      } 
      finally 
      {
        if(decoder != null)
          decoder.close();
      }
    } 
    catch (IOException e) {System.out.println(e.getMessage());}
  }
}