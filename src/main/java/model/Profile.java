package model;

import javafx.beans.property.SimpleStringProperty;
import model.exceptions.KMLException;
import model.exceptions.LoadProfileException;
import model.exceptions.SaveProfileException;

import java.io.*;

public class Profile {

    private SimpleStringProperty profilePath = new SimpleStringProperty();

    public String getProfilePath() {
        return profilePath.get();
    }

    public SimpleStringProperty profilePathProperty() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath.set(profilePath);
    }

    /**
     * Метод загружает данные из файла профиля в программу
     * */
    public void loadProfile() throws LoadProfileException {

        //очищаем список команд перед загрузкой профиля из файла
        DataModel.getDataModel().clear();

        KMLParser kmlParser = new KMLParser();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(profilePath.get()))) {

            //читаем строки
            while (true){
                String line = bufferedReader.readLine();
                //выход из чтения строк
                if (line == null || line.equals("---END---")){
                    break;
                }

                //загружаем в датамодел преобразованные строки из файла в команды
                DataModel.getDataModel().getCommands().add(kmlParser.stringToObject(line));
            }

        } catch (KMLException exception) {
            throw new LoadProfileException(exception.getMessage());
        } catch (Exception otherException){

            String error = "Не известная ошибка при чтении файла профиля: "
                    + "\n\tтип ошибки: " + otherException.getClass().getName()
                    + "\n\tкласс: " + otherException.getStackTrace()[0].getClassName()
                    + "\n\tстрока: " + otherException.getStackTrace()[0].getLineNumber();

            throw new LoadProfileException(error);
        }
    }

    /**
     * Метод сохранения профиля в файл
     * */
    public void saveProfile() throws SaveProfileException {

        KMLParser kmlParser = new KMLParser();
        try (FileWriter fileWriter = new FileWriter(profilePath.get())) {

            //записываем все команды, предварительно преобразовав в строку
            for(Command cmd : DataModel.getDataModel().getCommands()){
                fileWriter.write(kmlParser.objectToString(cmd) + "\n");
            }

            //в конец файла записываем маркер конца файла
            fileWriter.write("---END---");

        } catch (KMLException exception) {
            throw new SaveProfileException(exception.getMessage());
        } catch (Exception otherException){

            String error = "Не известная ошибка при записи файла профиля: "
                    + "\n\tтип ошибки: " + otherException.getClass().getName()
                    + "\n\tкласс: " + otherException.getStackTrace()[0].getClassName()
                    + "\n\tстрока: " + otherException.getStackTrace()[0].getLineNumber();

            throw new SaveProfileException(error);
        }

    }
}
