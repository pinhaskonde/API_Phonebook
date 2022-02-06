import com.google.gson.Gson;
import dto.ContactDTO;
import dto.GetAllContactsDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class OkHttpDeleteContact {

    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();

    String authorization = "Authorization";
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InBpbmhhc0BnbWFpbC5jb20ifQ.fPovUT2VY3QbeyAhoYp9jEYSJaNxMd1hNTYcPj-8O30";

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @Test
    public void deleteContactTestPositive() throws IOException {

        ContactDTO contactDTO = new ContactDTO().builder()
                .name("Benni3")
                .lastName("Vasserman3")
                .email("benvas3@gmail.com")
                .phone("111135510")
                .address("Vangog str.")
                .description("next description")
                .build();
        RequestBody requestBody = RequestBody.create(gson.toJson(contactDTO), JSON);
        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact")
                .post(requestBody)
                .addHeader(authorization, token)
                .build();
        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());

        ContactDTO contact = gson.fromJson(response.body().string(), ContactDTO.class);

//        System.out.println("Contact created: " + contact.getName());

        int id = contact.getId();
        RequestBody requestBodyId = RequestBody.create(gson.toJson(contactDTO), JSON);
        Request requestId = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact/" + id)
                .delete(requestBody)
                .addHeader(authorization, token)
                .build();

        Response responseDel = client.newCall(requestId).execute();
//        System.out.println("Contact: "+ contact.getName()+" - was deleted");

        Assert.assertEquals(responseDel.code(), 200);
    }


}
