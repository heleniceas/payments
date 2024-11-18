package br.com.caju.payments.repository

import br.com.caju.payments.entity.TransactionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface TransactionRepository : JpaRepository<TransactionEntity, UUID> {

    fun save(transaction: TransactionEntity): TransactionEntity
}