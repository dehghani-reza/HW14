package ir.maktab.entities.bankside;

import ir.maktab.entities.Address;
import ir.maktab.entities.customerside.Account;
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
public class BankBranch {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long bankBranchId;

    @Column(nullable = false)
    private String name;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "addressId")
    private Address address;

    @OneToOne
    @JoinColumn(name = "managerId")
    private BankBranchManager bankBranchManager;

    @OneToMany(mappedBy = "bankBranch")
    private List<BankEmployee> bankEmployeeList;

    @OneToMany(mappedBy = "bankBranch")
    private List<Account> accountList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankBranch)) return false;
        BankBranch that = (BankBranch) o;
        return getBankBranchId().equals(that.getBankBranchId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBankBranchId());
    }

    @Override
    public String toString() {
        return "BankBranch{" +
                "bankBranchId=" + bankBranchId +
                ", name='" + name + '\'' +
                ", address=" + address +
                '}';
    }
}
