package ipg.mcm.cacaub

import android.content.ContentValues
import android.database.Cursor

data class Venda(var descricao: String, var valor: String, var idEmpresa: Long, var nomeEmpresa: String = "", var id: Long = -1) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaVenda.CAMPO_DESCRICAO, descricao)
        valores.put(TabelaVenda.CAMPO_VALOR, valor)
        valores.put(TabelaVenda.CAMPO_ID_EMPRESA, idEmpresa)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor) = Venda(
            descricao = cursor.getString(cursor.getColumnIndex(TabelaVenda.CAMPO_DESCRICAO)),
            valor = cursor.getString(cursor.getColumnIndex(TabelaVenda.CAMPO_VALOR)),
            idEmpresa = cursor.getLong(cursor.getColumnIndex(TabelaVenda.CAMPO_ID_EMPRESA)),
            nomeEmpresa = cursor.getString(cursor.getColumnIndex(TabelaVenda.CAMPO_NOME_EMPRESA)),
            id = cursor.getLong(cursor.getColumnIndex(TabelaBD.CAMPO_ID))
        )
    }
}