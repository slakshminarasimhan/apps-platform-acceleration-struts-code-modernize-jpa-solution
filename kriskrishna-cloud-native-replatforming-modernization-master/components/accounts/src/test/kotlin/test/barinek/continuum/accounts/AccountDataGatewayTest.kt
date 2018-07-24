package test.barinek.continuum.accounts

import io.barinek.continuum.TestDataSourceConfig
import io.barinek.continuum.accounts.AccountDataGateway
import io.barinek.continuum.jdbcsupport.JdbcTemplate
import org.junit.Test
import kotlin.test.assertEquals

class AccountDataGatewayTest() {
    val dataSource = TestDataSourceConfig().dataSource
    val template = JdbcTemplate(dataSource)
    val gateway = AccountDataGateway(template)

    @Test
    fun testCreate() {
        template.execute("insert into users (id, name) values (12, 'Jack')")

        gateway.create(12L, "anAccount")

        val actual = template.query("select id, owner_id, name from accounts", { },
                { rs -> listOf(rs.getLong("id"), rs.getLong("owner_id"), rs.getString("name")) }).first()

        assert(actual[0] as Long > 0)
        assertEquals(12L, actual[1])
        assertEquals("anAccount", actual[2])
    }

    @Test
    fun testFindBy() {
        template.execute("insert into users (id, name) values (12, 'Jack')")
        template.execute("insert into accounts (id, owner_id, name) values (1, 12, 'anAccount')")

        val actual = gateway.findBy(12L).first()

        assertEquals(1L, actual.id)
        assertEquals(12L, actual.ownerId)
        assertEquals("anAccount", actual.name)
    }
}