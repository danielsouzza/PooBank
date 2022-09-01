package com.danielsouzza.poobank.model.account

class DataInvalidException(date:String): Exception("Nenhuma transaçâo na data $date") {
}