package ir.maktab.entities.customerside.transaction;

import ir.maktab.entities.customerside.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor

@MappedSuperclass
public class AccountTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountTransactionId;

    private boolean isSuccessfullyDone;

    private Date transactionDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountTransaction)) return false;
        AccountTransaction that = (AccountTransaction) o;
        return getAccountTransactionId().equals(that.getAccountTransactionId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccountTransactionId());
    }

    @Override
    public String toString() {
        return "AccountTransaction{" +
                "isSuccessfullyDone=" + isSuccessfullyDone +
                ", transactionDate=" + transactionDate +
                '}';
    }
}
