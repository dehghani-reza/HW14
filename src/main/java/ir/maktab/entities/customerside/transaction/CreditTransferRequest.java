package ir.maktab.entities.customerside.transaction;

import ir.maktab.entities.customerside.Account;
import ir.maktab.entities.customerside.card.CreditCard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class CreditTransferRequest extends AccountTransaction {

    @ManyToOne
    @JoinColumn(name ="creditCardId")
    private CreditCard sourceCard;

    @ManyToOne
    @JoinColumn(name ="creditCardId" , insertable = false , updatable = false)
    private CreditCard destinationCardId;

    private Long transferAmount;

    private String description;


    @ManyToOne
    @JoinColumn(name = "accountId")
    private Account originAccount;

    @Override
    public String toString() {
        return "CreditTransferRequest{" +
                "sourceCard=" + sourceCard +
                ", destinationCardId=" + destinationCardId +
                ", transferAmount=" + transferAmount +
                ", description='" + description + '\'' +
                ", originAccount=" + originAccount +
                '}';
    }
}
