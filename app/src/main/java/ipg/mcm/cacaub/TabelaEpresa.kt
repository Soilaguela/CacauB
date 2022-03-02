package ipg.mcm.cacaub

import android.database.sqlite.SQLiteDatabase

class TabelaEpresa(db : SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA($CAMPO_ID INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_NOME TEXT NOT NULL, $CAMPO_CATEGORIA TEXT)")
    }

    companion object {
        const val NOME_TABELA = "empresa"

        const val CAMPO_NOME = "nome"
        const val CAMPO_CATEGORIA = "categoria" // ex: cacau, cafe.

        val TODOS_CAMPOS = arrayOf(CAMPO_ID, CAMPO_NOME, CAMPO_CATEGORIA)
    }
}