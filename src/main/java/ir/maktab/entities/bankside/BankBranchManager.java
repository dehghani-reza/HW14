package ir.maktab.entities.bankside;

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
public class BankBranchManager{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long managerId;

    private String name;

    private String LastName;

    @OneToOne(mappedBy = "bankBranchManager")
    private BankBranch bankBranch;

    @OneToMany(mappedBy = "bankBranchManager")
    private List<BankEmployee> bankEmployeeList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankBranchManager)) return false;
        BankBranchManager that = (BankBranchManager) o;
        return getManagerId().equals(that.getManagerId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getManagerId());
    }

    @Override
    public String toString() {
        return "BankBranchManager{" +
                "name='" + name + '\'' +
                ", LastName='" + LastName + '\'' +
                ", bankBranch=" + bankBranch +
                '}';
    }
}
