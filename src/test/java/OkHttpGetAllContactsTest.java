import com.google.gson.Gson;
import dto.AuthResponseDTO;
import dto.ContactDTO;
import dto.GetAllContactsDTO;
import okhttp3.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class OkHttpGetAllContactsTest {

    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    @Test
    public void getAllContactsTest() throws IOException {
        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact")
                .addHeader("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InBpbmhhc0BnbWFpbC5jb20ifQ.fPovUT2VY3QbeyAhoYp9jEYSJaNxMd1hNTYcPj-8O30").build();

        Response response = client.newCall(request).execute();
        AuthResponseDTO responseDTO = new AuthResponseDTO();
        Assert.assertTrue(response.isSuccessful());

        GetAllContactsDTO contactsList = gson.fromJson(response.body().string(), GetAllContactsDTO.class);

        for (ContactDTO contact : contactsList.getContacts()) {
            System.out.println(contact.getId() + " *** " + contact.getEmail());
            System.out.println("----------------------------------------");
        }
    }

    @Test
    public void addNewContactTest() throws IOException {

        ContactDTO contactDTO = new ContactDTO().builder()
                .name("Benni")
                .lastName("Vasserman")
                .email("benvas@gmail.com")
                .phone("77712010")
                .address("Erushalmi")
                .description("next description")
                .build();
        RequestBody requestBody = RequestBody.create(gson.toJson(contactDTO), JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact")
                .post(requestBody)
                .addHeader("Authorization", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InBpbmhhc0BnbWFpbC5jb20ifQ.fPovUT2VY3QbeyAhoYp9jEYSJaNxMd1hNTYcPj-8O30")
                .build();
        Response response = client.newCall(request).execute();

        Assert.assertTrue(response.isSuccessful());
    }


}
