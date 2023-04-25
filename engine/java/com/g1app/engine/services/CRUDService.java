package com.g1app.engine.services;


import com.g1app.engine.models.FamilyMember;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public interface CRUDService<T> {
    /***
     *
     * @author Mayank S.
     */
        T save(T entity);
        FamilyMember getById(Serializable id);
        List<FamilyMember> getAll();
        void delete(Serializable id);
}
