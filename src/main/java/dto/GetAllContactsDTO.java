package dto;

import java.util.List;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString

public class GetAllContactsDTO {

    List<ContactDTO> contacts;


}
