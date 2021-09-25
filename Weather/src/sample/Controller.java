package sample;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.JSONObject;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField city;

    @FXML
    private Button getData;

    @FXML
    private Text temp;

    @FXML
    private Text temp_feels;

    @FXML
    private Text temp_max;

    @FXML
    private Text temp_min;

    @FXML
    private Text pressure;

    @FXML
    private Text wind;

    @FXML
    void initialize() {
//        city.setOnMouseClicked(e -> city.selectAll());
        getData.setOnAction(event -> {
            String getUserCity = city.getText().trim();
            if(!getUserCity.equals("")) { // Если данные не пустые
                // Получаем данные о погоде с сайта openweathermap
            String outPut = getUrlContent("http://api.openweathermap.org/data/2.5/weather?q="+getUserCity+"&appid=43abcdac5b798601b57f8e9aba14a9b1&units=metric");
            System.out.println(outPut);
                if (!outPut.isEmpty()) { // Нет ошибки и такой город есть
                    JSONObject obj = new JSONObject(outPut);
                    // Обрабатываем JSON и устанавливаем данные в текстовые надписи
                    temp.setText("Температура: " + obj.getJSONObject("main").getDouble("temp"));
                    temp_feels.setText("Ощущается: " + obj.getJSONObject("main").getDouble("feels_like"));
                    temp_max.setText("Максимум: " + obj.getJSONObject("main").getDouble("temp_max"));
                    temp_min.setText("Минимум: " + obj.getJSONObject("main").getDouble("temp_min"));
                    pressure.setText("Давление: " + obj.getJSONObject("main").getDouble("pressure"));
                    wind.setText("Ветер: "+ obj.getJSONObject("wind").getDouble("speed"));
                }
            }
             city.clear();
            initialize();
        });
    }

    private static String getUrlContent(String urlAdress){
        StringBuffer content = new StringBuffer();
        try {
            URL url = new URL(urlAdress);
            URLConnection urlConnection = url.openConnection();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;

            while ((line =bufferedReader.readLine()) != null){
                content.append(line+"\n");
            }
            bufferedReader.close();
        }
        catch (Exception e){
            System.out.println("Такой город не был найден!");
        }
        return content.toString();
    }
}


