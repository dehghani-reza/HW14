package ir.maktab.entities.customerside;

import ir.maktab.entities.bankside.BankBranch;
import ir.maktab.entities.customerside.card.CreditCard;
import ir.maktab.entities.customerside.transaction.AccountTransaction;
import ir.maktab.entities.customerside.transaction.CreditTransferRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long accountId;

    private Long accountBalance;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customerId")
    private Customer customer;

    @OneToOne(mappedBy = "account")
    private CreditCard creditCard;

    @ManyToOne
    @JoinColumn(name = "bankBranchId")
    private BankBranch bankBranch;

    @OneToMany(mappedBy = "originAccount")
    private List<CreditTransferRequest> creditTransferRequestList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return getAccountId().equals(account.getAccountId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAccountId());
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", accountBalance=" + accountBalance +
                ", customer=" + customer +
                ", bankBranch=" + bankBranch +
                '}';
    }
}
