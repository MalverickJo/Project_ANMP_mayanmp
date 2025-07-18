package com.example.mayanmp.model

import androidx.room.*

@Dao
interface
DaoBudget {
    @Insert
    fun insert(budget: Budget)

    @Query("UPDATE Budget SET name=:pname, nominal=:pnominal, budgetLeft=:pbudgetLeft WHERE id = :pid")
    fun update(pname: String, pnominal: Int, pbudgetLeft: Int, pid: Int)

    @Query("SELECT * FROM Budget WHERE id= :id")
    fun selectBudget(id:Int): Budget

    @Delete
    fun delete(budget: Budget)

    @Query("SELECT * FROM Budget WHERE idUser = :userId")
    fun getAllBudgets(userId: Int): List<Budget>
}