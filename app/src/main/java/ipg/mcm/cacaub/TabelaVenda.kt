package ipg.mcm.cacaub

import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

class TabelaVenda(db: SQLiteDatabase) : TabelaBD(db, NOME_TABELA) {
    fun cria() {
        db.execSQL("CREATE TABLE $NOME_TABELA($CAMPO_ID INTEGER PRIMARY KEY AUTOINCREMENT, $CAMPO_DESCRICAO TEXT NOT NULL, $CAMPO_VALOR TEXT NOT NULL, $CAMPO_ID_EMPRESA INTEGER NOT NULL, FOREIGN KEY($CAMPO_ID_EMPRESA) REFERENCES ${TabelaEpresa.NOME_TABELA}(${CAMPO_ID}))")
    }

    override fun query(
        columns: Array<String>,
        selection: String?,
        selectionArgs: Array<String>?,
        groupBy: String?,
        having: String?,
        orderBy: String?
    ): Cursor {
        var posCampoEmpresa = -1 // -1 significa que não encontrou

        for ((i, coluna) in columns.withIndex()) {
            if (coluna == CAMPO_NOME_EMPRESA) {
                posCampoEmpresa = i
                break;
            }
        }

        if (posCampoEmpresa == -1) { // todos os campos pertencem à tabela de Empresa
            return super.query(columns, selection, selectionArgs, groupBy, having, orderBy)
        }

        var campos = ""
        for ((i, coluna) in columns.withIndex()) {
            if (i > 0) campos += ","

            campos += if (coluna == CAMPO_NOME_EMPRESA) {
                "${TabelaEpresa.NOME_TABELA}.${TabelaEpresa.CAMPO_NOME} AS $CAMPO_NOME_EMPRESA"
            } else {
                "$NOME_TABELA.$coluna"
            }
        }

        var selecao = when (selection?.trim()) {
            null -> ""
            "" -> ""
            else -> "WHERE $selection"
        }

        val sql = "SELECT $campos FROM $NOME_TABELA INNER JOIN ${TabelaEpresa.NOME_TABELA} ON $CAMPO_ID_EMPRESA = ${TabelaEpresa.NOME_TABELA}.$CAMPO_ID $selecao"
        return db.rawQuery(sql, selectionArgs)
    }

    companion object {
        const val NOME_TABELA = "venda"
        const val CAMPO_DESCRICAO = "descricao"
        const val CAMPO_VALOR = "valor"
        const val CAMPO_ID_EMPRESA = "id_empresa"
        const val CAMPO_NOME_EMPRESA = "nome_empresa"

        val TODOS_CAMPOS = arrayOf(CAMPO_ID, CAMPO_DESCRICAO, CAMPO_VALOR, CAMPO_ID_EMPRESA, CAMPO_NOME_EMPRESA)
    }
}