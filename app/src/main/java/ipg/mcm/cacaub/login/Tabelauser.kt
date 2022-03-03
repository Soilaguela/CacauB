package ipg.mcm.cacaub.login

import android.database.sqlite.SQLiteDatabase
import ipg.mcm.cacaub.TabelaBD
import ipg.mcm.cacaub.TabelaEpresa

class Tabelauser(db : SQLiteDatabase) : TabelaBD(db, TabelaEpresa.NOME_TABELA) {
    fun cria() {
        db.execSQL("CREATE TABLE ${TabelaEpresa.NOME_TABELA}(${TabelaBD.CAMPO_ID} INTEGER PRIMARY KEY AUTOINCREMENT, ${Tabelauser.CAMPO_NOME} TEXT NOT NULL, ${Tabelauser.CAMPO_PASS} TEXT NOT NULL ,${Tabelauser.CAMPO_EMAIL} TEXT NOT NULL)")
    }



//val name: String, val email: String, val password: String
    companion object {
        const val NOME_TABELA = "User"

        const val CAMPO_NOME = "nome"
        const val CAMPO_EMAIL = "email"
        const val CAMPO_PASS = "password" // ex: Sr. Eng. Dr.
        val TODOS_CAMPOS = arrayOf(CAMPO_ID, CAMPO_NOME, CAMPO_EMAIL, CAMPO_PASS)
    }
}