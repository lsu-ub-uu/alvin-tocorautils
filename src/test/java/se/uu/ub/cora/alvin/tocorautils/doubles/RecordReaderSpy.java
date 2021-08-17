package se.uu.ub.cora.alvin.tocorautils.doubles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.uu.ub.cora.sqldatabase.DbQueryInfo;
import se.uu.ub.cora.sqldatabase.RecordReader;

public class RecordReaderSpy implements RecordReader {

	public String usedTableName = "";
	public List<Map<String, Object>> returnedList;
	public int noOfRecordsToReturn = 1;
	public Map<String, Object> usedConditions = new HashMap<>();

	@Override
	public List<Map<String, Object>> readAllFromTable(String tableName) {
		usedTableName = tableName;
		returnedList = new ArrayList<>();
		for (int i = 0; i < noOfRecordsToReturn; i++) {
			Map<String, Object> map = new HashMap<>();
			map.put("someKey" + i, "someValue" + i);
			returnedList.add(map);
		}
		return returnedList;
	}

	@Override
	public Map<String, Object> readOneRowFromDbUsingTableAndConditions(String tableName,
			Map<String, Object> conditions) {
		usedTableName = tableName;
		usedConditions = conditions;
		Map<String, Object> map = new HashMap<>();
		map.put("someKey", "someValue");
		returnedList = new ArrayList<>();
		returnedList.add(map);
		return map;

	}

	@Override
	public List<Map<String, Object>> readFromTableUsingConditions(String tableName,
			Map<String, Object> conditions) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> readNextValueFromSequence(String sequenceName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> readAllFromTable(String tableName, DbQueryInfo queryInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long readNumberOfRows(String tableName, Map<String, Object> conditions,
			DbQueryInfo queryInfo) {
		// TODO Auto-generated method stub
		return 0;
	}

}
