package com.cloudtracebucket.storageapi.utils

import java.sql.PreparedStatement
import java.sql.SQLException
import org.hibernate.type.EnumType
import java.sql.Types
import org.hibernate.HibernateException
import org.hibernate.engine.spi.SharedSessionContractImplementor

class PostgreSQLEnumUtil : EnumType<Enum<*>>() {

    @Throws(HibernateException::class, SQLException::class)
    override fun nullSafeSet(
        st: PreparedStatement,
        value: Any,
        index: Int,
        session: SharedSessionContractImplementor) {
        st.setObject(
            index,
            value.toString(),
            Types.OTHER
        )
    }
}
