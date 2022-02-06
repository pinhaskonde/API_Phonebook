import com.google.gson.Gson;
import dto.ContactDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class OkHttpAddContactTest {

    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    String authorization = "Authorization";
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InBpbmhhc0BnbWFpbC5jb20ifQ.fPovUT2VY3QbeyAhoYp9jEYSJaNxMd1hNTYcPj-8O30";

    @Test
    public void createContactTest() throws IOException {

        ContactDTO contactDTO = new ContactDTO().builder()
                .name("Bibi")
                .lastName("Vainshtein")
                .email("borrncel@gmail.com")
                .phone("11556790")
                .address("asdfgg")
                .description("123 description")
                .build();
        RequestBody requestBody = RequestBody.create(gson.toJson(contactDTO), JSON);
        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact")
                .post(requestBody)
                .addHeader(authorization, token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());

    }



}
