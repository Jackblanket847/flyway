/*-
 * ========================LICENSE_START=================================
 * flyway-database-mongodb
 * ========================================================================
 * Copyright (C) 2010 - 2025 Red Gate Software Ltd
 * ========================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * =========================LICENSE_END==================================
 */
package org.flywaydb.database.mongodb;

import org.flywaydb.core.internal.database.base.Table;
import org.flywaydb.core.internal.jdbc.JdbcTemplate;

import java.sql.SQLException;

public class MongoDBTable extends Table<MongoDBDatabase, MongoDBSchema> {
    /**
     * @param jdbcTemplate The JDBC template for communicating with the DB.
     * @param database     The database-specific support.
     * @param schema       The schema this table lives in.
     * @param name         The name of the table.
     */
    public MongoDBTable(JdbcTemplate jdbcTemplate, MongoDBDatabase database, MongoDBSchema schema, String name) {
        super(jdbcTemplate, database, schema, name);
    }

    @Override
    protected void doDrop() throws SQLException {
        jdbcTemplate.execute("db.getSiblingDB('" + schema.getName() + "')." + name + ".drop()");
    }

    @Override
    protected boolean doExists() throws SQLException {
        return jdbcTemplate.queryForInt("db.getSiblingDB('" + schema.getName() + "').getCollectionNames().filter(function (c) {return c == ('" + name + "');}).length") > 0;
    }

    @Override
    protected void doLock() throws SQLException {

    }
}
