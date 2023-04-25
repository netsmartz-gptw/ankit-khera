package com.g1app.engine.services;

import com.g1app.engine.models.FamilyMember;
import com.g1app.engine.repositories.FamilyMembersRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class FamilyMembersServiceImpl implements FamilyMembersServices  {

    @Autowired
    FamilyMembersRepository familyMembersRepository;

    @Override
    public FamilyMember save(FamilyMember entity) {
        return null;
    }

    @Override
    public FamilyMember getById(Serializable id) {
        return null;
    }

    @Override
    public List<FamilyMember> getAll() {
        return null;
    }

    @Override
    public void delete(Serializable id) {
       // familyMembersRepository.delete((UUID) id);
    }

    @Override
    public List<FamilyMember> findByCustomerID(UUID customerID) {
        return (List<FamilyMember>) familyMembersRepository.findByCustomerID(customerID);
    }

    @Override
    public void deleteFamilyMember(UUID customerID) {
        familyMembersRepository.deleteById(customerID);

    }

}
