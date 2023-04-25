package com.g1app.engine.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "relation_master")
public class RelationMaster {

    @Id
    @Column(name="id")
    UUID id;

    @Column(name="relation")
    String relation;

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "RelationMaster{" +
                "id=" + id +
                ", relation='" + relation + '\'' +
                '}';
    }
}
