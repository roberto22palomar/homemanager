package com.example.homemanager.infraestructure.abstract_services;

public interface CrudService<Request, Response, Id> {

    Response create(Request request);

    Response read(Id id);

    Response update(Request request, Id id);

    void delete(Id id);
}
