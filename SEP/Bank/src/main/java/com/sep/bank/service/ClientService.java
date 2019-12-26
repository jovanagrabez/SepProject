package com.sep.bank.service;

import com.sep.bank.model.Client;
import org.springframework.stereotype.Service;

@Service
public interface ClientService {

    Client createClient(Client c);
}
