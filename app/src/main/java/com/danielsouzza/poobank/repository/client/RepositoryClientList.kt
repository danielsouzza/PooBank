package com.danielsouzza.poobank.repository.client

import com.danielsouzza.poobank.model.client.Client

class RepositoryClientList : RepositoryClient {

    private val clients = mutableListOf<Client>()

    override fun insertClient(client: Client) {
        try {
            searchClient(client.getCpf())
            throw CPFAlreadyRegistered()
        } catch (ex: ClientNotRegisteredException) {
            clients.add(client)
        }
    }

    override fun alterClient(client: Client) {
        searchClient(client.getCpf())
    }

    override fun deleteClient(client: Client) {
        if (!clients.remove(client)) {
            throw ClientNotRegisteredException()
        }
    }

    override fun searchClient(cpf: String): Client {
        clients.forEach {
            if (it.getCpf() == cpf) {
                return it
            }
        }
        throw ClientNotRegisteredException()
    }

    override fun getAll(): List<Client> = clients
}