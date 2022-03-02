package ipg.mcm.cacaub

import android.content.ContentValues
import android.database.Cursor

data class Empresa(var nome: String, var categoria: String?, var id: Long = -1) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(TabelaEpresa.CAMPO_NOME, nome)
        valores.put(TabelaEpresa.CAMPO_CATEGORIA, categoria)

        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor) = Empresa(
            nome = cursor.getString(cursor.getColumnIndex(TabelaEpresa.CAMPO_NOME)),
            categoria = cursor.getString(cursor.getColumnIndex(TabelaEpresa.CAMPO_CATEGORIA)),
            id = cursor.getLong(cursor.getColumnIndex(TabelaBD.CAMPO_ID))
        )
    }
}