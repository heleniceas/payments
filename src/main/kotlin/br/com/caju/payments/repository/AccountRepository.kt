package br.com.caju.payments.repository

import br.com.caju.payments.entity.AccountEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface AccountRepository : JpaRepository<AccountEntity, UUID> {

    fun findByNumber(account: String): AccountEntity?

    fun save(accountEntity: AccountEntity): AccountEntity

}