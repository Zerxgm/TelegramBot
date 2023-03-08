import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;

public class Storage {

  private ArrayList<String> quoteList;
  private ArrayList<String> eggsList;
  private ArrayList<String> roseList;
  private ArrayList<String> fotoList;
  Storage()
  {
    quoteList = new ArrayList<>();
    quoteList.add("Розы - набери /rose.");
    quoteList.add("Яйцо - /eggs");
    quoteList.add("Мясо птицы - /meat");
  }

  String getList()
  {
    StringBuilder bld = new StringBuilder();
    String str = null;
    //получаем случайное значение в интервале от 0 до самого большого индекса
    //int randValue = (int)(Math.random() * quoteList.size());
    //Из коллекции получаем цитату со случайным индексом и возвращаем ее
    for (int i = 0; i < quoteList.size(); i++) {
      bld.append(quoteList.get(i));
      bld.append("\n");
    }
    str = bld.toString();
    return str; 
  }

   public Map getRoseData() {
    String connectionUrl =
        "jdbc:sqlserver://localhost;databaseName=BaseForBot;"
            + "user=user1;"
            + "password=sa;"
            + "loginTimeout=10;"
            + "encrypt=false";
    StringBuilder bld1 = new StringBuilder();
    String str = null;
    String basestr = null;
    String basefoto = null;
    roseList = new ArrayList<>();
    fotoList = new ArrayList<>();
    ResultSet resultSet = null;
    ResultSet resultSetFoto = null;
    try (Connection connection = DriverManager.getConnection(connectionUrl);
        Statement statement = connection.createStatement()) {
      // System.out.println("Connected!");
      String selectSql = "SELECT id, name, quantity, cost FROM good where type = 'Цветы'";

      resultSet = statement.executeQuery(selectSql);



      while (resultSet.next()) {
        basestr = resultSet.getString(1) + " в наличии: " + resultSet.getString(2) + "шт. " + "Стоимость: " + resultSet.getString(3) + " руб.";
        roseList.add(basestr);
        String selectFoto = "SELECT path FROM foto f where id=" + resultSetFoto.getString(0);
        resultSetFoto = statement.executeQuery(selectFoto);
        basefoto = resultSetFoto.getString(0);
        fotoList.add(basefoto);
      }
    }
    // Handle any errors that may have occurred.
    catch (SQLException e) {
      e.printStackTrace();
    }
     Map resiveFoto = new HashMap<>();
    for (int i = 0; i < roseList.size(); i++) {
      for (int j = 0; j < fotoList.size(); j++) {
        String name = roseList.get(i);
        String foto = fotoList.get(j);
        resiveFoto.put(name,foto);

      }

     // bld1.append(resiveFoto);
     // bld1.append("\n");
    }

    //  str = bld1.toString();
      return resiveFoto;
  }

  String getEggsData() {
    String connectionUrl =
        "jdbc:sqlserver://localhost;databaseName=BaseForBot;"
            + "user=user1;"
            + "password=sa;"
            + "loginTimeout=10;"
            + "encrypt=false";
    StringBuilder bld1 = new StringBuilder();
    String str = null;
    String basestr = null;
    eggsList = new ArrayList<>();
    ResultSet resultSet = null;
    try (Connection connection = DriverManager.getConnection(connectionUrl);
        Statement statement = connection.createStatement()) {
      // System.out.println("Connected!");
      String selectSql = "SELECT name, quantity, cost FROM good";
      resultSet = statement.executeQuery(selectSql);


      while (resultSet.next()) {
        basestr = resultSet.getString(1) + " в наличии: " + resultSet.getString(2) + "шт. " + "Стоимость: " + resultSet.getString(3) + " руб.";
        eggsList.add(basestr);
      }
    }
    // Handle any errors that may have occurred.
    catch (SQLException e) {
      e.printStackTrace();
    }
    for (int i = 0; i < eggsList.size(); i++) {
      bld1.append(eggsList.get(i));
      bld1.append("\n");
    }

    str = bld1.toString();
    return str;
  }

  String getMeatData() {
    String str = null;
    return str;
  }

}
