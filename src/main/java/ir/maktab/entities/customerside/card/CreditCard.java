package ir.maktab.entities.customerside.card;

import ir.maktab.entities.customerside.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long creditCardId;

    @OneToOne(mappedBy = "creditCard")
    private Account account;

    private Long cardNumber;

    private String firstPassword;

    private boolean isSuspended;

    @OneToOne
    @JoinColumn(name = "cardPasswordInfoId")
    private CardPasswordInfo cardPasswordInfo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreditCard)) return false;
        CreditCard that = (CreditCard) o;
        return getCreditCardId().equals(that.getCreditCardId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCreditCardId());
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "account=" + account +
                ", cardNumber=" + cardNumber +
                ", firstPassword='" + firstPassword + '\'' +
                ", isSuspended=" + isSuspended +
                '}';
    }
}
