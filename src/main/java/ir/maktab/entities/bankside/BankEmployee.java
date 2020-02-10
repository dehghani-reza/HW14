package ir.maktab.entities.bankside;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class BankEmployee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long employeeId;

    private String name;

    private String LastName;

    @ManyToOne
    @JoinColumn(name ="bankBranchId")
    private BankBranch bankBranch;

    @ManyToOne
    @JoinColumn(name = "managerId")
    private BankBranchManager bankBranchManager;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankEmployee)) return false;
        BankEmployee that = (BankEmployee) o;
        return getEmployeeId().equals(that.getEmployeeId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmployeeId());
    }

    @Override
    public String toString() {
        return "BankEmployee{" +
                "name='" + name + '\'' +
                ", LastName='" + LastName + '\'' +
                ", bankBranchManager=" + bankBranchManager +
                '}';
    }
}
