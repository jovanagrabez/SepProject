package com.sep.bank.service.serviceImpl;

import com.sep.bank.model.Client;
import com.sep.bank.repository.ClientRepository;
import com.sep.bank.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;


    @Override
    public Client createClient(Client c) {
        return clientRepository.save(c);
    }
}
