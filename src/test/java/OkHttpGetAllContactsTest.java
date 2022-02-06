import com.google.gson.Gson;
import dto.AuthResponseDTO;
import dto.ContactDTO;
import dto.GetAllContactsDTO;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class OkHttpGetAllContactsTest {

    Gson gson = new Gson();
    OkHttpClient client = new OkHttpClient();

    @Test
    public void getAllContactsTest() throws IOException {
        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/contact")
                .addHeader("Authorization","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InBpbmhhc0BnbWFpbC5jb20ifQ.fPovUT2VY3QbeyAhoYp9jEYSJaNxMd1hNTYcPj-8O30").build();

        Response response = client.newCall(request).execute();
        AuthResponseDTO responseDTO = new AuthResponseDTO();
        Assert.assertTrue(response.isSuccessful());

        GetAllContactsDTO contactsList =
                            gson.fromJson(response.body().string(),GetAllContactsDTO.class);

        for (ContactDTO contact: contactsList.getContacts()){
            System.out.println(contact.getId()+"***" + contact.getEmail());
            System.out.println("----------------------------------------");
        }



    }


}
