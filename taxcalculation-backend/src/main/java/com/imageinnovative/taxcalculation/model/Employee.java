package com.imageinnovative.taxcalculation.model;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Employee")
public class Employee {


    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long employeeId;

    private String firstName;

    private String lastName;

    private String email;

    private LocalDate doj;

    private double salary;

  public List<PhoneNumber> getPhoneNumbers() {
    return phoneNumbers;
  }

   public void setPhoneNumbers(List<PhoneNumber> phoneNumbers) {
      this.phoneNumbers = phoneNumbers;
   }

   @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhoneNumber> phoneNumbers;

}