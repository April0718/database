package ContentObjects;

import java.util.List;

public interface RecordDAO {
	public Boolean insertRecord(Record record);
	public void updateRecord(int uID,int fID);
	public void deleteRecord(int uID,int fID);
	public List<Record> getRecord(int uID,int fID);
	public void print(List<Record> records);
}
