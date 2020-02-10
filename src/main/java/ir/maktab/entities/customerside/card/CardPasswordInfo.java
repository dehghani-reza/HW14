package ir.maktab.entities.customerside.card;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    private Date expirationDate;

    private String secondPassword;

    @OneToOne(mappedBy = "cardPasswordInfo")
    private CreditCard creditCard;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CardPasswordInfo)) return false;
        CardPasswordInfo that = (CardPasswordInfo) o;
        return getCardPasswordInfoId().equals(that.getCardPasswordInfoId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCardPasswordInfoId());
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
