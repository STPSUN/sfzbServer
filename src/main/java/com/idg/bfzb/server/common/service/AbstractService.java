package com.idg.bfzb.server.common.service;

import org.springframework.data.repository.CrudRepository;

import java.io.Serializable;


public abstract class AbstractService<T,ID extends Serializable> {

    protected abstract CrudRepository<T,ID> getRepository();


    public T get(ID id) {
        return this.getRepository().findOne(id);
    }

    public Iterable<T> getList(Iterable<ID> ids) {
        return this.getRepository().findAll(ids);
    }


    public T create(T entity) {
        return this.getRepository().save(entity);
    }

    public T update(T entity) {
        return this.getRepository().save(entity);
    }

    public void delete(ID id) {
        this.getRepository().delete(id);
    }

    public void delete(T entity){
        this.getRepository().delete(entity);
    }

    public void delete(Iterable<T> ids){
        this.getRepository().delete(ids);
    }

}
