package dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class ContactDTO {

    String address;
    String description;
    String email;
    int id;
    String lastName;
    String name;
    String phone;


}


