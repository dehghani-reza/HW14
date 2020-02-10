package ir.maktab.repositories.bankside;

import ir.maktab.entities.bankside.BankBranch;
import ir.maktab.entities.bankside.BankBranchManager;
import ir.maktab.repositories.CrudRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

public class BankBranchManagerDao extends CrudRepository<BankBranchManager,Long> {

    @Override
    protected Class<BankBranchManager> getEntityClass() {
        return BankBranchManager.class;
    }
}
