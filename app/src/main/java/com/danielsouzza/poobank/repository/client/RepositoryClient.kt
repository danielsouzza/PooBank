package com.danielsouzza.poobank.repository.client

import com.danielsouzza.poobank.model.client.Client

interface RepositoryClient {
    fun insertClient(client: Client)
    fun alterClient(client: Client)
    fun deleteClient(client: Client)
    fun searchClient(cpf: String): Client
    fun getAll(): List<Client>
}