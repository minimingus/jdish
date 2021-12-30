package io.dish.service;

import io.dish.mappers.ProviderMapper;
import io.dish.model.Provider;
import io.dish.dto.ProviderDto;
import io.dish.repository.ProviderRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class ProviderService {

    @Inject
    ProviderRepository providerRepository;

    public Optional<ProviderDto> findByName(String providerName) {
        return providerRepository.findByName(providerName)
                .map(ProviderMapper::toProviderDto);
    }


    @Transactional
    public UUID createProvider (ProviderDto providerDto) {
        System.out.println("creating provider");
        Provider provider = ProviderMapper.toProvider(providerDto);
        System.out.println(provider);
        providerRepository.persistAndFlush(provider);
        return provider.getProviderId();

    }



}
