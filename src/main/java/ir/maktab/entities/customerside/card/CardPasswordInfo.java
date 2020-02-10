package ir.maktab.entities.customerside.card;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class CardPasswordInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cardPasswordInfoId;

    private int cvv2;

    private LocalDate expirationDate;

    private Long secondPassword;

    @OneToOne(mappedBy = "cardPasswordInfo")
    private CreditCard creditCard;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CardPasswordInfo)) return false;
        CardPasswordInfo that = (CardPasswordInfo) o;
        return getCvv2() == that.getCvv2() &&
                getExpirationDate().equals(that.getExpirationDate()) &&
                getSecondPassword().equals(that.getSecondPassword()) &&
                getCreditCard().equals(that.getCreditCard());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCvv2(), getExpirationDate(), getSecondPassword(), getCreditCard());
    }

    @Override
    public String toString() {
        return "CardPasswordInfo{" +
                "cvv2=" + cvv2 +
                ", expirationDate=" + expirationDate +
                ", secondPassword='" + secondPassword + '\'' +
                ", creditCard=" + creditCard +
                '}';
    }
}
