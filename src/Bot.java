import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

  //создаем две константы, присваиваем им значения токена и имя бота соответсвтенно
  //вместо звездочек подставляйте свои данные
  final private String BOT_TOKEN = "387482437:AAHQkvtddWuzN_4jyQdvl_6Ax5XYay5El4o";
  final private String BOT_NAME = "ZerxgmBot";
  Storage storage;

  Bot()
  {
    storage = new Storage();
  }

  @Override
  public String getBotUsername() {
    return BOT_NAME;
  }

  @Override
  public String getBotToken() {
    return BOT_TOKEN;
  }

  @Override
  public void onUpdateReceived(Update update) {
    try{
      if(update.hasMessage() && update.getMessage().hasText())
      {
        //Извлекаем из объекта сообщение пользователя
        Message inMess = update.getMessage();
        //Достаем из inMess id чата пользователя
        String chatId = inMess.getChatId().toString();
        //Получаем текст сообщения пользователя, отправляем в написанный нами обработчик
        String response = parseMessage(inMess.getText());
        String responsePhoto = parseMessage(response);
        //Создаем объект класса SendMessage - наш будущий ответ пользователю
        SendMessage outMess = new SendMessage();
        SendPhoto outPhoto = new SendPhoto();
        //Добавляем в наше сообщение id чата а также наш ответ
        outMess.setChatId(chatId);
        outMess.setText(response);
        //Отправка в чат сообщения с фото
        try {

          outPhoto.setPhoto(responsePhoto,);      //сюды нужно доставить название и путь к фото
          execute(outPhoto);
        } catch (FileNotFoundException e) {
          e.printStackTrace();
        }

        //Отправка в чат
        execute(outMess);
      }
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }
  public String parseMessage(String textMsg) {
    String response;

    //Сравниваем текст пользователя с нашими командами, на основе этого формируем ответ
    if(textMsg.equals("/start"))
      response = "Приветствую, у бота есть много всего интересного, что вам предложить? Отправь /get, чтобы получить весь список";
    else if(textMsg.equals("/get"))
      response = storage.getList();
    else if(textMsg.equals("/rose"))
       response = storage.getRoseData();        // это строка а у меня Map, как передать 2 строки
     // response = storage.getRosePhoto();
    else if(textMsg.equals("/eggs"))
    response = storage.getEggsData();
    else if(textMsg.equals("/meat"))
    response = storage.getMeatData();
    else
      response = "Сообщение не распознано";

    return response;
  }

}
