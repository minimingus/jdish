package io.dish.repository;

import io.dish.model.Provider;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;


@ApplicationScoped
public class ProviderRepository implements PanacheRepository<Provider> {

    public Optional<Provider> findByName(String name) {
        return Optional.ofNullable(find("name", name).firstResult());

    }
}
