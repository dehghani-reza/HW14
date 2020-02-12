package ir.maktab.entities.customerside.transaction;

import ir.maktab.entities.customerside.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
public class DepositRequestTransaction extends AccountTransaction {

    private Long transferAmount;

    private String description;

    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account originAccount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DepositRequestTransaction)) return false;
        if (!super.equals(o)) return false;
        DepositRequestTransaction that = (DepositRequestTransaction) o;
        return Objects.equals(getTransferAmount(), that.getTransferAmount());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTransferAmount());
    }

    @Override
    public String toString() {
        return "DepositRequestTransaction{" +
                "transferAmount=" + transferAmount +
                ", description='" + description + '\'' +
                ", originAccount=" + originAccount +
                '}';
    }
}
