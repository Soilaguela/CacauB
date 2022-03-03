package ipg.mcm.cacaub.login

import android.content.ContentValues
import android.database.Cursor
import ipg.mcm.cacaub.TabelaBD


data class User(val id: Long =-1, val name: String, val email: String, val password: String) {
    fun toContentValues() : ContentValues {
        val valores = ContentValues()

        valores.put(Tabelauser.CAMPO_NOME, name)
        valores.put(Tabelauser.CAMPO_EMAIL, email)
        valores.put(Tabelauser.CAMPO_PASS, password)
        return valores
    }

    companion object {
        fun fromCursor(cursor: Cursor) = User(
            name = cursor.getString(cursor.getColumnIndex(Tabelauser.CAMPO_NOME)),
            email = cursor.getString(cursor.getColumnIndex(Tabelauser.CAMPO_EMAIL)),
            password = cursor.getString(cursor.getColumnIndex(Tabelauser.CAMPO_PASS)),
            id = cursor.getLong(cursor.getColumnIndex(TabelaBD.CAMPO_ID))
        )
    }
}