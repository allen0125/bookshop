package com.sx.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;

import com.sx.db.DBlink;

public class DbMetadata {
	Connection conn = DBlink.getConnection();
//	DatabaseMetaData dbmd = conn.getMetaData();
}
