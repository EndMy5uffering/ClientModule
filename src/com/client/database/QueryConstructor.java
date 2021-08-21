package com.client.database;

@FunctionalInterface
public interface QueryConstructor {
	
	public String construct(QueryObject q);
	
}
