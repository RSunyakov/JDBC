package ru.itis.hw1.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User implements Serializable {
   private String name;
   private String surname;
   private int age;
   private long passportNumber;
   private String dateOfIssue;
   private String email;


   public static byte[] serialize(User user) throws IOException {
      ByteArrayOutputStream out = new ByteArrayOutputStream();
      ObjectOutputStream os = new ObjectOutputStream(out);
      os.writeObject(user);
      return out.toByteArray();
   }

   public static User deserialize(byte[] data) throws IOException, ClassNotFoundException {
      ByteArrayInputStream in = new ByteArrayInputStream(data);
      ObjectInputStream is = new ObjectInputStream(in);
      return (User) is.readObject();
   }
}
