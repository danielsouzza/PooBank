package com.danielsouzza.poobank.model.account

class ExtractAccount {
    private var extract = mutableListOf<ExtractItem>()

    fun addLaunch(launch: ExtractItem){
        this.extract.add(launch)
    }

    fun getExtract(): List<ExtractItem>{
        return this.extract
    }

    fun getExtract(date:String): List<ExtractItem>{
        if (extract.isEmpty()){
            throw DataInvalidException(date)
        }
        return extract.filter {
            it.getData() == date
        }
    }
}