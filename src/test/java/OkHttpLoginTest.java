import com.google.gson.Gson;
import dto.AuthRequestDTO;
import dto.ErrorDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class OkHttpLoginTest {

    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @Test
    public void loginTestPositive() throws IOException {

        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .email("pinhas@gmail.com")
                .password("Pinhas123$").build();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDTO), JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        String responseJson = response.body().string();
//        System.out.println("responseJson: "+responseJson);
        Assert.assertTrue(response.isSuccessful());
    }


    @Test
    public void loginTestNegativeWrongEmail() throws IOException {

        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .email("pinhasgmail.com")
                .password("Pinhas123$").build();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDTO), JSON);
        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);

        Assert.assertEquals(errorDTO.getCode(), 400);
//        Assert.assertFalse(response.isSuccessful());
    }

    @Test
    public void loginTestNegativeWrongPassword() throws IOException {

        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .email("pinhas@gmail.com")
                .password("pinhas123$").build();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDTO), JSON);
        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);

        Assert.assertEquals(errorDTO.getCode(), 400);
    }


    @Test
    public void loginTestNegativeUnregisteredUser() throws IOException {

        AuthRequestDTO requestDTO = AuthRequestDTO.builder()
                .email("papapap@gmail.com")
                .password("Papap123$").build();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDTO), JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        ErrorDTO errorDTO = gson.fromJson(response.body().string(), ErrorDTO.class);

        Assert.assertEquals(errorDTO.getCode(), 401);
    }


}
